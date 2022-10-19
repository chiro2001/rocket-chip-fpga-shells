// See LICENSE file for license details.
(* keep_hierarchy = "yes" *) module PowerOnResetFPGAOnly
#(parameter N = 32;)(
input clock,
(* dont_touch = "true" *) output power_on_reset
);
reg reset;
reg [$clog(N):0] cnt;
assign power_on_reset = reset;

initial begin
    reset <= 1'b1;
    cnt <= {N{1'b0}};
end

always @(posedge clock) begin
    if (cnt == N - 1) begin
        reset <= 1'b0;
    end
    else begin
        cnt <= cnt + 1;
    end
end
endmodule
