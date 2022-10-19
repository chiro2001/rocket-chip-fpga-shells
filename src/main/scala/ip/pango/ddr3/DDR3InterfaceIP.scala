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

/**
 *
 * @param depth
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

//scalastyle:off
//turn off linter: blackbox name must match verilog module
class ddr3_core(depth : BigInt)(implicit val p:Parameters) extends BlackBox
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
    // val awcache_0 = Bits(INPUT, 4)
    // val awprot_0 = Bits(INPUT, 3)
    // val awqos_0 = Bits(INPUT, 4)
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
    // val arcache_0 = Bits(INPUT, 4)
    // val arprot_0 = Bits(INPUT, 3)
    // val arqos_0 = Bits(INPUT, 4)
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

  ElaborationArtefacts.add(
    "ddr3_core.vivado.tcl",
    """ 
      create_ip -vendor xilinx.com -library ip -version 2.2 -name ddr4 -module_name ddr3_core -dir $ipdir -force
      set_property -dict [list \
      CONFIG.AL_SEL                               {0} \
      CONFIG.C0.ADDR_WIDTH                        {17} \
      CONFIG.C0.BANK_GROUP_WIDTH                  {1} \
      CONFIG.C0.CKE_WIDTH                         {1} \
      CONFIG.C0.CK_WIDTH                          {1} \
      CONFIG.C0.CS_WIDTH                          {1} \
      CONFIG.C0.ControllerType                    {DDR4_SDRAM} \
      CONFIG.C0.DDR4_AUTO_AP_COL_A3               {false} \
      CONFIG.C0.DDR4_AutoPrecharge                {false} \
      CONFIG.C0.DDR4_AxiAddressWidth              {31} \
      CONFIG.C0.DDR4_AxiArbitrationScheme         {RD_PRI_REG} \
      CONFIG.C0.DDR4_AxiDataWidth                 {64} \
      CONFIG.C0.DDR4_AxiIDWidth                   {4} \
      CONFIG.C0.DDR4_AxiNarrowBurst               {false} \
      CONFIG.C0.DDR4_AxiSelection                 {true} \
      CONFIG.C0.DDR4_BurstLength                  {8} \
      CONFIG.C0.DDR4_BurstType                    {Sequential} \
      CONFIG.C0.DDR4_CLKFBOUT_MULT                {8} \
      CONFIG.C0.DDR4_CLKOUT0_DIVIDE               {5} \
      CONFIG.C0.DDR4_Capacity                     {512} \
      CONFIG.C0.DDR4_CasLatency                   {11} \
      CONFIG.C0.DDR4_CasWriteLatency              {9} \
      CONFIG.C0.DDR4_ChipSelect                   {true} \
      CONFIG.C0.DDR4_Clamshell                    {false} \
      CONFIG.C0.DDR4_CustomParts                  {no_file_loaded} \
      CONFIG.C0.DDR4_DIVCLK_DIVIDE                {2} \
      CONFIG.C0.DDR4_DataMask                     {DM_NO_DBI} \
      CONFIG.C0.DDR4_DataWidth                    {64} \
      CONFIG.C0.DDR4_Ecc                          {false} \
      CONFIG.C0.DDR4_MCS_ECC                      {false} \
      CONFIG.C0.DDR4_Mem_Add_Map                  {ROW_COLUMN_BANK} \
      CONFIG.C0.DDR4_MemoryName                   {MainMemory} \
      CONFIG.C0.DDR4_MemoryPart                   {MT40A256M16GE-083E} \
      CONFIG.C0.DDR4_MemoryType                   {Components} \
      CONFIG.C0.DDR4_MemoryVoltage                {1.2V} \
      CONFIG.C0.DDR4_OnDieTermination             {RZQ/6} \
      CONFIG.C0.DDR4_Ordering                     {Normal} \
      CONFIG.C0.DDR4_OutputDriverImpedenceControl {RZQ/7} \
      CONFIG.C0.DDR4_PhyClockRatio                {4:1} \
      CONFIG.C0.DDR4_SAVE_RESTORE                 {false} \
      CONFIG.C0.DDR4_SELF_REFRESH                 {false} \
      CONFIG.C0.DDR4_Slot                         {Single} \
      CONFIG.C0.DDR4_Specify_MandD                {true} \
      CONFIG.C0.DDR4_TimePeriod                   {1250} \
      CONFIG.C0.DDR4_UserRefresh_ZQCS             {false} \
      CONFIG.C0.DDR4_isCKEShared                  {false} \
      CONFIG.C0.DDR4_isCustom                     {false} \
      CONFIG.C0.LR_WIDTH                          {1} \
      CONFIG.C0.ODT_WIDTH                         {1} \
      CONFIG.C0.StackHeight                       {1} \
      CONFIG.C0_CLOCK_BOARD_INTERFACE             {Custom} \
      CONFIG.C0_DDR4_BOARD_INTERFACE              {Custom} \
      CONFIG.DCI_Cascade                          {false} \
      CONFIG.DIFF_TERM_SYSCLK                     {false} \
      CONFIG.Debug_Signal                         {Disable} \
      CONFIG.Default_Bank_Selections              {false} \
      CONFIG.Enable_SysPorts                      {true} \
      CONFIG.IOPowerReduction                     {OFF} \
      CONFIG.IO_Power_Reduction                   {false} \
      CONFIG.IS_FROM_PHY                          {1} \
      CONFIG.MCS_DBG_EN                           {false} \
      CONFIG.No_Controller                        {1} \
      CONFIG.PARTIAL_RECONFIG_FLOW_MIG            {false} \
      CONFIG.PING_PONG_PHY                        {1} \
      CONFIG.Phy_Only                             {Complete_Memory_Controller} \
      CONFIG.RECONFIG_XSDB_SAVE_RESTORE           {false} \
      CONFIG.RESET_BOARD_INTERFACE                {Custom} \
      CONFIG.Reference_Clock                      {Differential} \
      CONFIG.SET_DW_TO_40                         {false} \
      CONFIG.System_Clock                         {No_Buffer} \
      CONFIG.TIMING_3DS                           {false} \
      CONFIG.TIMING_OP1                           {false} \
      CONFIG.TIMING_OP2                           {false} \
      ] [get_ips ddr3_core]"""
  )

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
