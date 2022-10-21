source args.tcl
file mkdir $project_dir

# define file directory
set sources_dir   [file normalize ./sources ]
set sim_dir       [file normalize ./sim     ]
set constrs_dir   [file normalize ./constrs ]
set tcl_dir       [file normalize ./tcl     ]

# puts "Generating project ${top}"

# create_project $project_name $project_dir -part $part -force

# add_design $top_file
foreach {f} $source_files {
  add_design $f
}

foreach {f} [ glob *.v ] {
  add_design $f
}
remove_design ${long_name}.top.mems.v
foreach {f} [ glob *.sv ] {
  add_design $f
}

# can auto select the first module in sim file
if { [file exists $top_sim_file] == 1} {
  puts "Adding sim file: $top_sim_file!"
  add_simulation $top_sim_file
}

set ipcores [ glob ${fpga_dir}/common/ipcores/*/*.idf ]
puts "Importing IP cores: $ipcores"
foreach {f} $ipcores {
  add_design $f
}

# set constraints [ glob *.fdc ]
# foreach {f} $constraints {
#   add_constraint $f
# }
set constraints [ glob ${fpga_dir}/${device_name}/constraints/*.fdc ]
foreach {f} $constraints {
  add_constraint $f
}

# set module_fdc_file ../../scripts/fdc/${top}.fdc
# if { [file exists $module_fdc_file] == 1} {
#  puts "Adding fdc file: $top_sim_file!"
#  add_constraint $module_fdc_file
# }
# if { [file exists generated.fdc] == 1} {
#  puts "Adding fdc file: generated.fdc!"
#  add_constraint generated.fdc
# }

compile -system_verilog -top_module ${top}