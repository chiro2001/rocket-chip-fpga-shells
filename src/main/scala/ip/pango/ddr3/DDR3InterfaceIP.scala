package sifive.fpgashells.ip.pango.ddr3

import Chisel.{Bits, _}
import chisel3.experimental.{Analog, attach}
import freechips.rocketchip.amba.axi4.AXI4Bundle
import freechips.rocketchip.util.ElaborationArtefacts
import freechips.rocketchip.util.GenericParameterizedBundle
import freechips.rocketchip.config._

// IP VLNV: xilinx.com:customize_ip:ddr3_core:1.0
// Black Box

/**
 * .pll_refclk_in(pll_refclk_in),        // input
 * .top_rst_n(top_rst_n),                // input
 * .ddrc_rst(ddrc_rst),                  // input
 * .csysreq_ddrc(csysreq_ddrc),          // input
 * .csysack_ddrc(csysack_ddrc),          // output
 * .cactive_ddrc(cactive_ddrc),          // output
 * .pll_lock(pll_lock),                  // output
 * .pll_aclk_0(pll_aclk_0),              // output
 * .pll_aclk_1(pll_aclk_1),              // output
 * .pll_aclk_2(pll_aclk_2),              // output
 * .ddrphy_rst_done(ddrphy_rst_done),    // output
 * .ddrc_init_done(ddrc_init_done),      // output
 *
 * .pad_loop_in(pad_loop_in),            // input
 * .pad_loop_in_h(pad_loop_in_h),        // input
 * // .pad_rstn_ch0(pad_rstn_ch0),          // output
 * // .pad_ddr_clk_w(pad_ddr_clk_w),        // output
 * // .pad_ddr_clkn_w(pad_ddr_clkn_w),      // output
 * // .pad_csn_ch0(pad_csn_ch0),            // output
 * // .pad_addr_ch0(pad_addr_ch0),          // output [15:0]
 * // .pad_dq_ch0(pad_dq_ch0),              // inout [15:0]
 * // .pad_dqs_ch0(pad_dqs_ch0),            // inout [1:0]
 * // .pad_dqsn_ch0(pad_dqsn_ch0),          // inout [1:0]
 * // .pad_dm_rdqs_ch0(pad_dm_rdqs_ch0),    // output [1:0]
 * // .pad_cke_ch0(pad_cke_ch0),            // output
 * // .pad_odt_ch0(pad_odt_ch0),            // output
 * // .pad_rasn_ch0(pad_rasn_ch0),          // output
 * // .pad_casn_ch0(pad_casn_ch0),          // output
 * // .pad_wen_ch0(pad_wen_ch0),            // output
 * // .pad_ba_ch0(pad_ba_ch0),              // output [2:0]
 * .pad_loop_out(pad_loop_out),          // output
 * .pad_loop_out_h(pad_loop_out_h),      // output
 *
 * .areset_0(areset_0),                  // input
 * .aclk_0(aclk_0),                      // input
 * .awid_0(awid_0),                      // input [7:0]
 * .awaddr_0(awaddr_0),                  // input [31:0]
 * .awlen_0(awlen_0),                    // input [7:0]
 * .awsize_0(awsize_0),                  // input [2:0]
 * .awburst_0(awburst_0),                // input [1:0]
 * .awlock_0(awlock_0),                  // input
 * .awvalid_0(awvalid_0),                // input
 * .awready_0(awready_0),                // output
 * .awurgent_0(awurgent_0),              // input
 * .awpoison_0(awpoison_0),              // input
 * .wdata_0(wdata_0),                    // input [127:0]
 * .wstrb_0(wstrb_0),                    // input [15:0]
 * .wlast_0(wlast_0),                    // input
 * .wvalid_0(wvalid_0),                  // input
 * .wready_0(wready_0),                  // output
 * .bid_0(bid_0),                        // output [7:0]
 * .bresp_0(bresp_0),                    // output [1:0]
 * .bvalid_0(bvalid_0),                  // output
 * .bready_0(bready_0),                  // input
 * .arid_0(arid_0),                      // input [7:0]
 * .araddr_0(araddr_0),                  // input [31:0]
 * .arlen_0(arlen_0),                    // input [7:0]
 * .arsize_0(arsize_0),                  // input [2:0]
 * .arburst_0(arburst_0),                // input [1:0]
 * .arlock_0(arlock_0),                  // input
 * .arvalid_0(arvalid_0),                // input
 * .arready_0(arready_0),                // output
 * .arpoison_0(arpoison_0),              // input
 * .rid_0(rid_0),                        // output [7:0]
 * .rdata_0(rdata_0),                    // output [127:0]
 * .rresp_0(rresp_0),                    // output [1:0]
 * .rlast_0(rlast_0),                    // output
 * .rvalid_0(rvalid_0),                  // output
 * .rready_0(rready_0),                  // input
 * .arurgent_0(arurgent_0),              // input
 * .csysreq_0(csysreq_0),                // input
 * .csysack_0(csysack_0),                // output
 * .cactive_0(cactive_0)                 // output
 * );
 */

