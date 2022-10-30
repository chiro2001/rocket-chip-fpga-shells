package sifive.fpgashells.shell.pango

import chisel3._
import chisel3.util.Cat
import freechips.rocketchip.diplomacy._
import sifive.fpgashells.shell._
import sifive.fpgashells.ip.xilinx._
import sifive.fpgashells.shell.pango.PangoShell

abstract class SPIFlashPangoPlacedOverlay(name: String, di: SPIFlashDesignInput, si: SPIFlashShellInput)
  extends SPIFlashPlacedOverlay(name, di, si) {
  def shell: PangoShell

  //val dqiVec = VecInit.tabulate(4)(j =>tlqspiSink.bundle.dq(j))
  shell {
    InModuleBody {
      UIntToAnalog(tlqspiSink.bundle.sck, io.qspi_sck, true.B)
      UIntToAnalog(tlqspiSink.bundle.cs(0), io.qspi_cs, true.B)

      tlqspiSink.bundle.dq.zip(io.qspi_dq).foreach { case (design_dq, io_dq) =>
        UIntToAnalog(design_dq.o, io_dq, design_dq.oe)
        design_dq.i := AnalogToUInt(io_dq)
      }
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
