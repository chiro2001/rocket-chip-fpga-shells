package sifive.fpgashells.shell.pango

import chisel3._
import freechips.rocketchip.diplomacy._
import sifive.fpgashells.shell._
import sifive.fpgashells.ip.pango._
import sifive.fpgashells.shell.pango.PangoShell

// Tack on cts, rts signals available on some FPGAs. They are currently unused
// by our designs.
class ShellPangoUARTPortIO(val flowControl: Boolean) extends Bundle {
  val txd = Output(Bool())
  val rxd = Input(Bool())
}

abstract class UARTBasePlacedOverlay(val name: String, val di: UARTDesignInput, val si: UARTShellInput, val flowControl: Boolean)
  extends IOPlacedOverlay[ShellPangoUARTPortIO, UARTDesignInput, UARTShellInput, UARTOverlayOutput] {
  implicit val p = di.p

  def ioFactory = new ShellPangoUARTPortIO(flowControl)

  val tluartSink = sinkScope {
    di.node.makeSink
  }

  def overlayOutput = UARTOverlayOutput()
}

abstract class UARTPangoPlacedOverlay(name: String, di: UARTDesignInput, si: UARTShellInput, flowControl: Boolean)
  extends UARTBasePlacedOverlay(name, di, si, flowControl) {
  def shell: PangoShell

  shell {
    InModuleBody {
      tluartSink.bundle.txd := io.txd
      io.rxd := tluartSink.bundle.rxd
    }
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
