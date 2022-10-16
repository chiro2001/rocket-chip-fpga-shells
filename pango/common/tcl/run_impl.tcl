source run_synth.tcl

dev_map 
pnr 
if {$generate_timng_report != ""} {
  puts "Creating timing report for Synthesis..."
  report_timing
}
# report_power 
gen_netlist 

puts "Implementation done!"