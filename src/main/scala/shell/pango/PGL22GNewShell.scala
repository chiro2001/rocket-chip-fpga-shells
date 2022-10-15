package sifive.fpgashells.shell.pango.pgl22gshell

import chisel3._
import chisel3.experimental.{attach, Analog, IO}
import freechips.rocketchip.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.util.SyncResetSynchronizerShiftReg
import sifive.fpgashells.clocks._
import sifive.fpgashells.shell._
// import sifive.fpgashells.ip.xilinx._
import sifive.fpgashells.ip.pango._
import sifive.fpgashells.shell.pango._
import sifive.blocks.devices.chiplink._
import sifive.fpgashells.devices.pango.ddr3._
// import sifive.fpgashells.devices.xilinx.xdma._
// import sifive.fpgashells.ip.xilinx.xxv_ethernet._

class SysClockPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: ClockInputDesignInput, val shellInput: ClockInputShellInput)
  extends SingleEndedClockInputPangoPlacedOverlay(name, designInput, shellInput)
{
  val node = shell { ClockSourceNode(freqMHz = 250, jitterPS = 50)(ValName(name)) }

  shell { InModuleBody {
    // shell.xdc.addPackagePin(io.p, "E12")
    // shell.xdc.addPackagePin(io.n, "D12")
    // shell.xdc.addIOStandard(io.p, "DIFF_SSTL12")
    // shell.xdc.addIOStandard(io.n, "DIFF_SSTL12")
    shell.xdc.addPackagePin(io.asBool, "E12")
    shell.xdc.addIOStandard(io.asBool, "CMOS33")
  } }
}
class SysClockPGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: ClockInputShellInput)(implicit val valName: ValName)
  extends ClockInputShellPlacer[PGL22GShellBasicOverlays]
{
    def place(designInput: ClockInputDesignInput) = new SysClockPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
}

class RefClockPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: ClockInputDesignInput, val shellInput: ClockInputShellInput)
  extends SingleEndedClockInputPangoPlacedOverlay(name, designInput, shellInput) {
  val node = shell { ClockSourceNode(freqMHz = 125, jitterPS = 50)(ValName(name)) }

  shell { InModuleBody {
    // shell.xdc.addPackagePin(io.p, "AY24")
    // shell.xdc.addPackagePin(io.n, "AY23")
    // shell.xdc.addIOStandard(io.p, "LVDS")
    // shell.xdc.addIOStandard(io.n, "LVDS")
    shell.xdc.addPackagePin(io.asBool, "E13")
    shell.xdc.addIOStandard(io.asBool, "CMOS33")
  } }
}
class RefClockPGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: ClockInputShellInput)(implicit val valName: ValName)
  extends ClockInputShellPlacer[PGL22GShellBasicOverlays] {
  def place(designInput: ClockInputDesignInput) = new RefClockPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
}

