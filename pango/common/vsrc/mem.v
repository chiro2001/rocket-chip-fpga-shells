// name cc_dir_ext depth 128 width 36 ports mrw mask_gran 18 
// name cc_banks_0_ext depth 1024 width 32 ports rw  
// name data_arrays_0_ext depth 1024 width 32 ports mrw mask_gran 8 
// name tag_array_ext depth 64 width 22 ports rw  
// name tag_array_0_ext depth 64 width 21 ports mrw mask_gran 21 
// name data_arrays_0_0_ext depth 1024 width 32 ports mrw mask_gran 32 

module cc_dir_ext(
  input  [6:0]  RW0_addr,
  input         RW0_clk,
  input  [35:0] RW0_wdata,
  output [35:0] RW0_rdata,
  input         RW0_en,
  input         RW0_wmode,
  input  [1:0]  RW0_wmask
);
  wire [6:0] mem_0_0_RW0_addr;
  wire  mem_0_0_RW0_clk;
  wire [17:0] mem_0_0_RW0_wdata;
  wire [17:0] mem_0_0_RW0_rdata;
  wire  mem_0_0_RW0_en;
  wire  mem_0_0_RW0_wmode;
  wire  mem_0_0_RW0_wmask;
  wire [6:0] mem_0_1_RW0_addr;
  wire  mem_0_1_RW0_clk;
  wire [17:0] mem_0_1_RW0_wdata;
  wire [17:0] mem_0_1_RW0_rdata;
  wire  mem_0_1_RW0_en;
  wire  mem_0_1_RW0_wmode;
  wire  mem_0_1_RW0_wmask;
  wire [17:0] RW0_rdata_0_0 = mem_0_0_RW0_rdata;
  wire [17:0] RW0_rdata_0_1 = mem_0_1_RW0_rdata;
  wire [35:0] RW0_rdata_0 = {RW0_rdata_0_1,RW0_rdata_0_0};
  split_drm_36x128 mem_0_0 (
    .RW0_addr(mem_0_0_RW0_addr),
    .RW0_clk(mem_0_0_RW0_clk),
    .RW0_wdata(mem_0_0_RW0_wdata),
    .RW0_rdata(mem_0_0_RW0_rdata),
    .RW0_en(mem_0_0_RW0_en),
    .RW0_wmode(mem_0_0_RW0_wmode),
    .RW0_wmask(mem_0_0_RW0_wmask)
  );
  split_drm_36x128 mem_0_1 (
    .RW0_addr(mem_0_1_RW0_addr),
    .RW0_clk(mem_0_1_RW0_clk),
    .RW0_wdata(mem_0_1_RW0_wdata),
    .RW0_rdata(mem_0_1_RW0_rdata),
    .RW0_en(mem_0_1_RW0_en),
    .RW0_wmode(mem_0_1_RW0_wmode),
    .RW0_wmask(mem_0_1_RW0_wmask)
  );
  assign RW0_rdata = {RW0_rdata_0_1,RW0_rdata_0_0};
  assign mem_0_0_RW0_addr = RW0_addr;
  assign mem_0_0_RW0_clk = RW0_clk;
  assign mem_0_0_RW0_wdata = RW0_wdata[17:0];
  assign mem_0_0_RW0_en = RW0_en;
  assign mem_0_0_RW0_wmode = RW0_wmode;
  assign mem_0_0_RW0_wmask = RW0_wmask[0];
  assign mem_0_1_RW0_addr = RW0_addr;
  assign mem_0_1_RW0_clk = RW0_clk;
  assign mem_0_1_RW0_wdata = RW0_wdata[35:18];
  assign mem_0_1_RW0_en = RW0_en;
  assign mem_0_1_RW0_wmode = RW0_wmode;
  assign mem_0_1_RW0_wmask = RW0_wmask[1];
