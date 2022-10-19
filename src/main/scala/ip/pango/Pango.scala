package sifive.fpgashells.ip.pango

import Chisel._
import chisel3.{Input, Output}
import chisel3.experimental.{Analog, attach}
import freechips.rocketchip.util.{ElaborationArtefacts}

import sifive.blocks.devices.pinctrl.{BasePin}
import sifive.fpgashells.clocks._
import freechips.rocketchip.diplomacy.LazyModule

//-------------------------------------------------------------------------
// PowerOnResetFPGAOnly
//-------------------------------------------------------------------------
/** PowerOnResetFPGAOnly -- this generates a power_on_reset signal using
 * initial blocks.  It is synthesizable on FPGA flows only.
 */

// This is a FPGA-Only construct, which uses
// 'initial' constructions
class PowerOnResetFPGAOnly extends BlackBox {
  val io = new Bundle {
    val clock = Input(Clock())
    val power_on_reset = Output(Bool())
  }
}

object PowerOnResetFPGAOnly {
  def apply (clk: Clock, name: String): Bool = {
    val por = Module(new PowerOnResetFPGAOnly())
    por.suggestName(name)
    por.io.clock := clk
    por.io.power_on_reset
  }
  def apply (clk: Clock): Bool = apply(clk, "fpga_power_on")
}


//-------------------------------------------------------------------------
// vc707_sys_clock_mmcm
//-------------------------------------------------------------------------
//IP : pango mmcm with "NO_BUFFER" input clock
class PangoPLL(c : PLLParameters) extends BlackBox with PLLInstance {
  val io = new Bundle {
    val clkin1   = Clock(INPUT)
    val clkout0  = if (c.req.size >= 1) Some(Clock(OUTPUT)) else None
    val clkout1  = if (c.req.size >= 2) Some(Clock(OUTPUT)) else None
    val clkout2  = if (c.req.size >= 3) Some(Clock(OUTPUT)) else None
    val clkout3  = if (c.req.size >= 4) Some(Clock(OUTPUT)) else None
    val pll_rst     = Bool(INPUT)
    val pll_lock    = Bool(OUTPUT)
  }

  val moduleName = c.name
  override def desiredName = c.name

  def getClocks = Seq() ++ io.clkout0 ++ io.clkout1 ++
    io.clkout2 ++ io.clkout3
  def getInput = io.clkin1
  def getReset = Some(io.pll_rst)
  def getLocked = io.pll_lock
  def getClockNames = Seq.tabulate (c.req.size) { i =>
    s"${c.name}/inst/pango_pll_inst/CLKOUT${i}"
  }

  val used = Seq.tabulate(7) { i =>
    s" CONFIG.CLKOUT${i+1}_USED {${i < c.req.size}} \\\n"
  }.mkString

  val outputs = c.req.zipWithIndex.map { case (r, i) =>
    s""" CONFIG.CLKOUT${i+1}_REQUESTED_OUT_FREQ {${r.freqMHz}} \\
       | CONFIG.CLKOUT${i+1}_REQUESTED_PHASE {${r.phaseDeg}} \\
       | CONFIG.CLKOUT${i+1}_REQUESTED_DUTY_CYCLE {${r.dutyCycle}} \\
       |""".stripMargin
  }.mkString

