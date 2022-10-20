package sifive.fpgashells.devices.pango.ddr3

import Chisel._
import chisel3.experimental.{Analog, attach}
import freechips.rocketchip.amba.axi4._
import freechips.rocketchip.config.Parameters
import freechips.rocketchip.subsystem._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.interrupts._
import sifive.fpgashells.clocks.PLLNode
import sifive.fpgashells.ip.pango.ddr3.{PGL22GMIGIOClocksReset, PGL22GMIGIODDR, ddr3_core}

case class PangoPGL22GMIGParams(
  address : Seq[AddressSet]
)

class PangoPGL22GMIGPads(depth : BigInt) extends PGL22GMIGIODDR(depth) {
  def this(c : PangoPGL22GMIGParams) {
    this(AddressRange.fromSets(c.address).head.size)
  }
}

class PangoPGL22GMIGIO(depth : BigInt) extends PGL22GMIGIODDR(depth) with PGL22GMIGIOClocksReset

class PangoPGL22GMIGIsland(c : PangoPGL22GMIGParams)(implicit p: Parameters) extends LazyModule with CrossesToOnlyOneClockDomain {
  val ranges = AddressRange.fromSets(c.address)
  require (ranges.size == 1, "DDR range must be contiguous")
  val offset = ranges.head.base
  val depth = ranges.head.size
  val crossing = AsynchronousCrossing(8)
  // require((depth<=0x80000000L),"ddr3_core supports upto 2GB depth configuraton")
  
  val device = new MemoryDevice
  val node = AXI4SlaveNode(Seq(AXI4SlavePortParameters(
      slaves = Seq(AXI4SlaveParameters(
      address       = c.address,
      resources     = device.reg,
      regionType    = RegionType.UNCACHED,
      executable    = true,
      supportsWrite = TransferSizes(1, 256*8),
      supportsRead  = TransferSizes(1, 256*8))),
    beatBytes = 8)))