// class SDIOPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: SPIDesignInput, val shellInput: SPIShellInput)
//   extends SDIOPangoPlacedOverlay(name, designInput, shellInput)
// {
//   shell { InModuleBody {
//     val packagePinsWithPackageIOs = Seq(("AV15", IOPin(io.spi_clk)),
//                                         ("AY15", IOPin(io.spi_cs)),
//                                         ("AW15", IOPin(io.spi_dat(0))),
//                                         ("AV16", IOPin(io.spi_dat(1))),
//                                         ("AU16", IOPin(io.spi_dat(2))),
//                                         ("AY14", IOPin(io.spi_dat(3))))
//
//     packagePinsWithPackageIOs foreach { case (pin, io) => {
//       shell.xdc.addPackagePin(io, pin)
//       shell.xdc.addIOStandard(io, "LVCMOS18")
//     } }
//     packagePinsWithPackageIOs drop 1 foreach { case (pin, io) => {
//       shell.xdc.addPullup(io)
//       shell.xdc.addIOB(io)
//     } }
//   } }
// }
// class SDIOPGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: SPIShellInput)(implicit val valName: ValName)
//   extends SPIShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: SPIDesignInput) = new SDIOPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }
//
// class SPIFlashPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: SPIFlashDesignInput, val shellInput: SPIFlashShellInput)
//   extends SPIFlashPangoPlacedOverlay(name, designInput, shellInput)
// {
//
//   shell { InModuleBody {
//     /*val packagePinsWithPackageIOs = Seq(("AF13", IOPin(io.qspi_sck)),
//       ("AJ11", IOPin(io.qspi_cs)),
//       ("AP11", IOPin(io.qspi_dq(0))),
//       ("AN11", IOPin(io.qspi_dq(1))),
//       ("AM11", IOPin(io.qspi_dq(2))),
//       ("AL11", IOPin(io.qspi_dq(3))))
//
//     packagePinsWithPackageIOs foreach { case (pin, io) => {
//       shell.xdc.addPackagePin(io, pin)
//       shell.xdc.addIOStandard(io, "LVCMOS18")
//       shell.xdc.addIOB(io)
//     } }
//     packagePinsWithPackageIOs drop 1 foreach { case (pin, io) => {
//       shell.xdc.addPullup(io)
//     } }
// */
//   } }
// }
// class SPIFlashPGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: SPIFlashShellInput)(implicit val valName: ValName)
//   extends SPIFlashShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: SPIFlashDesignInput) = new SPIFlashPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }

class UARTPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: UARTDesignInput, val shellInput: UARTShellInput)
  extends UARTPangoPlacedOverlay(name, designInput, shellInput, true)
{
  shell { InModuleBody {
    val packagePinsWithPackageIOs = Seq(("AY25", IOPin(io.ctsn.get)),
                                        ("BB22", IOPin(io.rtsn.get)),
                                        ("AW25", IOPin(io.rxd)),
                                        ("BB21", IOPin(io.txd)))

    packagePinsWithPackageIOs foreach { case (pin, io) => {
      shell.xdc.addPackagePin(io, pin)
      shell.xdc.addIOStandard(io, "LVCMOS18")
      shell.xdc.addIOB(io)
    } }
  } }
}
class UARTPGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: UARTShellInput)(implicit val valName: ValName)
  extends UARTShellPlacer[PGL22GShellBasicOverlays] {
  def place(designInput: UARTDesignInput) = new UARTPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
}

// class QSFP1PGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: EthernetDesignInput, val shellInput: EthernetShellInput)
//   extends EthernetUltraScalePlacedOverlay(name, designInput, shellInput, XXVEthernetParams(name = name, speed   = 10, dclkMHz = 125))
// {
//   val dclkSource = shell { BundleBridgeSource(() => Clock()) }
//   val dclkSink = dclkSource.makeSink()
//   InModuleBody {
//     dclk := dclkSink.bundle
//   }
//   shell { InModuleBody {
//     dclkSource.bundle := shell.ref_clock.get.get.overlayOutput.node.out(0)._1.clock
//     shell.xdc.addPackagePin(io.tx_p, "V7")
//     shell.xdc.addPackagePin(io.tx_n, "V6")
//     shell.xdc.addPackagePin(io.rx_p, "Y2")
//     shell.xdc.addPackagePin(io.rx_n, "Y1")
//     shell.xdc.addPackagePin(io.refclk_p, "W9")
//     shell.xdc.addPackagePin(io.refclk_n, "W8")
//   } }
// }
// class QSFP1PGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: EthernetShellInput)(implicit val valName: ValName)
//   extends EthernetShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: EthernetDesignInput) = new QSFP1PGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }
//
// class QSFP2PGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: EthernetDesignInput, val shellInput: EthernetShellInput)
//   extends EthernetUltraScalePlacedOverlay(name, designInput, shellInput, XXVEthernetParams(name = name, speed   = 10, dclkMHz = 125))
// {
//   val dclkSource = shell { BundleBridgeSource(() => Clock()) }
//   val dclkSink = dclkSource.makeSink()
//   InModuleBody {
//     dclk := dclkSink.bundle
//   }
//   shell { InModuleBody {
//     dclkSource.bundle := shell.ref_clock.get.get.overlayOutput.node.out(0)._1.clock
//     shell.xdc.addPackagePin(io.tx_p, "L5")
//     shell.xdc.addPackagePin(io.tx_n, "L4")
//     shell.xdc.addPackagePin(io.rx_p, "T2")
//     shell.xdc.addPackagePin(io.rx_n, "T1")
//     shell.xdc.addPackagePin(io.refclk_p, "R9")
//     shell.xdc.addPackagePin(io.refclk_n, "R8")
//   } }
// }
// class QSFP2PGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: EthernetShellInput)(implicit val valName: ValName)
//   extends EthernetShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: EthernetDesignInput) = new QSFP2PGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }

// object LEDPGL22GPinConstraints {
//   val pins = Seq("AT32", "AV34", "AY30", "BB32", "BF32", "AU37", "AV36", "BA37")
// }
// class LEDPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: LEDDesignInput, val shellInput: LEDShellInput)
//   extends LEDPangoPlacedOverlay(name, designInput, shellInput, packagePin = Some(LEDPGL22GPinConstraints.pins(shellInput.number)), ioStandard = "LVCMOS12")
// class LEDPGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: LEDShellInput)(implicit val valName: ValName)
//   extends LEDShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: LEDDesignInput) = new LEDPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }

// object ButtonPGL22GPinConstraints {
//   val pins = Seq("BB24", "BE23", "BF22", "BE22", "BD23")
// }
// class ButtonPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: ButtonDesignInput, val shellInput: ButtonShellInput)
//   extends ButtonPangoPlacedOverlay(name, designInput, shellInput, packagePin = Some(ButtonPGL22GPinConstraints.pins(shellInput.number)), ioStandard = "LVCMOS18")
// class ButtonPGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: ButtonShellInput)(implicit val valName: ValName)
//   extends ButtonShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: ButtonDesignInput) = new ButtonPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }

// object SwitchPGL22GPinConstraints {
//   val pins = Seq("B17", "G16", "J16", "D21")
// }
// class SwitchPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: SwitchDesignInput, val shellInput: SwitchShellInput)
//   extends SwitchPangoPlacedOverlay(name, designInput, shellInput, packagePin = Some(SwitchPGL22GPinConstraints.pins(shellInput.number)), ioStandard = "LVCMOS12")
// class SwitchPGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: SwitchShellInput)(implicit val valName: ValName)
//   extends SwitchShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: SwitchDesignInput) = new SwitchPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }

class ChipLinkPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: ChipLinkDesignInput, val shellInput: ChipLinkShellInput)
  extends ChipLinkPangoPlacedOverlay(name, designInput, shellInput, rxPhase= -120, txPhase= -90, rxMargin=0.6, txMargin=0.5)
{
  val ereset_n = shell { InModuleBody {
    val ereset_n = IO(Analog(1.W))
    ereset_n.suggestName("ereset_n")
    val pin = IOPin(ereset_n, 0)
    shell.xdc.addPackagePin(pin, "BC8")
    shell.xdc.addIOStandard(pin, "LVCMOS18")
    shell.xdc.addTermination(pin, "NONE")
    shell.xdc.addPullup(pin)

    val iobuf = Module(new IOBUF)
    iobuf.suggestName("chiplink_ereset_iobuf")
    attach(ereset_n, iobuf.io.IO)
    iobuf.io.T := true.B // !oe
    iobuf.io.I := false.B

    iobuf.io.O
  } }

  shell { InModuleBody {
    val dir1 = Seq("BC9", "AV8", "AV9", /* clk, rst, send */
                   "AY9",  "BA9",  "BF10", "BF9",  "BC11", "BD11", "BD12", "BE12",
                   "BF12", "BF11", "BE14", "BF14", "BD13", "BE13", "BC15", "BD15",
                   "BE15", "BF15", "BA14", "BB14", "BB13", "BB12", "BA16", "BA15",
                   "BC14", "BC13", "AY8",  "AY7",  "AW8",  "AW7",  "BB16", "BC16")
    val dir2 = Seq("AV14", "AK13", "AK14", /* clk, rst, send */
                   "AR14", "AT14", "AP12", "AR12", "AW12", "AY12", "AW11", "AY10",
                   "AU11", "AV11", "AW13", "AY13", "AN16", "AP16", "AP13", "AR13",
                   "AT12", "AU12", "AK15", "AL15", "AL14", "AM14", "AV10", "AW10",
                   "AN15", "AP15", "AK12", "AL12", "AM13", "AM12", "AJ13", "AJ12")
    (IOPin.of(io.b2c) zip dir1) foreach { case (io, pin) => shell.xdc.addPackagePin(io, pin) }
    (IOPin.of(io.c2b) zip dir2) foreach { case (io, pin) => shell.xdc.addPackagePin(io, pin) }
  } }
}
class ChipLinkPGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: ChipLinkShellInput)(implicit val valName: ValName)
  extends ChipLinkShellPlacer[PGL22GShellBasicOverlays] {
  def place(designInput: ChipLinkDesignInput) = new ChipLinkPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
}

// TODO: JTAG is untested
// class JTAGDebugPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: JTAGDebugDesignInput, val shellInput: JTAGDebugShellInput)
//   extends JTAGDebugPangoPlacedOverlay(name, designInput, shellInput)
// {
//   shell { InModuleBody {
//     val pin_locations = Map(
//       "PMOD_J52" -> Seq("AW15",      "AU16",      "AV16",      "AY14",      "AY15"),
//       "PMOD_J53" -> Seq( "N30",       "L31",       "P29",       "N28",       "M30"),
//       "FMC_J2"   -> Seq("AL12",      "AN15",      "AP15",      "AM12",      "AK12"))
//     val pins      = Seq(io.jtag_TCK, io.jtag_TMS, io.jtag_TDI, io.jtag_TDO, io.srst_n)

//     shell.sdc.addClock("JTCK", IOPin(io.jtag_TCK), 10)
//     shell.sdc.addGroup(clocks = Seq("JTCK"))
//     shell.xdc.clockDedicatedRouteFalse(IOPin(io.jtag_TCK))

//     val pin_voltage:String = if(shellInput.location.get == "PMOD_J53") "LVCMOS12" else "LVCMOS18"

//     (pin_locations(shellInput.location.get) zip pins) foreach { case (pin_location, ioport) =>
//       val io = IOPin(ioport)
//       shell.xdc.addPackagePin(io, pin_location)
//       shell.xdc.addIOStandard(io, pin_voltage)
//       shell.xdc.addPullup(io)
//       shell.xdc.addIOB(io)
//     }
//   } }
// }
// class JTAGDebugPGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: JTAGDebugShellInput)(implicit val valName: ValName)
//   extends JTAGDebugShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: JTAGDebugDesignInput) = new JTAGDebugPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }

// class cJTAGDebugPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: cJTAGDebugDesignInput, val shellInput: cJTAGDebugShellInput)
//   extends cJTAGDebugPangoPlacedOverlay(name, designInput, shellInput)
// {
//   shell { InModuleBody {
//     shell.sdc.addClock("JTCKC", IOPin(io.cjtag_TCKC), 10)
//     shell.sdc.addGroup(clocks = Seq("JTCKC"))
//     shell.xdc.clockDedicatedRouteFalse(IOPin(io.cjtag_TCKC))
//     val packagePinsWithPackageIOs = Seq(("AW11", IOPin(io.cjtag_TCKC)),
//                                         ("AP13", IOPin(io.cjtag_TMSC)),
//                                         ("AY10", IOPin(io.srst_n)))