trait PGL22GMIGIODDRBaseTraitInOut extends Bundle {
  val pad_addr_ch0 = Bits(OUTPUT, 16)
  val pad_ba_ch0 = Bits(OUTPUT, 3)
  val pad_rasn_ch0 = Bool(OUTPUT)
  val pad_casn_ch0 = Bool(OUTPUT)
  val pad_wen_ch0 = Bool(OUTPUT)
  val pad_rstn_ch0 = Bool(OUTPUT)
  val pad_ddr_clk_w = Bits(OUTPUT, 1)
  val pad_ddr_clkn_w = Bits(OUTPUT, 1)
  val pad_cke_ch0 = Bits(OUTPUT, 1)
  val pad_csn_ch0 = Bits(OUTPUT, 1)
  val pad_dm_rdqs_ch0 = Bits(OUTPUT, 2)
  val pad_odt_ch0 = Bits(OUTPUT, 1)
  val pad_dq_ch0 = Analog(16.W)
  val pad_dqsn_ch0 = Analog(2.W)
  val pad_dqs_ch0 = Analog(2.W)

  // ?
  val pad_loop_in = Bool(INPUT)
  val pad_loop_in_h = Bool(INPUT)
  val pad_loop_out = Bool(OUTPUT)
  val pad_loop_out_h = Bool(OUTPUT)

  def connectPads(ddrIO: PGL22GMIGIODDR) = {
    attach(pad_dq_ch0, ddrIO.pad_dq_ch0)
    attach(pad_dqsn_ch0, ddrIO.pad_dqsn_ch0)
    attach(pad_dqs_ch0, ddrIO.pad_dqs_ch0)
    pad_addr_ch0 <> ddrIO.pad_addr_ch0
    pad_ba_ch0 <> ddrIO.pad_ba_ch0
    pad_rasn_ch0 <> ddrIO.pad_rasn_ch0
    pad_casn_ch0 <> ddrIO.pad_casn_ch0
    pad_wen_ch0 <> ddrIO.pad_wen_ch0
    pad_rstn_ch0 <> ddrIO.pad_rstn_ch0
    pad_ddr_clk_w <> ddrIO.pad_ddr_clk_w
    pad_ddr_clkn_w <> ddrIO.pad_ddr_clkn_w
    pad_cke_ch0 <> ddrIO.pad_cke_ch0
    pad_csn_ch0 <> ddrIO.pad_csn_ch0
    pad_dm_rdqs_ch0 <> ddrIO.pad_dm_rdqs_ch0
    pad_odt_ch0 <> ddrIO.pad_odt_ch0
    ddrIO.pad_loop_in := pad_loop_in
    ddrIO.pad_loop_in_h := pad_loop_in_h
    pad_loop_out <> ddrIO.pad_loop_out
    pad_loop_out_h <> ddrIO.pad_loop_out_h
  }
}

class PGL22GMIGIODDRBase extends PGL22GMIGIODDRBaseTraitInOut

class PGL22GMIGIODDR(depth: BigInt = BigInt(0x80000000L))
  extends GenericParameterizedBundle(depth)
    with PGL22GMIGIODDRBaseTraitInOut {
  // require((depth<=0x80000000L),"PGL22GMIGIODDR supports upto 2GB depth configuraton")
}

