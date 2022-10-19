package sifive.fpgashells.shell.pango

import chisel3._
import chisel3.experimental.{Analog, IO, attach}
import freechips.rocketchip.config._
import freechips.rocketchip.diplomacy._
import sifive.fpgashells.clocks._
import sifive.fpgashells.devices.pango.ddr3.{PangoPGL22GMIG, PangoPGL22GMIGPads, PangoPGL22GMIGParams}
import sifive.fpgashells.ip.pango.{PowerOnResetFPGAOnly, GTP_INBUF, GTP_IOBUF}
import sifive.fpgashells.shell._

class SysClockPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: ClockInputDesignInput, val shellInput: ClockInputShellInput)
  extends SingleEndedClockInputPangoPlacedOverlay(name, designInput, shellInput)
{
  val node = shell { ClockSourceNode(freqMHz = 100, jitterPS = 50) }

  shell { InModuleBody {
    val clk: Clock = io
    shell.xdc.addPackagePin(clk, "E3")
    shell.xdc.addIOStandard(clk, "LVCMOS33")
  } }
}
class SysClockPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: ClockInputShellInput)(implicit val valName: ValName)
  extends ClockInputShellPlacer[PGL22GShellBasicOverlays] {
  def place(designInput: ClockInputDesignInput) = new SysClockPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
}

//PMOD JA used for SDIO
class SDIOPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: SPIDesignInput, val shellInput: SPIShellInput)
  extends SDIOPangoPlacedOverlay(name, designInput, shellInput)
{
  shell { InModuleBody {
    val packagePinsWithPackageIOs = Seq(("D12", IOPin(io.spi_clk)),
      ("B11", IOPin(io.spi_cs)),
      ("A11", IOPin(io.spi_dat(0))),
      ("D13", IOPin(io.spi_dat(1))),
      ("B18", IOPin(io.spi_dat(2))),
      ("G13", IOPin(io.spi_dat(3))))

    packagePinsWithPackageIOs foreach { case (pin, io) => {
      shell.xdc.addPackagePin(io, pin)
      shell.xdc.addIOStandard(io, "LVCMOS33")
      shell.xdc.addIOB(io)
    } }
    packagePinsWithPackageIOs drop 1 foreach { case (pin, io) => {
      shell.xdc.addPullup(io)
    } }
  } }
}
class SDIOPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: SPIShellInput)(implicit val valName: ValName)
  extends SPIShellPlacer[PGL22GShellBasicOverlays] {
  def place(designInput: SPIDesignInput) = new SDIOPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
}

class SPIFlashPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: SPIFlashDesignInput, val shellInput: SPIFlashShellInput)
  extends SPIFlashPangoPlacedOverlay(name, designInput, shellInput)
{

  shell { InModuleBody {
    val packagePinsWithPackageIOs = Seq(("L16", IOPin(io.qspi_sck)),
      ("L13", IOPin(io.qspi_cs)),
      ("K17", IOPin(io.qspi_dq(0))),
      ("K18", IOPin(io.qspi_dq(1))),
      ("L14", IOPin(io.qspi_dq(2))),
      ("M14", IOPin(io.qspi_dq(3))))

    packagePinsWithPackageIOs foreach { case (pin, io) => {
      shell.xdc.addPackagePin(io, pin)
      shell.xdc.addIOStandard(io, "LVCMOS33")
    } }
    packagePinsWithPackageIOs drop 1 foreach { case (pin, io) => {
      shell.xdc.addPullup(io)
    } }
  } }
}
class SPIFlashPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: SPIFlashShellInput)(implicit val valName: ValName)
  extends SPIFlashShellPlacer[PGL22GShellBasicOverlays] {
  def place(designInput: SPIFlashDesignInput) = new SPIFlashPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
}