endmodule
module cc_banks_0_ext(
  input  [9:0]  RW0_addr,
  input         RW0_clk,
  input  [31:0] RW0_wdata,
  output [31:0] RW0_rdata,
  input         RW0_en,
  input         RW0_wmode
);
  wire [9:0] mem_0_0_RW0_addr;
  wire  mem_0_0_RW0_clk;
  wire [31:0] mem_0_0_RW0_wdata;
  wire [31:0] mem_0_0_RW0_rdata;
  wire  mem_0_0_RW0_en;
  wire  mem_0_0_RW0_wmode;
  wire [31:0] RW0_rdata_0_0 = mem_0_0_RW0_rdata;
  wire [31:0] RW0_rdata_0 = RW0_rdata_0_0;
  split_drm_32x1024_nomask mem_0_0 (
    .RW0_addr(mem_0_0_RW0_addr),
    .RW0_clk(mem_0_0_RW0_clk),
    .RW0_wdata(mem_0_0_RW0_wdata),
    .RW0_rdata(mem_0_0_RW0_rdata),
    .RW0_en(mem_0_0_RW0_en),
    .RW0_wmode(mem_0_0_RW0_wmode)
  );
  assign RW0_rdata = mem_0_0_RW0_rdata;
  assign mem_0_0_RW0_addr = RW0_addr;
  assign mem_0_0_RW0_clk = RW0_clk;
  assign mem_0_0_RW0_wdata = RW0_wdata;
  assign mem_0_0_RW0_en = RW0_en;
  assign mem_0_0_RW0_wmode = RW0_wmode;