//reused directly in io bundle for sifive.blocks.devices.xilinxhmemc
trait PGL22GMIGIOClocksReset extends Bundle {
  // 外部参考时钟输入
  val pll_refclk_in = Clock(INPUT)
  // 外部复位输入
  val top_rst_n = Bool(INPUT)
  // DDRC 的复位输入
  val ddrc_rst = Bool(INPUT)
  // ddr3_core 内部 PLL lock 信号。
  val pll_lock = Bool(OUTPUT)
  // DDRPHY 复位完成标志
  val ddrphy_rst_done = Bool(OUTPUT)
  // DDRC 的初始化完成标志
  val ddrc_init_done = Bool(OUTPUT)
  // Axi4 Port0 的时钟
  val pll_aclk_0 = Clock(OUTPUT)
  // Axi4 Port1 的时钟
  val pll_aclk_1 = Clock(OUTPUT)
  // Axi4 Port2 的时钟
  val pll_aclk_2 = Clock(OUTPUT)
  // APB Port 的时钟
  // val pll_pclk = Clock(OUTPUT)
  // DDRC 低功耗请求输入
  val csysreq_ddrc = Bool(INPUT)
  // ?
  val csysreq_1 = Bool(INPUT)
  // DDRC 低功耗响应
  val csysack_ddrc = Bool(OUTPUT)
  // DDRC 激活标志
  val cactive_ddrc = Bool(OUTPUT)

  // Axi4 Port0 返回时钟
  def aclk_0: Option[Clock]

  // Axi4 Port1 返回时钟
  def aclk_1: Option[Clock]

  // Axi4 Port2 返回时钟
  def aclk_2: Option[Clock]
  
  def connectClockResetIOs(ddrCtrl: PGL22GMIGIOClocksReset) ={
    pll_refclk_in <> ddrCtrl.pll_refclk_in
    top_rst_n <> ddrCtrl.top_rst_n
    ddrc_rst <> ddrCtrl.ddrc_rst
    pll_lock <> ddrCtrl.pll_lock
    ddrphy_rst_done <> ddrCtrl.ddrphy_rst_done
    ddrc_init_done <> ddrCtrl.ddrc_init_done
    pll_aclk_0 <> ddrCtrl.pll_aclk_0
    pll_aclk_1 <> ddrCtrl.pll_aclk_1
    pll_aclk_2 <> ddrCtrl.pll_aclk_2
    if (aclk_0.nonEmpty && ddrCtrl.aclk_0.nonEmpty) aclk_0.get <> ddrCtrl.aclk_0.get
    if (aclk_1.nonEmpty && ddrCtrl.aclk_1.nonEmpty) aclk_1.get <> ddrCtrl.aclk_1.get
    if (aclk_2.nonEmpty && ddrCtrl.aclk_2.nonEmpty) aclk_2.get <> ddrCtrl.aclk_2.get
    // pll_pclk <> ddrCtrl.pll_pclk
    csysreq_ddrc <> ddrCtrl.csysreq_ddrc
    csysreq_1 <> ddrCtrl.csysreq_1
    csysack_ddrc <> ddrCtrl.csysack_ddrc
    cactive_ddrc <> ddrCtrl.cactive_ddrc
  }
}

class PGL22GMIGIOClocksResetBundle(useClock: Int = 1) extends PGL22GMIGIOClocksReset {
  override val aclk_0 = if (useClock == 0) Some(Input(Clock())) else None

  override val aclk_1 = if (useClock == 1) Some(Input(Clock())) else None

  override val aclk_2 = if (useClock == 2) Some(Input(Clock())) else None
}

