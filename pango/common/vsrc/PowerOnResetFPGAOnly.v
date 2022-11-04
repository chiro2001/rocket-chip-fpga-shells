// See LICENSE file for license details.
(* keep_hierarchy = "yes" *) module PowerOnResetFPGAOnly
#(parameter N = 128)(
input clock,
(* dont_touch = "true" *) output power_on_reset
);
reg reset;
reg [$clog2(N):0] cnt;
assign power_on_reset = reset;

// will not be synth here!
// initial begin
//     reset <= 1'b1;
//     cnt <= {N{1'b0}};
// end

always @(posedge clock) begin
    if (cnt == N - 1) begin
        reset <= 1'b0;
    end
    else begin
        reset <= 1'b1;
    end
end

always @(posedge clock) begin
    if (cnt < N - 1) begin
        cnt <= cnt + 1'b1;
    end else if (cnt == N - 1) begin
        cnt <= cnt;
    end else begin
        cnt <= 0;
    end
end
endmodule