  lazy val module = new LazyModuleImp(this) {
    val io = IO(new Bundle {
      val port = new PangoPGL22GMIGIO(depth)
    })

    //MIG black box instantiation
    val blackbox = Module(new ddr3_core(depth))
    val (axi_async, _) = node.in(0)

    //pins to top level

    //inouts
    attach(io.port.pad_dq_ch0, blackbox.io.pad_dq_ch0)
    attach(io.port.pad_dqsn_ch0, blackbox.io.pad_dqsn_ch0)
    attach(io.port.pad_dqsn_ch0, blackbox.io.pad_dqsn_ch0)

    //outputs
    io.port.pad_addr_ch0 := blackbox.io.pad_addr_ch0
    io.port.pad_ba_ch0 := blackbox.io.pad_ba_ch0
    io.port.pad_rasn_ch0 := blackbox.io.pad_rasn_ch0
    io.port.pad_casn_ch0 := blackbox.io.pad_casn_ch0
    io.port.pad_wen_ch0 := blackbox.io.pad_wen_ch0
    io.port.pad_rstn_ch0 := blackbox.io.pad_rstn_ch0
    io.port.pad_ddr_clk_w := blackbox.io.pad_ddr_clk_w
    io.port.pad_ddr_clkn_w := blackbox.io.pad_ddr_clkn_w
    io.port.pad_cke_ch0 := blackbox.io.pad_cke_ch0
    io.port.pad_csn_ch0 := blackbox.io.pad_csn_ch0
    io.port.pad_dm_rdqs_ch0 := blackbox.io.pad_dm_rdqs_ch0
    io.port.pad_odt_ch0 := blackbox.io.pad_odt_ch0

    //inputs
    //NO_BUFFER clock
    blackbox.io.pll_refclk_in := io.port.pll_refclk_in
    blackbox.io.top_rst_n := io.port.top_rst_n
    blackbox.io.ddrc_rst := io.port.ddrc_rst
    io.port.ddrphy_rst_done := blackbox.io.ddrphy_rst_done
    io.port.ddrc_init_done := blackbox.io.ddrc_init_done
    io.port.pll_aclk_0 := blackbox.io.pll_aclk_0
    io.port.pll_aclk_1 := blackbox.io.pll_aclk_1
    io.port.pll_aclk_2 := blackbox.io.pll_aclk_2
    // io.port.pll_pclk := blackbox.io.pll_pclk
    io.port.pll_lock := blackbox.io.pll_lock
    // fixme: ignore low power request
    io.port.cactive_ddrc := blackbox.io.cactive_ddrc
    io.port.csysack_ddrc := blackbox.io.csysack_ddrc
    blackbox.io.csysreq_ddrc := io.port.csysreq_ddrc

    val awaddr = axi_async.aw.bits.addr - UInt(offset)
    val araddr = axi_async.ar.bits.addr - UInt(offset)

    //slave AXI interface write address ports
    blackbox.io.awid_0 := axi_async.aw.bits.id
    blackbox.io.awaddr_0 := awaddr //truncated
    blackbox.io.awlen_0 := axi_async.aw.bits.len
    blackbox.io.awsize_0 := axi_async.aw.bits.size
    blackbox.io.awburst_0 := axi_async.aw.bits.burst
    blackbox.io.awlock_0 := axi_async.aw.bits.lock
    // blackbox.io.awcache_0 := UInt("b0011")
    // blackbox.io.awprot_0 := axi_async.aw.bits.prot
    // blackbox.io.awqos_0 := axi_async.aw.bits.qos
    blackbox.io.awvalid_0 := axi_async.aw.valid
    axi_async.aw.ready := blackbox.io.awready_0

    //slave interface write data ports
    blackbox.io.wdata_0 := axi_async.w.bits.data
    blackbox.io.wstrb_0 := axi_async.w.bits.strb
    blackbox.io.wlast_0 := axi_async.w.bits.last
    blackbox.io.wvalid_0 := axi_async.w.valid
    axi_async.w.ready := blackbox.io.wready_0

    //slave interface write response
    blackbox.io.bready_0 := axi_async.b.ready
    axi_async.b.bits.id := blackbox.io.bid_0
    axi_async.b.bits.resp := blackbox.io.bresp_0
    axi_async.b.valid := blackbox.io.bvalid_0

    //slave AXI interface read address ports
    blackbox.io.arid_0 := axi_async.ar.bits.id
    blackbox.io.araddr_0 := araddr // truncated
    blackbox.io.arlen_0 := axi_async.ar.bits.len
    blackbox.io.arsize_0 := axi_async.ar.bits.size
    blackbox.io.arburst_0 := axi_async.ar.bits.burst
    blackbox.io.arlock_0 := axi_async.ar.bits.lock
    // blackbox.io.arcache_0 := UInt("b0011")
    // blackbox.io.arprot_0 := axi_async.ar.bits.prot
    // blackbox.io.arqos_0 := axi_async.ar.bits.qos
    blackbox.io.arvalid_0 := axi_async.ar.valid
    axi_async.ar.ready := blackbox.io.arready_0

    //slace AXI interface read data ports
    blackbox.io.rready_0 := axi_async.r.ready
    axi_async.r.bits.id := blackbox.io.rid_0
    axi_async.r.bits.data := blackbox.io.rdata_0
    axi_async.r.bits.resp := blackbox.io.rresp_0
    axi_async.r.bits.last := blackbox.io.rlast_0
    axi_async.r.valid := blackbox.io.rvalid_0
  }
}

class PangoPGL22GMIG(c : PangoPGL22GMIGParams)(implicit p: Parameters) extends LazyModule {
  val ranges = AddressRange.fromSets(c.address)
  val depth = ranges.head.size

  val buffer  = LazyModule(new TLBuffer)
  val toaxi4  = LazyModule(new TLToAXI4(adapterName = Some("mem")))
  val indexer = LazyModule(new AXI4IdIndexer(idBits = 4))
  val deint   = LazyModule(new AXI4Deinterleaver(p(CacheBlockBytes)))
  val yank    = LazyModule(new AXI4UserYanker)
  val island  = LazyModule(new PangoPGL22GMIGIsland(c))

  val node: TLInwardNode =
    island.crossAXI4In(island.node) := yank.node := deint.node := indexer.node := toaxi4.node := buffer.node

  val pllNode = PLLNode(feedback = false)
  // for AXI

  lazy val module = new LazyModuleImp(this) {
    val io = IO(new Bundle {
      val port = new PangoPGL22GMIGIO(depth)
    })
    // pllNode.in.head._1.clock := island.module.clock
    pllNode.out.head._1.member.head.clock := island.module.io.port.pll_aclk_0

    io.port <> island.module.io.port

    // Shove the island
    // TODO: link MIG clock and reset
    // island.module.clock := io.port.c0_ddr4_ui_clk
    // island.module.reset := io.port.c0_ddr4_ui_clk_sync_rst
  }
}

/*
   Copyright 2016 SiFive, Inc.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
