// name mem_ext depth 8192 width 64 ports mwrite,read mask_gran 8


/**
 .R0_addr(mem_ext_R0_addr),
 .R0_en(mem_ext_R0_en),
 .R0_clk(mem_ext_R0_clk),
 .R0_data(mem_ext_R0_data),
 .W0_addr(mem_ext_W0_addr),
 .W0_en(mem_ext_W0_en),
 .W0_clk(mem_ext_W0_clk),
 .W0_data(mem_ext_W0_data),
 .W0_mask(mem_ext_W0_mask)
 */
module mem_ext(input [12:0] R0_addr,
               input R0_clk,
               output [63:0] R0_data,
               input R0_en,
               input [12:0] W0_addr,
               input W0_clk,
               input [63:0] W0_data,
               input W0_en,
               input [7:0] W0_mask);
drm_64x8192 mem_0_0 (
.wr_data(W0_data),
.wr_addr(W0_addr),
.wr_en(W0_en),
.wr_byte_en(W0_mask),
.wr_clk(W0_clk),
.wr_rst(0),
.rd_addr(R0_addr),
.rd_data(R0_data),
.rd_clk(R0_clk),
.rd_rst(0)
);
endmodule
