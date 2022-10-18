module ChipTopWrapper(input clock_clock,
                      input resetN,
                      output uart_0_txd,
                      input uart_0_rxd);
    wire clock_pll;
    wire pll_clock_0;
    wire pll_clock_1;
    wire pll_clock_2;
    wire pll_clock_lock;
    pll pll_inst(
    .clkin1(clock_clock),
    .clkout0(pll_clock_0), // 8M
    .clkout1(pll_clock_1), // 65M
    .clkout2(pll_clock_2), // 32M
    .pll_lock(pll_clock_lock)
    );
    assign clock_pll = pll_clock_0;
    ChipTop chiptop (
    .clock_clock(clock_pll),
    .reset(!resetN),
    .uart_0_rxd(uart_0_rxd),
    .uart_0_txd(uart_0_txd)
    );
endmodule
