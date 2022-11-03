`timescale 1ns/1ps

module VexChipTopSim ();
    reg clock;
    reg reset;
    initial begin
        clock <= 0;
        reset <= 0;
        #6
        reset <= 1;
    end
    always #1 clock <= ~clock;
    wire uart_txd;
    wire uart_rxd;
    VexChipTop top(
    .sys_clock(clock),
    .reset(reset),
    .uart_rxd(uart_rxd),
    .uart_txd(uart_txd)
    );
endmodule