// class TracePMODPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: TracePMODDesignInput, val shellInput: TracePMODShellInput)
//   extends TracePMODPangoPlacedOverlay(name, designInput, shellInput, packagePins = Seq("U12", "V12", "V10", "V11", "U14", "V14", "T13", "U13"))
// class TracePMODPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: TracePMODShellInput)(implicit val valName: ValName)
//   extends TracePMODShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: TracePMODDesignInput) = new TracePMODPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }
//
// class GPIOPMODPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: GPIOPMODDesignInput, val shellInput: GPIOPMODShellInput)
//   extends GPIOPMODPangoPlacedOverlay(name, designInput, shellInput)
// {
//   shell { InModuleBody {
//     val packagePinsWithPackageIOs = Seq(("E15", IOPin(io.gpio_pmod_0)), //These are PMOD B
//       ("E16", IOPin(io.gpio_pmod_1)),
//       ("D15", IOPin(io.gpio_pmod_2)),
//       ("C15", IOPin(io.gpio_pmod_3)),
//       ("J17", IOPin(io.gpio_pmod_4)),
//       ("J18", IOPin(io.gpio_pmod_5)),
//       ("K15", IOPin(io.gpio_pmod_6)),
//       ("J15", IOPin(io.gpio_pmod_7)))
//
//     packagePinsWithPackageIOs foreach { case (pin, io) => {
//       shell.xdc.addPackagePin(io, pin)
//       shell.xdc.addIOStandard(io, "LVCMOS33")
//     } }
//     packagePinsWithPackageIOs drop 7 foreach { case (pin, io) => {
//       shell.xdc.addPullup(io)
//     } }
//   } }
// }
// class GPIOPMODPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: GPIOPMODShellInput)(implicit val valName: ValName)
//   extends GPIOPMODShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: GPIOPMODDesignInput) = new GPIOPMODPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }

class UARTPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: UARTDesignInput, val shellInput: UARTShellInput)
  extends UARTPangoPlacedOverlay(name, designInput, shellInput, false)
{
  shell { InModuleBody {
    val packagePinsWithPackageIOs = Seq(("A9", IOPin(io.rxd)),
      ("D10", IOPin(io.txd)))

    packagePinsWithPackageIOs foreach { case (pin, io) => {
      shell.xdc.addPackagePin(io, pin)
      shell.xdc.addIOStandard(io, "LVCMOS33")
      shell.xdc.addIOB(io)
    } }
  } }
}
class UARTPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: UARTShellInput)(implicit val valName: ValName)
  extends UARTShellPlacer[PGL22GShellBasicOverlays] {
  def place(designInput: UARTDesignInput) = new UARTPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
}

// //LEDS - r0, g0, b0, r1, g1, b1 ..., 4 normal leds_
// object LEDPGL22GPinConstraints{
//   val pins = Seq("G6", "F6", "E1", "G3", "J4", "G4", "J3", "J2", "H4", "K1", "H6", "K2", "H5", "J5", "T9", "T10")
// }
// class LEDPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: LEDDesignInput, val shellInput: LEDShellInput)
//   extends LEDPangoPlacedOverlay(name, designInput, shellInput, packagePin = Some(LEDPGL22GPinConstraints.pins(shellInput.number)))
// class LEDPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: LEDShellInput)(implicit val valName: ValName)
//   extends LEDShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: LEDDesignInput) = new LEDPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }
//
// //SWs
// object SwitchPGL22GPinConstraints{
//   val pins = Seq("A8", "C11", "C10", "A10")
// }
// class SwitchPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: SwitchDesignInput, val shellInput: SwitchShellInput)
//   extends SwitchPangoPlacedOverlay(name, designInput, shellInput, packagePin = Some(SwitchPGL22GPinConstraints.pins(shellInput.number)))
// class SwitchPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: SwitchShellInput)(implicit val valName: ValName)
//   extends SwitchShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: SwitchDesignInput) = new SwitchPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }
//
// //Buttons
// object ButtonPGL22GPinConstraints {
//   val pins = Seq("D9", "C9", "B9", "B8")
// }
// class ButtonPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: ButtonDesignInput, val shellInput: ButtonShellInput)
//   extends ButtonPangoPlacedOverlay(name, designInput, shellInput, packagePin = Some(ButtonPGL22GPinConstraints.pins(shellInput.number)))
// class ButtonPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: ButtonShellInput)(implicit val valName: ValName)
//   extends ButtonShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: ButtonDesignInput) = new ButtonPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }
//
// class JTAGDebugBScanPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: JTAGDebugBScanDesignInput, val shellInput: JTAGDebugBScanShellInput)
//   extends JTAGDebugBScanPangoPlacedOverlay(name, designInput, shellInput)
// class JTAGDebugBScanPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: JTAGDebugBScanShellInput)(implicit val valName: ValName)
//   extends JTAGDebugBScanShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: JTAGDebugBScanDesignInput) = new JTAGDebugBScanPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }

