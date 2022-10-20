package shell.pango

import chisel3._
import freechips.rocketchip.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.tilelink._
import sifive.fpgashells.clocks._
import sifive.fpgashells.shell.{DesignPlacer, IOPlacedOverlay, ShellPlacer}


case class DDRShellInputSysClk()
case class DDRDesignInputSysClk(
                                 baseAddress: BigInt,
                                 wrangler: ClockAdapterNode,
                                 clockSource: ClockSourceNode,
                                 vc7074gbdimm: Boolean = false)(
  implicit val p: Parameters)
case class DDROverlayOutputSysClk(ddr: TLInwardNode)
trait DDRShellPlacerSysClk[Shell] extends ShellPlacer[DDRDesignInputSysClk, DDRShellInputSysClk, DDROverlayOutputSysClk]

case object DDROverlayKeySysClk extends Field[Seq[DesignPlacer[DDRDesignInputSysClk, DDRShellInputSysClk, DDROverlayOutputSysClk]]](Nil)

abstract class DDRPlacedOverlaySysClk[IO <: Data](val name: String, val di: DDRDesignInputSysClk, val si: DDRShellInputSysClk)
  extends IOPlacedOverlay[IO, DDRDesignInputSysClk, DDRShellInputSysClk, DDROverlayOutputSysClk]
{
  implicit val p = di.p
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