//     packagePinsWithPackageIOs foreach { case (pin, io) => {
//       shell.xdc.addPackagePin(io, pin)
//       shell.xdc.addIOStandard(io, "LVCMOS18")
//     } }
//       shell.xdc.addPullup(IOPin(io.cjtag_TCKC))
//       shell.xdc.addPullup(IOPin(io.srst_n))
//   } }
// }
// class cJTAGDebugPGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: cJTAGDebugShellInput)(implicit val valName: ValName)
//   extends cJTAGDebugShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: cJTAGDebugDesignInput) = new cJTAGDebugPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }

// class JTAGDebugBScanPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: JTAGDebugBScanDesignInput, val shellInput: JTAGDebugBScanShellInput)
//   extends JTAGDebugBScanPangoPlacedOverlay(name, designInput, shellInput)
// class JTAGDebugBScanPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: JTAGDebugBScanShellInput)(implicit val valName: ValName)
//   extends JTAGDebugBScanShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: JTAGDebugBScanDesignInput) = new JTAGDebugBScanPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }

case object PGL22GDDRSize extends Field[BigInt](0x40000000L * 2) // 2GB
class DDRPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: DDRDesignInput, val shellInput: DDRShellInput)
  extends DDRPlacedOverlay[PangoPGL22GMIGPads](name, designInput, shellInput)
{
  val size = p(PGL22GDDRSize)

  val migParams = PangoPGL22GMIGParams(address = AddressSet.misaligned(di.baseAddress, size))
  val mig = LazyModule(new PangoPGL22GMIG(migParams))
  val ioNode = BundleBridgeSource(() => mig.module.io.cloneType)
  val topIONode = shell { ioNode.makeSink() }
  val ddrUI     = shell { ClockSourceNode(freqMHz = 200) }
  val areset    = shell { ClockSinkNode(Seq(ClockSinkParameters())) }
  areset := designInput.wrangler := ddrUI

  def overlayOutput = DDROverlayOutput(ddr = mig.node)
  def ioFactory = new PangoPGL22GMIGPads(size)

  InModuleBody { ioNode.bundle <> mig.module.io }

  shell { InModuleBody {
    require (shell.sys_clock.get.isDefined, "Use of DDRPGL22GOverlay depends on SysClockPGL22GOverlay")
    val (sys, _) = shell.sys_clock.get.get.overlayOutput.node.out(0)
    val (ui, _) = ddrUI.out(0)
    val (ar, _) = areset.in(0)
    val port = topIONode.bundle.port
    io <> port
    ui.clock := port.c0_ddr4_ui_clk
    ui.reset := /*!port.mmcm_locked ||*/ port.c0_ddr4_ui_clk_sync_rst
    port.c0_sys_clk_i := sys.clock.asUInt
    port.sys_rst := sys.reset // pllReset
    port.c0_ddr4_aresetn := !ar.reset

    val allddrpins = Seq(  "D14", "B15", "B16", "C14", "C15", "A13", "A14",
      "A15", "A16", "B12", "C12", "B13", "C13", "D15", "H14", "H15", "F15",
      "H13", "G15", "G13", "N20", "E13", "E14", "F14", "A10", "F13", "C8",
      "F11", "E11", "F10", "F9",  "H12", "G12", "E9",  "D9",  "R19", "P19",
      "M18", "M17", "N19", "N18", "N17", "M16", "L16", "K16", "L18", "K18",
      "J17", "H17", "H19", "H18", "F19", "F18", "E19", "E18", "G20", "F20",
      "E17", "D16", "D17", "C17", "C19", "C18", "D20", "D19", "C20", "B20",
      "N23", "M23", "R21", "P21", "R22", "P22", "T23", "R23", "K24", "J24",
      "M21", "L21", "K21", "J21", "K22", "J22", "H23", "H22", "E23", "E22",
      "F21", "E21", "F24", "F23", "D10", "P16", "J19", "E16", "A18", "M22",
      "L20", "G23", "D11", "P17", "K19", "F16", "A19", "N22", "M20", "H24",
      "G11", "R18", "K17", "G18", "B18", "P20", "L23", "G22")

    (IOPin.of(io) zip allddrpins) foreach { case (io, pin) => shell.xdc.addPackagePin(io, pin) }
  } }

  shell.sdc.addGroup(pins = Seq(mig.island.module.blackbox.io.c0_ddr4_ui_clk))
}
class DDRPGL22GShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: DDRShellInput)(implicit val valName: ValName)
  extends DDRShellPlacer[PGL22GShellBasicOverlays] {
  def place(designInput: DDRDesignInput) = new DDRPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
}

