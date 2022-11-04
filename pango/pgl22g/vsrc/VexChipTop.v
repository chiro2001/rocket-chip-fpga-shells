`timescale 1ns/1ps

module VexChipTop (input reset,
                   input sys_clock,
                   output uart_txd,
                   input uart_rxd);
    wire pll_clock;
    wire pll_locked;
    /* synthesis syn_keep=1 */ wire  rvfi_valid;
    /* synthesis syn_keep=1 */ wire [63:0] rvfi_order;
    /* synthesis syn_keep=1 */ wire [31:0] rvfi_insn;
    /* synthesis syn_keep=1 */ wire  rvfi_trap;
    /* synthesis syn_keep=1 */ wire  rvfi_halt;
    /* synthesis syn_keep=1 */ wire  rvfi_intr;
    /* synthesis syn_keep=1 */ wire [31:0] rvfi_pc_rdata;
    /* synthesis syn_keep=1 */ wire [31:0] rvfi_pc_wdata;
    // harnessSysPLLPerf harnessSysPLL(
    // .clkin1(sys_clock),
    // .pll_rst(reset),
    // .pll_lock(pll_locked),
    // .clkout0(pll_clock)
    // );
    assign pll_clock = sys_clock;
    assign pll_locked = reset;
    VexChip core(
    .reset((!pll_locked)),
    .sys_clock(pll_clock),
    .uart_txd(uart_txd),
    .uart_rxd(uart_rxd),
    .rvfi_valid(rvfi_valid),
    .rvfi_order(rvfi_order),
    .rvfi_insn(rvfi_insn),
    .rvfi_trap(rvfi_trap),
    .rvfi_halt(rvfi_halt),
    .rvfi_intr(rvfi_intr),
    .rvfi_pc_rdata(rvfi_pc_rdata),
    .rvfi_pc_wdata(rvfi_pc_wdata)
    );
    
endmodule
