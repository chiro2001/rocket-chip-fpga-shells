`timescale 1ns/1ps

module VexChipTop (
  input               reset,
  input               sys_clock,
  input               jtag_tms,
  input               jtag_tdi,
  output              jtag_tdo,
  input               jtag_tck,
  output              uart_txd,
  input               uart_rxd
);
wire pll_clock;
wire pll_locked;
harnessSysPLLPerf harnessSysPLL(
  .clkin1(sys_clock),
  .pll_rst(reset),
  .pll_lock(pll_locked),
  .clkout0(pll_clock)
);
VexChip core(
  .reset(!pll_locked),
  .sys_clock(pll_clock),
  .jtag_tms(jtag_tms),
  .jtag_tdi(jtag_tdi),
  .jtag_tdo(jtag_tdo),
  .jtag_tck(jtag_tck),
  .uart_txd(uart_txd),
  .uart_rxd(uart_rxd)
);
endmodule