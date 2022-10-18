package sifive.fpgashells.shell.pangogshell

import chisel3._
import chisel3.experimental.Analog
import chisel3.{Input, RawModule}
import freechips.rocketchip.config._
import freechips.rocketchip.devices.debug._
import sifive.blocks.devices.pinctrl.BasePin
import sifive.blocks.devices.spi._
import sifive.blocks.devices.uart._
import sifive.fpgashells.ip.pango.{GTP_IOBUF, pll}

// import sifive.fpgashells.ip.xilinx.{IBUFG, IOBUF, PULLUP, mmcm, reset_sys, PowerOnResetFPGAOnly}

//-------------------------------------------------------------------------
// PGL22GShell
//-------------------------------------------------------------------------

abstract class PGL22GShell(implicit val p: Parameters) extends RawModule {

  //-----------------------------------------------------------------------
  // Interface
  //-----------------------------------------------------------------------

  // Clock & Reset
  val CLK50MHZ = IO(Input(Clock()))
  // negative available
  val ck_rst = IO(Input(Bool()))

  // Green LEDs
  // val led_0        = IO(Analog(1.W))
  // val led_1        = IO(Analog(1.W))
  // val led_2        = IO(Analog(1.W))
  // val led_3        = IO(Analog(1.W))

  // RGB LEDs, 3 pins each
  // val led0_r       = IO(Analog(1.W))
  // val led0_g       = IO(Analog(1.W))
  // val led0_b       = IO(Analog(1.W))
  //
  // val led1_r       = IO(Analog(1.W))
  // val led1_g       = IO(Analog(1.W))
  // val led1_b       = IO(Analog(1.W))
  //
  // val led2_r       = IO(Analog(1.W))
  // val led2_g       = IO(Analog(1.W))
  // val led2_b       = IO(Analog(1.W))

  // Sliding switches
  // val sw_0         = IO(Analog(1.W))
  // val sw_1         = IO(Analog(1.W))
  // val sw_2         = IO(Analog(1.W))
  // val sw_3         = IO(Analog(1.W))

  // Buttons. First 3 used as GPIO, the last is used as wakeup
  // val btn_0        = IO(Analog(1.W))
  // val btn_1        = IO(Analog(1.W))
  // val btn_2        = IO(Analog(1.W))
  // val btn_3        = IO(Analog(1.W))

  // Dedicated QSPI interface
  // val qspi_cs      = IO(Analog(1.W))
  // val qspi_sck     = IO(Analog(1.W))
  // val qspi_dq      = IO(Vec(4, Analog(1.W)))

  // UART0
  val uart_rxd_out = IO(Analog(1.W))
  val uart_txd_in = IO(Analog(1.W))

  // JA (Used for more generic GPIOs)
  // val ja_0         = IO(Analog(1.W))
  // val ja_1         = IO(Analog(1.W))
  // val ja_2         = IO(Analog(1.W))
  // val ja_3         = IO(Analog(1.W))
  // val ja_4         = IO(Analog(1.W))
  // val ja_5         = IO(Analog(1.W))
  // val ja_6         = IO(Analog(1.W))
  // val ja_7         = IO(Analog(1.W))

  // JC (used for additional debug/trace connection)
  // val jc           = IO(Vec(8, Analog(1.W)))

  // JD (used for JTAG connection)
  // val jd_0         = IO(Analog(1.W))  // TDO
  // val jd_1         = IO(Analog(1.W))  // TRST_n
  // val jd_2         = IO(Analog(1.W))  // TCK
  // val jd_4         = IO(Analog(1.W))  // TDI
  // val jd_5         = IO(Analog(1.W))  // TMS
  // val jd_6         = IO(Analog(1.W))  // SRST_n

  // ChipKit Digital I/O Pins
  // val ck_io        = IO(Vec(20, Analog(1.W)))

  // ChipKit SPI
  // val ck_miso      = IO(Analog(1.W))
  // val ck_mosi      = IO(Analog(1.W))
  // val ck_ss        = IO(Analog(1.W))
  // val ck_sck       = IO(Analog(1.W))

  //-----------------------------------------------------------------------
  // Wire declrations
  //-----------------------------------------------------------------------

  // Note: these frequencies are approximate.
  val clock_8MHz = Wire(Clock())
  val clock_32MHz = Wire(Clock())
  val clock_65MHz = Wire(Clock())