  val checks = c.req.zipWithIndex.map { case (r, i) =>
    val f = if (i == 0) "_F" else ""
    val phaseMin = r.phaseDeg - r.phaseErrorDeg
    val phaseMax = r.phaseDeg + r.phaseErrorDeg
    val freqMin = r.freqMHz * (1 - r.freqErrorPPM / 1000000)
    val freqMax = r.freqMHz * (1 + r.freqErrorPPM / 1000000)
    s"""set jitter [get_property CONFIG.CLKOUT${i+1}_JITTER [get_ips ${moduleName}]]
       |if {$$jitter > ${r.jitterPS}} {
       |  puts "Output jitter $$jitter ps exceeds required limit of ${r.jitterPS}"
       |  exit 1
       |}
       |set phase [get_property CONFIG.MMCM_CLKOUT${i}_PHASE [get_ips ${moduleName}]]
       |if {$$phase < ${phaseMin} || $$phase > ${phaseMax}} {
       |  puts "Achieved phase $$phase degrees is outside tolerated range ${phaseMin}-${phaseMax}"
       |  exit 1
       |}
       |set div2 [get_property CONFIG.MMCM_CLKOUT${i}_DIVIDE${f} [get_ips ${moduleName}]]
       |set freq [expr { ${c.input.freqMHz} * $$mult / $$div1 / $$div2 }]
       |if {$$freq < ${freqMin} || $$freq > ${freqMax}} {
       |  puts "Achieved frequency $$freq MHz is outside tolerated range ${freqMin}-${freqMax}"
       |  exit 1
       |}
       |puts "Achieve frequency $$freq MHz phase $$phase degrees jitter $$jitter ps"
       |""".stripMargin
  }.mkString


  val aligned = if (c.input.feedback) " CONFIG.USE_PHASE_ALIGNMENT {true} \\\n" else ""

  ElaborationArtefacts.add(s"${moduleName}.vivado.tcl",
    s"""create_ip -name clk_wiz -vendor pango.com -library ip -module_name \\
       | ${moduleName} -dir $$ipdir -force
       |set_property -dict [list \\
       | CONFIG.CLK_IN1_BOARD_INTERFACE {Custom} \\
       | CONFIG.PRIM_SOURCE {No_buffer} \\
       | CONFIG.NUM_OUT_CLKS {${c.req.size.toString}} \\
       | CONFIG.PRIM_IN_FREQ {${c.input.freqMHz.toString}} \\
       | CONFIG.CLKIN1_JITTER_PS {${c.input.jitter}} \\
       |${used}${aligned}${outputs}] [get_ips ${moduleName}]
       |set mult [get_property CONFIG.MMCM_CLKFBOUT_MULT_F [get_ips ${moduleName}]]
       |set div1 [get_property CONFIG.MMCM_DIVCLK_DIVIDE [get_ips ${moduleName}]]
       |${checks}""".stripMargin)
}

//-------------------------------------------------------------------------
// vc707reset
//-------------------------------------------------------------------------

class vc707reset() extends BlackBox
{
  val io = new Bundle{
    val areset = Bool(INPUT)
    val clock1 = Clock(INPUT)
    val reset1 = Bool(OUTPUT)
    val clock2 = Clock(INPUT)
    val reset2 = Bool(OUTPUT)
    val clock3 = Clock(INPUT)
    val reset3 = Bool(OUTPUT)
    val clock4 = Clock(INPUT)
    val reset4 = Bool(OUTPUT)
  }
}

//-------------------------------------------------------------------------
// pgl22g_sys_clock_mmcm
//-------------------------------------------------------------------------
//IP : pango mmcm with "NO_BUFFER" input clock

//-------------------------------------------------------------------------
// pgl22greset
//-------------------------------------------------------------------------

class pgl22greset() extends BlackBox
{
  val io = new Bundle{
    val areset = Bool(INPUT)
    val clock1 = Clock(INPUT)
    val reset1 = Bool(OUTPUT)
    val clock2 = Clock(INPUT)
    val reset2 = Bool(OUTPUT)
    val clock3 = Clock(INPUT)
    val reset3 = Bool(OUTPUT)
    val clock4 = Clock(INPUT)
    val reset4 = Bool(OUTPUT)
  }
}

//-------------------------------------------------------------------------
// sdio_spi_bridge
//-------------------------------------------------------------------------

class sdio_spi_bridge() extends BlackBox
{
  val io = new Bundle{
    val clk      = Clock(INPUT)
    val reset    = Bool(INPUT)
    val sd_cmd   = Analog(1.W)
    val sd_dat   = Analog(4.W)
    val spi_sck  = Bool(INPUT)
    val spi_cs   = Bool(INPUT)
    val spi_dq_o = Bits(INPUT,4)
    val spi_dq_i = Bits(OUTPUT,4)
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