// PMOD JD used for JTAG
class JTAGDebugPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: JTAGDebugDesignInput, val shellInput: JTAGDebugShellInput)
  extends JTAGDebugPangoPlacedOverlay(name, designInput, shellInput)
{
  shell { InModuleBody {
    shell.sdc.addClock("JTCK", IOPin(io.jtag_TCK), 10)
    shell.sdc.addGroup(clocks = Seq("JTCK"))
    shell.xdc.clockDedicatedRouteFalse(IOPin(io.jtag_TCK))
    val packagePinsWithPackageIOs = Seq(("F4", IOPin(io.jtag_TCK)),  //pin JD-3
      ("D2", IOPin(io.jtag_TMS)),  //pin JD-8
      ("E2", IOPin(io.jtag_TDI)),  //pin JD-7
      ("D4", IOPin(io.jtag_TDO)),  //pin JD-1
      ("H2", IOPin(io.srst_n)))

    packagePinsWithPackageIOs foreach { case (pin, io) => {
      shell.xdc.addPackagePin(io, pin)
      shell.xdc.addIOStandard(io, "LVCMOS33")
      shell.xdc.addPullup(io)
    } }
  } }
}
class JTAGDebugPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: JTAGDebugShellInput)(implicit val valName: ValName)
  extends JTAGDebugShellPlacer[PGL22GShellBasicOverlays] {
  def place(designInput: JTAGDebugDesignInput) = new JTAGDebugPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
}

// //cjtag
// class cJTAGDebugPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: cJTAGDebugDesignInput, val shellInput: cJTAGDebugShellInput)
//   extends cJTAGDebugPangoPlacedOverlay(name, designInput, shellInput)
// {
//   shell { InModuleBody {
//     shell.sdc.addClock("JTCKC", IOPin(io.cjtag_TCKC), 10)
//     shell.sdc.addGroup(clocks = Seq("JTCKC"))
//     shell.xdc.clockDedicatedRouteFalse(IOPin(io.cjtag_TCKC))
//     val packagePinsWithPackageIOs = Seq(("F4", IOPin(io.cjtag_TCKC)),  //pin JD-3
//       ("D2", IOPin(io.cjtag_TMSC)),  //pin JD-8
//       ("H2", IOPin(io.srst_n)))
//
//     packagePinsWithPackageIOs foreach { case (pin, io) => {
//       shell.xdc.addPackagePin(io, pin)
//       shell.xdc.addIOStandard(io, "LVCMOS33")
//     } }
//     shell.xdc.addPullup(IOPin(io.cjtag_TCKC))
//     shell.xdc.addPullup(IOPin(io.srst_n))
//   } }
// }
// class cJTAGDebugPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: cJTAGDebugShellInput)(implicit val valName: ValName)
//   extends cJTAGDebugShellPlacer[PGL22GShellBasicOverlays] {
//   def place(designInput: cJTAGDebugDesignInput) = new cJTAGDebugPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
// }