  val pll_locked = Wire(Bool())

  val reset_core = Wire(Bool())
  val reset_bus = Wire(Bool())
  val reset_periph = Wire(Bool())
  val reset_intcon_n = Wire(Bool())
  val reset_periph_n = Wire(Bool())

  // val SRST_n = Wire(Bool())

  // val dut_jtag_TCK   = Wire(Clock())
  // val dut_jtag_TMS   = Wire(Bool())
  // val dut_jtag_TDI   = Wire(Bool())
  // val dut_jtag_TDO   = Wire(Bool())
  // val dut_jtag_reset = Wire(Bool())
  // val dut_ndreset    = Wire(Bool())

  //-----------------------------------------------------------------------
  // Clock Generator
  //-----------------------------------------------------------------------
  // Mixed-mode clock generator

  val ip_pll = Module(new pll())
  ip_pll.io.clkin1 := CLK50MHZ
  clock_8MHz := ip_pll.io.clkout0 // 8.388 MHz = 32.768 kHz * 256
  clock_65MHz := ip_pll.io.clkout2 // 65 Mhz
  clock_32MHz := ip_pll.io.clkout1 // 65/2 Mhz
  pll_locked := ip_pll.io.pll_lock

  //-----------------------------------------------------------------------
  // System Reset
  //-----------------------------------------------------------------------
  // processor system reset module

  // val ip_reset_sys = Module(new reset_sys())

  // ip_reset_sys.io.slowest_sync_clk := clock_8MHz
  // ip_reset_sys.io.ext_reset_in     := ck_rst & SRST_n
  // ip_reset_sys.io.aux_reset_in     := true.B
  // ip_reset_sys.io.mb_debug_sys_rst := dut_ndreset
  // ip_reset_sys.io.dcm_locked       := mmcm_locked
  //
  // reset_core                       := ip_reset_sys.io.mb_reset
  // reset_bus                        := ip_reset_sys.io.bus_struct_reset
  // reset_periph                     := ip_reset_sys.io.peripheral_reset
  // reset_intcon_n                   := ip_reset_sys.io.interconnect_aresetn
  // reset_periph_n                   := ip_reset_sys.io.peripheral_aresetn
  reset_core := ck_rst
  reset_bus := ck_rst
  reset_periph := ck_rst
  reset_intcon_n := ck_rst
  reset_periph_n := ck_rst

  //-----------------------------------------------------------------------
  // SPI Flash
  //-----------------------------------------------------------------------

  // def connectSPIFlash(dut: HasPeripherySPIFlashModuleImp): Unit = dut.qspi.headOption.foreach {
  //   connectSPIFlash(_, dut.clock, dut.reset)
  // }
  //
  // def connectSPIFlash(qspi: SPIPortIO, clock: Clock, reset: Bool): Unit = {
  //   val qspi_pins = Wire(new SPIPins(() => {new BasePin()}, qspi.c))
  //
  //   SPIPinsFromPort(qspi_pins, qspi, clock, reset, syncStages = qspi.c.defaultSampleDel)
  //
  //   IOBUF(qspi_sck, qspi.sck)
  //   IOBUF(qspi_cs,  qspi.cs(0))
  //
  //   (qspi_dq zip qspi_pins.dq).foreach { case(a, b) => IOBUF(a, b) }
  // }

  //---------------------------------------------------------------------
  // Debug JTAG
  //---------------------------------------------------------------------

