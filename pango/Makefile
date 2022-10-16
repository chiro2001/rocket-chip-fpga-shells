PDS ?= pds_shell
PDSFLAGS := \
	-f $(fpga_board_script_dir)/board.tcl

# Path to a program in raw binary format to be flashed into the address that the
# bootrom jumps to.
# FIXME: This variable should probably be communicated by a higher-level Makefile
FLASHED_PROGRAM ?=

# Init project
init = $(FPGA_BUILD_DIR)/.init
$(init): $(fpga_common_script_dir)/create_project.tcl
	mkdir -p $(FPGA_BUILD_DIR) && \
	cd $(FPGA_BUILD_DIR) && \
	VSRCS="$(VSRCS)" IPPDSTCLS="$(IPPDSTCLS)" $(PDS) $(PDSFLAGS) -source $<

.PHONY: init
init: $(init)

# Generate bitstream
bit := $(FPGA_BUILD_DIR)/obj/$(FPGA_TOP_SYSTEM).sbit
$(bit): $(fpga_common_script_dir)/bitstream.tcl $(init)
	cd $(FPGA_BUILD_DIR) && \
	VSRCS="$(VSRCS)" $(PDS) -f $<

.PHONY: bit
bit: $(bit)

.PHONY: clean
clean:
	rm -rf $(FPGA_BUILD_DIR)