case object PGL22GDDRSize extends Field[BigInt](0x10000000L / 2) // 128 MB
class DDRPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: DDRDesignInput, val shellInput: DDRShellInput)
  extends DDRPlacedOverlay[PangoPGL22GMIGPads](name, designInput, shellInput)
{
  val size = p(PGL22GDDRSize)

  val ddrClk1 = shell { ClockSinkNode(freqMHz = 50)}
  val ddrGroup = shell { ClockGroup() }
  ddrClk1 := di.wrangler := ddrGroup := di.corePLL

  val migParams = PangoPGL22GMIGParams(address = AddressSet.misaligned(di.baseAddress, size))
  val mig = LazyModule(new PangoPGL22GMIG(migParams))
  val ioNode = BundleBridgeSource(() => mig.module.io.cloneType)
  val topIONode = shell { ioNode.makeSink() }
  val ddrUI     = shell { ClockSourceNode(freqMHz = 100) }
  val areset    = shell { ClockSinkNode(Seq(ClockSinkParameters())) }
  areset := di.wrangler := ddrUI

  def overlayOutput = DDROverlayOutput(ddr = mig.node)
  def ioFactory = new PangoPGL22GMIGPads(size)

  InModuleBody { ioNode.bundle <> mig.module.io }

  shell { InModuleBody {
    require (shell.sys_clock.get.isDefined, "Use of DDRPGL22GPlacedOverlay depends on SysClockPGL22GPlacedOverlay")
    val (sys, _) = shell.sys_clock.get.get.overlayOutput.node.out(0)
    val (ui, _) = ddrUI.out(0)
    val (dclk1, _) = ddrClk1.in(0)
    val (ar, _) = areset.in(0)
    val port = topIONode.bundle.port

    io <> port
    ui.clock := port.pll_aclk_0
    ui.reset := !port.pll_lock
    port.pll_refclk_in := dclk1.clock.asUInt
    port.ddrc_rst := shell.pllReset
    port.ddr_rstn_key := !ar.reset
  } }

  shell.sdc.addGroup(clocks = Seq("clk_pll_i"), pins = Seq(mig.island.module.blackbox.io.pll_aclk_0))
}
class DDRPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: DDRShellInput)(implicit val valName: ValName)
  extends DDRShellPlacer[PGL22GShellBasicOverlays] {
  def place(designInput: DDRDesignInput) = new DDRPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
}

//Core to shell external resets
class CTSResetPGL22GPlacedOverlay(val shell: PGL22GShellBasicOverlays, name: String, val designInput: CTSResetDesignInput, val shellInput: CTSResetShellInput)
  extends CTSResetPlacedOverlay(name, designInput, shellInput)
class CTSResetPGL22GShellPlacer(val shell: PGL22GShellBasicOverlays, val shellInput: CTSResetShellInput)(implicit val valName: ValName)
  extends CTSResetShellPlacer[PGL22GShellBasicOverlays] {
  def place(designInput: CTSResetDesignInput) = new CTSResetPGL22GPlacedOverlay(shell, valName.name, designInput, shellInput)
}
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

    val iobuf = Module(new GTP_IOBUF)
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

abstract class PGL22GShellBasicOverlays()(implicit p: Parameters) extends PangoPGL22GShell {
  override val pllReset = InModuleBody { Wire(Bool()) }
  // Order matters; ddr depends on sys_clock
  val sys_clock = Overlay(ClockInputOverlayKey, new SysClockPGL22GShellPlacer(this, ClockInputShellInput()))
  // val led       = Seq.tabulate(16)(i => Overlay(LEDOverlayKey, new LEDPGL22GShellPlacer(this, LEDMetas(i))(valName = ValName(s"led_$i"))))
  // val switch    = Seq.tabulate(4)(i => Overlay(SwitchOverlayKey, new SwitchPGL22GShellPlacer(this, SwitchShellInput(number = i))(valName = ValName(s"switch_$i"))))
  // val button    = Seq.tabulate(4)(i => Overlay(ButtonOverlayKey, new ButtonPGL22GShellPlacer(this, ButtonShellInput(number = i))(valName = ValName(s"button_$i"))))
  val ddr       = Overlay(DDROverlayKey, new DDRPGL22GShellPlacer(this, DDRShellInput()))
  val uart      = Overlay(UARTOverlayKey, new UARTPGL22GShellPlacer(this, UARTShellInput()))
  val sdio      = Overlay(SPIOverlayKey, new SDIOPGL22GShellPlacer(this, SPIShellInput()))
  val jtag      = Overlay(JTAGDebugOverlayKey, new JTAGDebugPGL22GShellPlacer(this, JTAGDebugShellInput()))
  // val cjtag     = Overlay(cJTAGDebugOverlayKey, new cJTAGDebugPGL22GShellPlacer(this, cJTAGDebugShellInput()))
  val spi_flash = Overlay(SPIFlashOverlayKey, new SPIFlashPGL22GShellPlacer(this, SPIFlashShellInput()))
  val cts_reset = Overlay(CTSResetOverlayKey, new CTSResetPGL22GShellPlacer(this, CTSResetShellInput()))
  // val jtagBScan = Overlay(JTAGDebugBScanOverlayKey, new JTAGDebugBScanPGL22GShellPlacer(this, JTAGDebugBScanShellInput()))
  val chiplink  = Overlay(ChipLinkOverlayKey, new ChipLinkPGL22GShellPlacer(this, ChipLinkShellInput()))

