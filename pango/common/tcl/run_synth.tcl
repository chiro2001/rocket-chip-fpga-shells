source create_project.tcl
compile -system_verilog -top_module ${top}

synthesize -ads -selected_syn_tool_opt 2 

puts "Synthesis done!"

