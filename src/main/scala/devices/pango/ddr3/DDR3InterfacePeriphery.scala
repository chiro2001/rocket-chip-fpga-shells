package sifive.fpgashells.devices.pango.ddr3

import Chisel._
import freechips.rocketchip.config._
import freechips.rocketchip.subsystem.BaseSubsystem
import freechips.rocketchip.diplomacy.{LazyModule, LazyModuleImp, AddressRange}

case object MemoryPangoDDRKey extends Field[PangoPGL22GMIGParams]

trait HasMemoryPangoPGL22GMIG { this: BaseSubsystem =>
  val module: HasMemoryPangoPGL22GMIGModuleImp

  val xilinxvcu118mig = LazyModule(new PangoPGL22GMIG(p(MemoryPangoDDRKey)))

  xilinxvcu118mig.node := mbus.toDRAMController(Some("xilinxvcu118mig"))()
}

trait HasMemoryPangoPGL22GMIGBundle {
  val xilinxvcu118mig: PangoPGL22GMIGIO
  def connectPangoPGL22GMIGToPads(pads: PangoPGL22GMIGPads) {
    pads <> xilinxvcu118mig
  }
}

trait HasMemoryPangoPGL22GMIGModuleImp extends LazyModuleImp
    with HasMemoryPangoPGL22GMIGBundle {
  val outer: HasMemoryPangoPGL22GMIG
  val ranges = AddressRange.fromSets(p(MemoryPangoDDRKey).address)
  require (ranges.size == 1, "DDR range must be contiguous")
  val depth = ranges.head.size
  val xilinxvcu118mig = IO(new PangoPGL22GMIGIO(depth))

  xilinxvcu118mig <> outer.xilinxvcu118mig.module.io.port
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