endmodule
module data_arrays_0_ext(
  input  [9:0]  RW0_addr,
  input         RW0_clk,
  input  [31:0] RW0_wdata,
  output [31:0] RW0_rdata,
  input         RW0_en,
  input         RW0_wmode,
  input  [3:0]  RW0_wmask
);
  wire [9:0] mem_0_0_RW0_addr;
  wire  mem_0_0_RW0_clk;
  wire [7:0] mem_0_0_RW0_wdata;
  wire [7:0] mem_0_0_RW0_rdata;
  wire  mem_0_0_RW0_en;
  wire  mem_0_0_RW0_wmode;
  wire  mem_0_0_RW0_wmask;
  wire [9:0] mem_0_1_RW0_addr;
  wire  mem_0_1_RW0_clk;
  wire [7:0] mem_0_1_RW0_wdata;
  wire [7:0] mem_0_1_RW0_rdata;
  wire  mem_0_1_RW0_en;
  wire  mem_0_1_RW0_wmode;
  wire  mem_0_1_RW0_wmask;
  wire [9:0] mem_0_2_RW0_addr;
  wire  mem_0_2_RW0_clk;
  wire [7:0] mem_0_2_RW0_wdata;
  wire [7:0] mem_0_2_RW0_rdata;
  wire  mem_0_2_RW0_en;
  wire  mem_0_2_RW0_wmode;
  wire  mem_0_2_RW0_wmask;
  wire [9:0] mem_0_3_RW0_addr;
  wire  mem_0_3_RW0_clk;
  wire [7:0] mem_0_3_RW0_wdata;
  wire [7:0] mem_0_3_RW0_rdata;
  wire  mem_0_3_RW0_en;
  wire  mem_0_3_RW0_wmode;
  wire  mem_0_3_RW0_wmask;
  wire [7:0] RW0_rdata_0_0 = mem_0_0_RW0_rdata;
  wire [7:0] RW0_rdata_0_1 = mem_0_1_RW0_rdata;
  wire [7:0] RW0_rdata_0_2 = mem_0_2_RW0_rdata;
  wire [7:0] RW0_rdata_0_3 = mem_0_3_RW0_rdata;
  wire [15:0] _GEN_0 = {RW0_rdata_0_1,RW0_rdata_0_0};
  wire [23:0] _GEN_1 = {RW0_rdata_0_2,RW0_rdata_0_1,RW0_rdata_0_0};
  wire [31:0] RW0_rdata_0 = {RW0_rdata_0_3,RW0_rdata_0_2,RW0_rdata_0_1,RW0_rdata_0_0};
  wire [15:0] _GEN_2 = {RW0_rdata_0_1,RW0_rdata_0_0};
  wire [23:0] _GEN_3 = {RW0_rdata_0_2,RW0_rdata_0_1,RW0_rdata_0_0};
  split_drm_8x1024 mem_0_0 (
    .RW0_addr(mem_0_0_RW0_addr),
    .RW0_clk(mem_0_0_RW0_clk),
    .RW0_wdata(mem_0_0_RW0_wdata),
    .RW0_rdata(mem_0_0_RW0_rdata),
    .RW0_en(mem_0_0_RW0_en),
    .RW0_wmode(mem_0_0_RW0_wmode),
    .RW0_wmask(mem_0_0_RW0_wmask)
  );
  split_drm_8x1024 mem_0_1 (
    .RW0_addr(mem_0_1_RW0_addr),
    .RW0_clk(mem_0_1_RW0_clk),
    .RW0_wdata(mem_0_1_RW0_wdata),
    .RW0_rdata(mem_0_1_RW0_rdata),
    .RW0_en(mem_0_1_RW0_en),
    .RW0_wmode(mem_0_1_RW0_wmode),
    .RW0_wmask(mem_0_1_RW0_wmask)
  );
  split_drm_8x1024 mem_0_2 (
    .RW0_addr(mem_0_2_RW0_addr),
    .RW0_clk(mem_0_2_RW0_clk),
    .RW0_wdata(mem_0_2_RW0_wdata),
    .RW0_rdata(mem_0_2_RW0_rdata),
    .RW0_en(mem_0_2_RW0_en),
    .RW0_wmode(mem_0_2_RW0_wmode),
    .RW0_wmask(mem_0_2_RW0_wmask)
  );
  split_drm_8x1024 mem_0_3 (
    .RW0_addr(mem_0_3_RW0_addr),
    .RW0_clk(mem_0_3_RW0_clk),
    .RW0_wdata(mem_0_3_RW0_wdata),
    .RW0_rdata(mem_0_3_RW0_rdata),
    .RW0_en(mem_0_3_RW0_en),
    .RW0_wmode(mem_0_3_RW0_wmode),
    .RW0_wmask(mem_0_3_RW0_wmask)
  );
  assign RW0_rdata = {RW0_rdata_0_3,_GEN_1};
  assign mem_0_0_RW0_addr = RW0_addr;
  assign mem_0_0_RW0_clk = RW0_clk;
  assign mem_0_0_RW0_wdata = RW0_wdata[7:0];
  assign mem_0_0_RW0_en = RW0_en;
  assign mem_0_0_RW0_wmode = RW0_wmode;
  assign mem_0_0_RW0_wmask = RW0_wmask[0];
  assign mem_0_1_RW0_addr = RW0_addr;
  assign mem_0_1_RW0_clk = RW0_clk;
  assign mem_0_1_RW0_wdata = RW0_wdata[15:8];
  assign mem_0_1_RW0_en = RW0_en;
  assign mem_0_1_RW0_wmode = RW0_wmode;
  assign mem_0_1_RW0_wmask = RW0_wmask[1];
  assign mem_0_2_RW0_addr = RW0_addr;
  assign mem_0_2_RW0_clk = RW0_clk;
  assign mem_0_2_RW0_wdata = RW0_wdata[23:16];
  assign mem_0_2_RW0_en = RW0_en;
  assign mem_0_2_RW0_wmode = RW0_wmode;
  assign mem_0_2_RW0_wmask = RW0_wmask[2];
  assign mem_0_3_RW0_addr = RW0_addr;
  assign mem_0_3_RW0_clk = RW0_clk;
  assign mem_0_3_RW0_wdata = RW0_wdata[31:24];
  assign mem_0_3_RW0_en = RW0_en;
  assign mem_0_3_RW0_wmode = RW0_wmode;
  assign mem_0_3_RW0_wmask = RW0_wmask[3];
endmodule
module tag_array_ext(
  input  [5:0]  RW0_addr,
  input         RW0_clk,
  input  [21:0] RW0_wdata,
  output [21:0] RW0_rdata,
  input         RW0_en,
  input         RW0_wmode
);
  wire [5:0] mem_0_0_RW0_addr;
  wire  mem_0_0_RW0_clk;
  wire [21:0] mem_0_0_RW0_wdata;
  wire [21:0] mem_0_0_RW0_rdata;
  wire  mem_0_0_RW0_en;
  wire  mem_0_0_RW0_wmode;
  wire [21:0] RW0_rdata_0_0 = mem_0_0_RW0_rdata;
  wire [21:0] RW0_rdata_0 = RW0_rdata_0_0;
  split_drm_32x64 mem_0_0 (
    .RW0_addr(mem_0_0_RW0_addr),
    .RW0_clk(mem_0_0_RW0_clk),
    .RW0_wdata(mem_0_0_RW0_wdata),
    .RW0_rdata(mem_0_0_RW0_rdata),
    .RW0_en(mem_0_0_RW0_en),
    .RW0_wmode(mem_0_0_RW0_wmode)
  );
  assign RW0_rdata = mem_0_0_RW0_rdata;
  assign mem_0_0_RW0_addr = RW0_addr;
  assign mem_0_0_RW0_clk = RW0_clk;
  assign mem_0_0_RW0_wdata = RW0_wdata;
  assign mem_0_0_RW0_en = RW0_en;
  assign mem_0_0_RW0_wmode = RW0_wmode;