// class PCIePGL22GFMCPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: PCIeDesignInput, val shellInput: PCIeShellInput)
//   extends PCIeUltraScalePlacedOverlay(name, designInput, shellInput, XDMAParams(
//     name     = "fmc_xdma",
//     location = "X0Y3",
//     bars     = designInput.bars,
//     control  = designInput.ecam,
//     bases    = designInput.bases,
//     lanes    = 4))
// {
//   shell { InModuleBody {
//     // Work-around incorrectly pre-assigned pins
//     IOPin.of(io).foreach { shell.xdc.addPackagePin(_, "") }

//     // We need some way to connect both of these to reach x8
//     val ref126 = Seq("V38",  "V39")  /* [pn] GBT0 Bank 126 */
//     val ref121 = Seq("AK38", "AK39") /* [pn] GBT0 Bank 121 */
//     val ref = ref126

//     // Bank 126 (DP5, DP6, DP4, DP7), Bank 121 (DP3, DP2, DP1, DP0)
//     val rxp = Seq("U45", "R45", "W45", "N45", "AJ45", "AL45", "AN45", "AR45") /* [0-7] */
//     val rxn = Seq("U46", "R46", "W46", "N46", "AJ46", "AL46", "AN46", "AR46") /* [0-7] */
//     val txp = Seq("P42", "M42", "T42", "K42", "AL40", "AM42", "AP42", "AT42") /* [0-7] */
//     val txn = Seq("P43", "M43", "T43", "K43", "AL41", "AM43", "AP43", "AT43") /* [0-7] */

//     def bind(io: Seq[IOPin], pad: Seq[String]) {
//       (io zip pad) foreach { case (io, pad) => shell.xdc.addPackagePin(io, pad) }
//     }

//     bind(IOPin.of(io.refclk), ref)
//     // We do these individually so that zip falls off the end of the lanes:
//     bind(IOPin.of(io.lanes.pci_exp_txp), txp)
//     bind(IOPin.of(io.lanes.pci_exp_txn), txn)
//     bind(IOPin.of(io.lanes.pci_exp_rxp), rxp)
//     bind(IOPin.of(io.lanes.pci_exp_rxn), rxn)
//   } }
// }
// class PCIePGL22GFMCShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: PCIeShellInput)(implicit val valName: ValName)
//   extends PCIeShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: PCIeDesignInput) = new PCIePGL22GFMCPlacedOverlay(shell, valName.name, designInput, shellInput)
// }

// class PCIePGL22GEdgePlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: PCIeDesignInput, val shellInput: PCIeShellInput)
//   extends PCIeUltraScalePlacedOverlay(name, designInput, shellInput, XDMAParams(
//     name     = "edge_xdma",
//     location = "X1Y2",
//     bars     = designInput.bars,
//     control  = designInput.ecam,
//     bases    = designInput.bases,
//     lanes    = 8))
// {
//   shell { InModuleBody {
//     // Work-around incorrectly pre-assigned pins
//     IOPin.of(io).foreach { shell.xdc.addPackagePin(_, "") }