  // def connectDebugJTAG(dut: HasPeripheryDebugModuleImp): SystemJTAGIO = {
  //
  //   require(dut.debug.isDefined, "Connecting JTAG requires that debug module exists")
  //   //-------------------------------------------------------------------
  //   // JTAG Reset
  //   //-------------------------------------------------------------------
  //
  //   val jtag_power_on_reset = PowerOnResetFPGAOnly(clock_32MHz)
  //
  //   dut_jtag_reset := jtag_power_on_reset
  //
  //   //-------------------------------------------------------------------
  //   // JTAG IOBUFs
  //   //-------------------------------------------------------------------
  //
  //   dut_jtag_TCK  := IBUFG(IOBUF(jd_2).asClock)
  //
  //   dut_jtag_TMS  := IOBUF(jd_5)
  //   PULLUP(jd_5)
  //
  //   dut_jtag_TDI  := IOBUF(jd_4)
  //   PULLUP(jd_4)
  //
  //   IOBUF(jd_0, dut_jtag_TDO)
  //
  //   SRST_n := IOBUF(jd_6)
  //   PULLUP(jd_6)
  //
  //   //-------------------------------------------------------------------
  //   // JTAG PINS
  //   //-------------------------------------------------------------------
  //
  //   val djtag     = dut.debug.get.systemjtag.get
  //
  //   djtag.jtag.TCK := dut_jtag_TCK
  //   djtag.jtag.TMS := dut_jtag_TMS
  //   djtag.jtag.TDI := dut_jtag_TDI
  //   dut_jtag_TDO   := djtag.jtag.TDO.data
  //
  //   djtag.mfr_id   := p(JtagDTMKey).idcodeManufId.U(11.W)
  //   djtag.part_number := p(JtagDTMKey).idcodePartNum.U(16.W)
  //   djtag.version  := p(JtagDTMKey).idcodeVersion.U(4.W)
  //
  //   djtag.reset    := dut_jtag_reset
  //   dut_ndreset    := dut.debug.get.ndreset
  //
  //   djtag
  // }

  //---------------------------------------------------------------------
  // UART
  //---------------------------------------------------------------------

  def connectUART(dut: HasPeripheryUARTModuleImp): Unit = dut.uart.headOption.foreach(connectUART)

  def connectUART(uart: UARTPortIO): Unit = {
    GTP_IOBUF(uart_rxd_out, uart.txd)
    uart.rxd := GTP_IOBUF(uart_txd_in)
  }

}

trait Bindable {
  def bindPort(port: Data): Unit
}
import chisel3.experimental.DataMirror
object BindingModulePorts {
  def bindModulePorts[T <: Bindable]
  (self: T, source: => RawModule,
   ignoredPorts: Seq[String] = Seq("clock", "reset"),
   shouldFlipped: Seq[String] = Seq()) = {
    DataMirror.modulePorts(source).foreach(item => {
      println(s"> port ${item}")
    })
    DataMirror.modulePorts(source).foreach(item => {
      if (!ignoredPorts.contains(item._1)) {
        val port = if (item._1.contains("flipped_")) Flipped(item._2.cloneType) else item._2.cloneType
        val portName = item._1
        println(s"port name: $portName")
        port.suggestName(portName)
        // bind this port to this module
        self.bindPort(port)
        // connect this port to target module
        try {
          if (portName.contains("txd")) {
            port := item._2
          } else if (portName.contains("rxd")) {
            item._2 := port
          } else {
            try {
              port <> item._2
            } catch {
              case _: Throwable => try {
                port := item._2
              } catch {
                case _: Throwable => {
                  item._2 := port
                }
              }
            }
          }
          // self.bindPort(port)
        } catch {
          case e: Exception =>
            throw e
        }
      }
    })
  }
}

import BindingModulePorts._

class BindableModule extends Module with Bindable {
  override def bindPort(port: Data) = _bindIoInPlace(port)

  def bindingModulePorts(source: => RawModule, ignoredPorts: Seq[String] = Seq("clock", "reset")) =
    bindModulePorts(this, source, ignoredPorts = ignoredPorts)
}

class BindableRawModule extends RawModule with Bindable{
  override def bindPort(port: Data) = _bindIoInPlace(port)

  def bindingModulePorts(source: => RawModule, ignoredPorts: Seq[String] = Seq("clock", "reset")) =
    bindModulePorts(this, source, ignoredPorts = ignoredPorts)
}

class NegativeResetWrapper
(module: => RawModule, ignoredPorts: Seq[String] = Seq("clock", "reset"), moduleName: String = null)
  extends BindableRawModule {
  val clock = IO(Input(Clock()))
  val ck_rst = IO(Input(Bool()))
  var gotModuleName: Option[String] = None
  withClockAndReset(clock, !ck_rst) {
    val inner = Module(module)
    gotModuleName = Some(inner.getClass.getName.split("\\.").last + "Wrapper")
    bindingModulePorts(inner, ignoredPorts = ignoredPorts)
  }

  override def desiredName =
    if (moduleName != null) moduleName
    else if (gotModuleName.nonEmpty) gotModuleName.get
    else super.desiredName
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