endmodule
module tag_array_0_ext(
  input  [5:0]  RW0_addr,
  input         RW0_clk,
  input  [20:0] RW0_wdata,
  output [20:0] RW0_rdata,
  input         RW0_en,
  input         RW0_wmode,
  input         RW0_wmask
);
  wire [5:0] mem_0_0_RW0_addr;
  wire  mem_0_0_RW0_clk;
  wire [20:0] mem_0_0_RW0_wdata;
  wire [20:0] mem_0_0_RW0_rdata;
  wire  mem_0_0_RW0_en;
  wire  mem_0_0_RW0_wmode;
  wire  mem_0_0_RW0_wmask;
  wire [20:0] RW0_rdata_0_0 = mem_0_0_RW0_rdata;
  wire [20:0] RW0_rdata_0 = RW0_rdata_0_0;
  split_drm_32x64 mem_0_0 (
    .RW0_addr(mem_0_0_RW0_addr),
    .RW0_clk(mem_0_0_RW0_clk),
    .RW0_wdata(mem_0_0_RW0_wdata),
    .RW0_rdata(mem_0_0_RW0_rdata),
    .RW0_en(mem_0_0_RW0_en),
    .RW0_wmode(mem_0_0_RW0_wmode),
    .RW0_wmask(mem_0_0_RW0_wmask)
  );
  assign RW0_rdata = mem_0_0_RW0_rdata;
  assign mem_0_0_RW0_addr = RW0_addr;
  assign mem_0_0_RW0_clk = RW0_clk;
  assign mem_0_0_RW0_wdata = RW0_wdata;
  assign mem_0_0_RW0_en = RW0_en;
  assign mem_0_0_RW0_wmode = RW0_wmode;
  assign mem_0_0_RW0_wmask = RW0_wmask;
endmodule
module data_arrays_0_0_ext(
  input  [9:0]  RW0_addr,
  input         RW0_clk,
  input  [31:0] RW0_wdata,
  output [31:0] RW0_rdata,
  input         RW0_en,
  input         RW0_wmode,
  input         RW0_wmask
);
  wire [9:0] mem_0_0_RW0_addr;
  wire  mem_0_0_RW0_clk;
  wire [31:0] mem_0_0_RW0_wdata;
  wire [31:0] mem_0_0_RW0_rdata;
  wire  mem_0_0_RW0_en;
  wire  mem_0_0_RW0_wmode;
  wire  mem_0_0_RW0_wmask;
  wire [31:0] RW0_rdata_0_0 = mem_0_0_RW0_rdata;
  wire [31:0] RW0_rdata_0 = RW0_rdata_0_0;
  split_drm_32x1024_nomask mem_0_0 (
    .RW0_addr(mem_0_0_RW0_addr),
    .RW0_clk(mem_0_0_RW0_clk),
    .RW0_wdata(mem_0_0_RW0_wdata),
    .RW0_rdata(mem_0_0_RW0_rdata),
    .RW0_en(mem_0_0_RW0_en),
    .RW0_wmode(mem_0_0_RW0_wmode),
    .RW0_wmask(mem_0_0_RW0_wmask)
  );
  assign RW0_rdata = mem_0_0_RW0_rdata;
  assign mem_0_0_RW0_addr = RW0_addr;
  assign mem_0_0_RW0_clk = RW0_clk;
  assign mem_0_0_RW0_wdata = RW0_wdata;
  assign mem_0_0_RW0_en = RW0_en;
  assign mem_0_0_RW0_wmode = RW0_wmode;
  assign mem_0_0_RW0_wmask = RW0_wmask;
endmodule