class PGL22GMIGIODDRIO(depth: BigInt = BigInt(0x80000000L), useClock: Int = 1)
  extends PGL22GMIGIODDR(depth) with PGL22GMIGIOClocksReset {
  //axi_s
  //slave interface write address ports
  val awid_0 = Bits(INPUT, 8)
  val awaddr_0 = Bits(INPUT, 32)
  val awlen_0 = Bits(INPUT, 8)
  val awsize_0 = Bits(INPUT, 3)
  val awburst_0 = Bits(INPUT, 2)
  val awlock_0 = Bits(INPUT, 1)
  val awvalid_0 = Bool(INPUT)
  val awready_0 = Bool(OUTPUT)
  //slave interface write data ports
  val wdata_0 = Bits(INPUT, 128)
  val wstrb_0 = Bits(INPUT, 16)
  val wlast_0 = Bool(INPUT)
  val wvalid_0 = Bool(INPUT)
  val wready_0 = Bool(OUTPUT)
  //slave interface write response ports
  val bready_0 = Bool(INPUT)
  val bid_0 = Bits(OUTPUT, 8)
  val bresp_0 = Bits(OUTPUT, 2)
  val bvalid_0 = Bool(OUTPUT)
  //slave interface read address ports
  val arid_0 = Bits(INPUT, 8)
  val araddr_0 = Bits(INPUT, 32)
  val arlen_0 = Bits(INPUT, 8)
  val arsize_0 = Bits(INPUT, 3)
  val arburst_0 = Bits(INPUT, 2)
  val arlock_0 = Bits(INPUT, 1)
  val arvalid_0 = Bool(INPUT)
  val arready_0 = Bool(OUTPUT)
  //slave interface read data ports
  val rready_0 = Bool(INPUT)
  val rid_0 = Bits(OUTPUT, 8)
  val rdata_0 = Bits(OUTPUT, 128)
  val rresp_0 = Bits(OUTPUT, 2)
  val rlast_0 = Bool(OUTPUT)
  val rvalid_0 = Bool(OUTPUT)

  override val aclk_0 = if (useClock == 0) Some(Input(Clock())) else None

  override val aclk_1 = if (useClock == 1) Some(Input(Clock())) else None

  override val aclk_2 = if (useClock == 2) Some(Input(Clock())) else None

  def connect(axi: AXI4Bundle) = {
    val offset = depth.U

    val awaddr = axi.aw.bits.addr - offset
    val araddr = axi.ar.bits.addr - offset

    axi.aw.bits.id <> awid_0
    // axi.aw.bits.addr <> awaddr_0
    awaddr_0 := awaddr
    axi.aw.bits.len <> awlen_0
    axi.aw.bits.size <> awsize_0
    axi.aw.bits.burst <> awburst_0
    axi.aw.bits.lock <> awlock_0
    axi.aw.valid <> awvalid_0
    axi.aw.ready <> awready_0
    axi.w.bits.data <> wdata_0
    axi.w.bits.strb <> wstrb_0
    axi.w.bits.last <> wlast_0
    axi.w.valid <> wvalid_0
    axi.w.ready <> wready_0
    axi.b.ready <> bready_0
    axi.b.bits.id <> bid_0
    axi.b.bits.resp <> bresp_0
    axi.b.valid <> bvalid_0
    axi.ar.bits.id <> arid_0
    // axi.ar.bits.addr <> araddr_0
    araddr_0 := araddr
    axi.ar.bits.len <> arlen_0
    axi.ar.bits.size <> arsize_0
    axi.ar.bits.burst <> arburst_0
    axi.ar.bits.lock <> arlock_0
    axi.ar.valid <> arvalid_0
    axi.ar.ready <> arready_0
    axi.r.ready <> rready_0
    axi.r.bits.id <> rid_0
    axi.r.bits.data <> rdata_0
    axi.r.bits.resp <> rresp_0
    axi.r.bits.last <> rlast_0
    axi.r.valid <> rvalid_0
  }
}