//     // PCIe Edge connector U2
//     //   Lanes 00-03 Bank 227
//     //   Lanes 04-07 Bank 226
//     //   Lanes 08-11 Bank 225
//     //   Lanes 12-15 Bank 224

//     // FMC+ J22
//     val ref227 = Seq("AC9", "AC8")  /* [pn]  Bank 227 PCIE_CLK2_*/
//     val ref = ref227

//     // PCIe Edge connector U2 : Bank 227, 226
//     val rxp = Seq("AA4", "AB2", "AC4", "AD2", "AE4", "AF2", "AG4", "AH2") // [0-7]
//     val rxn = Seq("AA3", "AB1", "AC3", "AD1", "AE3", "AF1", "AG3", "AH1") // [0-7]
//     val txp = Seq("Y7", "AB7", "AD7", "AF7", "AH7", "AK7", "AM7", "AN5") // [0-7]
//     val txn = Seq("Y6", "AB6", "AD6", "AF6", "AH6", "AK6", "AM6", "AN4") // [0-7]

//     def bind(io: Seq[IOPin], pad: Seq[String]) {
//       (io zip pad) foreach { case (io, pad) => shell.xdc.addPackagePin(io, pad) }
//     }

//     bind(IOPin.of(io.refclk), ref)
//     // We do these individually so that zip falls off the end of the lanes:
//     bind(IOPin.of(io.lanes.pci_exp_txp), txp)
//     bind(IOPin.of(io.lanes.pci_exp_txn), txn)
//     bind(IOPin.of(io.lanes.pci_exp_rxp), rxp)
//     bind(IOPin.of(io.lanes.pci_exp_rxn), rxn)
//   } }
// }
// class PCIePGL22GEdgeShellPlacer(shell: PGL22GShellBasicOverlays, val shellInput: PCIeShellInput)(implicit val valName: ValName)
//   extends PCIeShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: PCIeDesignInput) = new PCIePGL22GEdgePlacedOverlay(shell, valName.name, designInput, shellInput)
// }

abstract class PGL22GShellBasicOverlays()(implicit p: Parameters) extends UltraScaleShell{
  // PLL reset causes
  val pllReset = InModuleBody { Wire(Bool()) }

  val sys_clock = Overlay(ClockInputOverlayKey, new SysClockPGL22GShellPlacer(this, ClockInputShellInput()))
  val ref_clock = Overlay(ClockInputOverlayKey, new RefClockPGL22GShellPlacer(this, ClockInputShellInput()))
  // val led       = Seq.tabulate(8)(i => Overlay(LEDOverlayKey, new LEDPGL22GShellPlacer(this, LEDShellInput(color = "red", number = i))(valName = ValName(s"led_$i"))))
  // val switch    = Seq.tabulate(4)(i => Overlay(SwitchOverlayKey, new SwitchPGL22GShellPlacer(this, SwitchShellInput(number = i))(valName = ValName(s"switch_$i"))))
  // val button    = Seq.tabulate(5)(i => Overlay(ButtonOverlayKey, new ButtonPGL22GShellPlacer(this, ButtonShellInput(number = i))(valName = ValName(s"button_$i"))))
  val ddr       = Overlay(DDROverlayKey, new DDRPGL22GShellPlacer(this, DDRShellInput()))
  // val qsfp1     = Overlay(EthernetOverlayKey, new QSFP1PGL22GShellPlacer(this, EthernetShellInput()))
  // val qsfp2     = Overlay(EthernetOverlayKey, new QSFP2PGL22GShellPlacer(this, EthernetShellInput()))
  val chiplink  = Overlay(ChipLinkOverlayKey, new ChipLinkPGL22GShellPlacer(this, ChipLinkShellInput()))
  //val spi_flash = Overlay(SPIFlashOverlayKey, new SPIFlashPGL22GShellPlacer(this, SPIFlashShellInput()))
  //SPI Flash not functional
}