  def LEDMetas(i: Int): LEDShellInput =
    LEDShellInput(
      color = if((i < 12) && (i % 3 == 1)) "green" else if((i < 12) && (i % 3 == 2)) "blue" else "red",
      rgb = (i < 12),
      number = i)
}

class PGL22GShell()(implicit p: Parameters) extends PGL22GShellBasicOverlays
{
  // PLL reset causes
  override val pllReset = InModuleBody { Wire(Bool()) }

  val topDesign = LazyModule(p(DesignKey)(designParameters))

  // Place the sys_clock at the Shell if the user didn't ask for it
  p(ClockInputOverlayKey).foreach(_.place(ClockInputDesignInput()))

  override lazy val module = new LazyRawModuleImp(this) {
    val reset = IO(Input(Bool()))
    xdc.addBoardPin(reset, "reset")

    val reset_ibuf = Module(new GTP_INBUF)
    reset_ibuf.io.I := reset
    val sysclk: Clock = sys_clock.get() match {
      case Some(x: SysClockPGL22GPlacedOverlay) => x.clock
    }
    val powerOnReset = PowerOnResetFPGAOnly(sysclk)
    sdc.addAsyncPath(Seq(powerOnReset))

    pllReset :=
      (!reset_ibuf.io.O) || powerOnReset //PGL22G is active low reset
  }
}

class PGL22GShellGPIOPMOD()(implicit p: Parameters) extends PGL22GShellBasicOverlays
  //This is the Shell used for coreip arty builds, with GPIOS and trace signals on the pmods
{
  // PLL reset causes
  override val pllReset = InModuleBody { Wire(Bool()) }

  // val gpio_pmod = Overlay(GPIOPMODOverlayKey, new GPIOPMODPGL22GShellPlacer(this, GPIOPMODShellInput()))
  // val trace_pmod = Overlay(TracePMODOverlayKey, new TracePMODPGL22GShellPlacer(this, TracePMODShellInput()))

  val topDesign = LazyModule(p(DesignKey)(designParameters))

  // Place the sys_clock at the Shell if the user didn't ask for it
  p(ClockInputOverlayKey).foreach(_.place(ClockInputDesignInput()))

  override lazy val module = new LazyRawModuleImp(this) {
    val reset = IO(Input(Bool()))
    xdc.addBoardPin(reset, "reset")

    val reset_ibuf = Module(new GTP_INBUF)
    reset_ibuf.io.I := reset

    val sysclk: Clock = sys_clock.get() match {
      case Some(x: SysClockPGL22GPlacedOverlay) => x.clock
    }
    val powerOnReset = PowerOnResetFPGAOnly(sysclk)
    sdc.addAsyncPath(Seq(powerOnReset))
    val ctsReset: Bool = cts_reset.get() match {
      case Some(x: CTSResetPGL22GPlacedOverlay) => x.designInput.rst
      case None => false.B
    }

    pllReset :=
      (!reset_ibuf.io.O) || powerOnReset || ctsReset //PGL22G is active low reset
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
