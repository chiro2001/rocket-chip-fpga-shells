package sifive.fpgashells.shell.pango

import chisel3._
import chipsalliance.rocketchip.config.Parameters

class PerfUARTIO extends Bundle {
  val txd = Output(Bool())
  val rxd = Input(Bool())
}

abstract class PGL22GPerfShell(implicit val p: Parameters) extends RawModule {
  val sys_clock = IO(Input(Clock()))
  val reset = IO(Input(Bool()))
  val uart = IO(new PerfUARTIO)
}
