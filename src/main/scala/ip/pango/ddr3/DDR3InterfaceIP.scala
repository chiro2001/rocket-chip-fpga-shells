package sifive.fpgashells.ip.pango.ddr3

import Chisel._
import chisel3.experimental.{Analog,attach}
import freechips.rocketchip.util.{ElaborationArtefacts}
import freechips.rocketchip.util.GenericParameterizedBundle
import freechips.rocketchip.config._

// IP VLNV: xilinx.com:customize_ip:ddr3_core:1.0
// Black Box

/**
  .pll_refclk_in(pll_refclk_in),        // input
  .top_rst_n(top_rst_n),                // input
  .ddrc_rst(ddrc_rst),                  // input
  .csysreq_ddrc(csysreq_ddrc),          // input
  .csysack_ddrc(csysack_ddrc),          // output
  .cactive_ddrc(cactive_ddrc),          // output
  .pll_lock(pll_lock),                  // output
  .pll_aclk_0(pll_aclk_0),              // output
  .pll_aclk_1(pll_aclk_1),              // output
  .pll_aclk_2(pll_aclk_2),              // output
  .ddrphy_rst_done(ddrphy_rst_done),    // output
  .ddrc_init_done(ddrc_init_done),      // output

  .pad_loop_in(pad_loop_in),            // input
  .pad_loop_in_h(pad_loop_in_h),        // input
  // .pad_rstn_ch0(pad_rstn_ch0),          // output
  // .pad_ddr_clk_w(pad_ddr_clk_w),        // output
  // .pad_ddr_clkn_w(pad_ddr_clkn_w),      // output
  // .pad_csn_ch0(pad_csn_ch0),            // output
  // .pad_addr_ch0(pad_addr_ch0),          // output [15:0]
  // .pad_dq_ch0(pad_dq_ch0),              // inout [15:0]
  // .pad_dqs_ch0(pad_dqs_ch0),            // inout [1:0]
  // .pad_dqsn_ch0(pad_dqsn_ch0),          // inout [1:0]
  // .pad_dm_rdqs_ch0(pad_dm_rdqs_ch0),    // output [1:0]
  // .pad_cke_ch0(pad_cke_ch0),            // output
  // .pad_odt_ch0(pad_odt_ch0),            // output
  // .pad_rasn_ch0(pad_rasn_ch0),          // output
  // .pad_casn_ch0(pad_casn_ch0),          // output
  // .pad_wen_ch0(pad_wen_ch0),            // output
  // .pad_ba_ch0(pad_ba_ch0),              // output [2:0]
  .pad_loop_out(pad_loop_out),          // output
  .pad_loop_out_h(pad_loop_out_h),      // output

  .areset_0(areset_0),                  // input
  .aclk_0(aclk_0),                      // input
  .awid_0(awid_0),                      // input [7:0]
  .awaddr_0(awaddr_0),                  // input [31:0]
  .awlen_0(awlen_0),                    // input [7:0]
  .awsize_0(awsize_0),                  // input [2:0]
  .awburst_0(awburst_0),                // input [1:0]
  .awlock_0(awlock_0),                  // input
  .awvalid_0(awvalid_0),                // input
  .awready_0(awready_0),                // output
  .awurgent_0(awurgent_0),              // input
  .awpoison_0(awpoison_0),              // input
  .wdata_0(wdata_0),                    // input [127:0]
  .wstrb_0(wstrb_0),                    // input [15:0]
  .wlast_0(wlast_0),                    // input
  .wvalid_0(wvalid_0),                  // input
  .wready_0(wready_0),                  // output
  .bid_0(bid_0),                        // output [7:0]
  .bresp_0(bresp_0),                    // output [1:0]
  .bvalid_0(bvalid_0),                  // output
  .bready_0(bready_0),                  // input
  .arid_0(arid_0),                      // input [7:0]
  .araddr_0(araddr_0),                  // input [31:0]
  .arlen_0(arlen_0),                    // input [7:0]
  .arsize_0(arsize_0),                  // input [2:0]
  .arburst_0(arburst_0),                // input [1:0]
  .arlock_0(arlock_0),                  // input
  .arvalid_0(arvalid_0),                // input
  .arready_0(arready_0),                // output
  .arpoison_0(arpoison_0),              // input
  .rid_0(rid_0),                        // output [7:0]
  .rdata_0(rdata_0),                    // output [127:0]
  .rresp_0(rresp_0),                    // output [1:0]
  .rlast_0(rlast_0),                    // output
  .rvalid_0(rvalid_0),                  // output
  .rready_0(rready_0),                  // input
  .arurgent_0(arurgent_0),              // input
  .csysreq_0(csysreq_0),                // input
  .csysack_0(csysack_0),                // output
  .cactive_0(cactive_0)                 // output
);
 */

class PGL22GMIGIODDR(depth : BigInt) extends GenericParameterizedBundle(depth) {
  // require((depth<=0x80000000L),"PGL22GMIGIODDR supports upto 2GB depth configuraton")
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
}

//reused directly in io bundle for sifive.blocks.devices.xilinxhmemc
trait PGL22GMIGIOClocksReset extends Bundle {
  // 外部参考时钟输入
  val pll_refclk_in = Bool(INPUT)
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
  // DDRC 低功耗响应
  val csysack_ddrc = Bool(OUTPUT)
  // DDRC 激活标志
  val cactive_ddrc = Bool(OUTPUT)
}

class PGL22GMIGIOClocksResetBundle extends PGL22GMIGIOClocksReset

//scalastyle:off
//turn off linter: blackbox name must match verilog module
class ddr3_core(depth : BigInt) extends BlackBox
{
  require((depth<=0x80000000L),"ddr3_core supports upto 2GB depth configuraton")

  val io = new PGL22GMIGIODDR(depth) with PGL22GMIGIOClocksReset {
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
  }
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
