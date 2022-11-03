source run_map.tcl

pnr 
# pnr -gplace_times 16 -mode fast -parallel 16 
if {$generate_timng_report != ""} {
  puts "Creating timing report for Synthesis..."
  report_timing
}
# report_power 
# gen_netlist 

puts "Implementation done!"