class PGL22GMIGIODDRIO64(depth: BigInt = BigInt(0x80000000L), useClock: Int = 1)
  extends PGL22GMIGIODDR(depth) with PGL22GMIGIOClocksReset {
  //axi_s
  //slave interface write address ports
  val awid_1 = Bits(INPUT, 8)
  val awaddr_1 = Bits(INPUT, 32)
  val awlen_1 = Bits(INPUT, 8)
  val awsize_1 = Bits(INPUT, 3)
  val awburst_1 = Bits(INPUT, 2)
  val awlock_1 = Bits(INPUT, 1)
  val awvalid_1 = Bool(INPUT)
  val awready_1 = Bool(OUTPUT)
  //slave interface write data ports
  val wdata_1 = Bits(INPUT, 64)
  val wstrb_1 = Bits(INPUT, 16)
  val wlast_1 = Bool(INPUT)
  val wvalid_1 = Bool(INPUT)
  val wready_1 = Bool(OUTPUT)
  //slave interface write response ports
  val bready_1 = Bool(INPUT)
  val bid_1 = Bits(OUTPUT, 8)
  val bresp_1 = Bits(OUTPUT, 2)
  val bvalid_1 = Bool(OUTPUT)
  //slave interface read address ports
  val arid_1 = Bits(INPUT, 8)
  val araddr_1 = Bits(INPUT, 32)
  val arlen_1 = Bits(INPUT, 8)
  val arsize_1 = Bits(INPUT, 3)
  val arburst_1 = Bits(INPUT, 2)
  val arlock_1 = Bits(INPUT, 1)
  val arvalid_1 = Bool(INPUT)
  val arready_1 = Bool(OUTPUT)
  //slave interface read data ports
  val rready_1 = Bool(INPUT)
  val rid_1 = Bits(OUTPUT, 8)
  val rdata_1 = Bits(OUTPUT, 64)
  val rresp_1 = Bits(OUTPUT, 2)
  val rlast_1 = Bool(OUTPUT)
  val rvalid_1 = Bool(OUTPUT)

  override val aclk_0 = if (useClock == 0) Some(Input(Clock())) else None

  override val aclk_1 = if (useClock == 1) Some(Input(Clock())) else None

  override val aclk_2 = if (useClock == 2) Some(Input(Clock())) else None

  def connect(axi: AXI4Bundle) = {
    val offset = depth.U

    val awaddr = axi.aw.bits.addr - offset
    val araddr = axi.ar.bits.addr - offset

    axi.aw.bits.id <> awid_1
    // axi.aw.bits.addr <> awaddr_1
    awaddr_1 := awaddr
    axi.aw.bits.len <> awlen_1
    axi.aw.bits.size <> awsize_1
    axi.aw.bits.burst <> awburst_1
    axi.aw.bits.lock <> awlock_1
    axi.aw.valid <> awvalid_1
    axi.aw.ready <> awready_1
    axi.w.bits.data <> wdata_1
    axi.w.bits.strb <> wstrb_1
    axi.w.bits.last <> wlast_1
    axi.w.valid <> wvalid_1
    axi.w.ready <> wready_1
    axi.b.ready <> bready_1
    axi.b.bits.id <> bid_1
    axi.b.bits.resp <> bresp_1
    axi.b.valid <> bvalid_1
    axi.ar.bits.id <> arid_1
    // axi.ar.bits.addr <> araddr_1
    araddr_1 := araddr
    axi.ar.bits.len <> arlen_1
    axi.ar.bits.size <> arsize_1
    axi.ar.bits.burst <> arburst_1
    axi.ar.bits.lock <> arlock_1
    axi.ar.valid <> arvalid_1
    axi.ar.ready <> arready_1
    axi.r.ready <> rready_1
    axi.r.bits.id <> rid_1
    axi.r.bits.data <> rdata_1
    axi.r.bits.resp <> rresp_1
    axi.r.bits.last <> rlast_1
    axi.r.valid <> rvalid_1
  }
}

trait DDR3CoreTrait {
  val io: PGL22GMIGIODDR with PGL22GMIGIOClocksReset
}

//scalastyle:off
//turn off linter: blackbox name must match verilog module
class ddr3_core(depth: BigInt) extends BlackBox with DDR3CoreTrait {
  require((depth <= 0x80000000L), "ddr3_core supports upto 2GB depth configuraton")

  val io = new PGL22GMIGIODDRIO(depth)
}

class ddr3_core_64(depth: BigInt) extends BlackBox with DDR3CoreTrait {
  val io = new PGL22GMIGIODDRIO64(depth)
}
//scalastyle:on

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
