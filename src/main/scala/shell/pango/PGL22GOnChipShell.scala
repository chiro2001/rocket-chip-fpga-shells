package shell.pango

import chipsalliance.rocketchip.config.Parameters
import chisel3._
import sifive.fpgashells.shell.{JTAGDebugOverlayKey, JTAGDebugShellInput}
import sifive.fpgashells.shell.pango.{JTAGDebugPGL22GShellPlacer, PGL22GPerfShell, PGL22GShellBasicOverlays}

abstract class PGL22GOnChipShell(implicit p: Parameters) extends PGL22GShellBasicOverlays {
  val jtag = Overlay(JTAGDebugOverlayKey, new JTAGDebugPGL22GShellPlacer(this, JTAGDebugShellInput()))
}