case object PGL22GShellPMOD extends Field[String]("JTAG")
case object PGL22GShellPMOD2 extends Field[String]("JTAG")

class WithPGL22GShellPMOD(device: String) extends Config((site, here, up) => {
  case PGL22GShellPMOD => device
})

// Change JTAG pinouts to PGL22G J53
// Due to the level shifter is from 1.2V to 3.3V, the frequency of JTAG should be slow down to 1Mhz
// class WithPGL22GShellPMOD2(device: String) extends Config((site, here, up) => {
//   case PGL22GShellPMOD2 => device
// })

// class WithPGL22GShellPMODJTAG extends WithPGL22GShellPMOD("JTAG")
// class WithPGL22GShellPMODSDIO extends WithPGL22GShellPMOD("SDIO")

// // Reassign JTAG pinouts location to PMOD J53
// class WithPGL22GShellPMOD2JTAG extends WithPGL22GShellPMOD2("PMODJ53_JTAG")

class PGL22GShell()(implicit p: Parameters) extends PGL22GShellBasicOverlays
{
  // val pmod_is_sdio  = p(PGL22GShellPMOD) == "SDIO"
  // val pmod_j53_is_jtag = p(PGL22GShellPMOD2) == "PMODJ53_JTAG"
  // val jtag_location = Some(if (pmod_is_sdio) (if (pmod_j53_is_jtag) "PMOD_J53" else "FMC_J2") else "PMOD_J52")

  // Order matters; ddr depends on sys_clock
  val uart      = Overlay(UARTOverlayKey, new UARTPGL22GShellPlacer(this, UARTShellInput()))
  // val sdio      = if (pmod_is_sdio) Some(Overlay(SPIOverlayKey, new SDIOPGL22GShellPlacer(this, SPIShellInput()))) else None
  // val jtag      = Overlay(JTAGDebugOverlayKey, new JTAGDebugPGL22GShellPlacer(this, JTAGDebugShellInput(location = jtag_location)))
  // val cjtag     = Overlay(cJTAGDebugOverlayKey, new cJTAGDebugPGL22GShellPlacer(this, cJTAGDebugShellInput()))
  // val jtagBScan = Overlay(JTAGDebugBScanOverlayKey, new JTAGDebugBScanPGL22GShellPlacer(this, JTAGDebugBScanShellInput()))
  // val fmc       = Overlay(PCIeOverlayKey, new PCIePGL22GFMCShellPlacer(this, PCIeShellInput()))
  // val edge      = Overlay(PCIeOverlayKey, new PCIePGL22GEdgeShellPlacer(this, PCIeShellInput()))

  val topDesign = LazyModule(p(DesignKey)(designParameters))

  // Place the sys_clock at the Shell if the user didn't ask for it
  designParameters(ClockInputOverlayKey).foreach { unused =>
    val source = unused.place(ClockInputDesignInput()).overlayOutput.node
    val sink = ClockSinkNode(Seq(ClockSinkParameters()))
    sink := source
  }

  override lazy val module = new LazyRawModuleImp(this) {
    val reset = IO(Input(Bool()))
    xdc.addPackagePin(reset, "L19")
    xdc.addIOStandard(reset, "LVCMOS12")

    val reset_ibuf = Module(new IBUF)
    reset_ibuf.io.I := reset

    val sysclk: Clock = sys_clock.get() match {
      case Some(x: SysClockPGL22GPlacedOverlay) => x.clock
    }

    val powerOnReset: Bool = PowerOnResetFPGAOnly(sysclk)
    sdc.addAsyncPath(Seq(powerOnReset))

    val ereset: Bool = chiplink.get() match {
      case Some(x: ChipLinkPGL22GPlacedOverlay) => !x.ereset_n
      case _ => false.B
    }
    // val ereset: Bool = false.B

    pllReset := (reset_ibuf.io.O || powerOnReset || ereset)
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
