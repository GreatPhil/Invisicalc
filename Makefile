# This makefile is defined to give you the following targets:
#
#    default: The default target: Compiles $(PROG) and whatever it 
#	   depends on.
#    test: Compile $(PROG), if needed, and then for each file, F, listed in 
#	   $(TESTS), use that file as input to "java $(MAIN_CLASS)" and
#          compare the input to the contents of the file names F.std.
#          Report discrepencies.
#    clean: Remove all the .class files produced by java compilation, 
#          all Emacs backup files, and testing output files.
#
# In other words, type 'gmake' to compile everything; 'gmake test' to 
# compile and test everything, and 'gmake clean' to clean things up.
# 
# You can use this file without understanding most of it, of course, but
# we strongly recommend that you try to figure it out, and where you cannot,
# that you ask questions.

# Name of class containing main procedure 
MAIN_CLASS = invisicalc

# List all .java files used here.
SRCS = invisicalc.java Cell.java Cellworker.java CircleChecker.java Date.java ExpressionParser.java Operator.java Sorter.java Table.java checkDisplay.java


# List tests here.  For each name, BAR, in this list, there should be an
# input file BAR.cmd, and a comparison file BAR.cmd.std.
# (ADD MORE!  End a line with \ to continue it, if needed.)
TESTS = test1 test2 test3

# .java file containing the main program.
MAIN_SRC = $(MAIN_CLASS).java

#### YOU SHOULDN'T HAVE TO CHANGE BELOW HERE ####

# Names of all class files created from $(SRCS)
CLASSES = $(SRCS:.java=.class)

# RatioCalc.class must be recompiled whenever (a) it is missing or
# (b) any of the SRCS have been changed more recently than it has.
# Use javac to recompile.
# This is the first, and therefore default, target.
$(MAIN_CLASS).class: $(SRCS)
	javac -g $(SRCS)

# 'make test' will test run the tests in TESTS.  See the rule for
# %: %.cmd below for what to do.  
test: $(TESTS)

# Before running any test, make sure that the program is compiled.
$(TESTS): $(MAIN_CLASS).class

# 'make clean' will clean up stuff you can reconstruct.
clean:
	rm -f *~ */*~ $(CLASSES) OUTPUT 

# General (default) rule for performing test FOO, in FOO.cmd:
# Run the main program, giving it the input in FOO.cmd.  Compare the output
# to the file FOO.cmd.std, after first filtering out the greeting message
# at the beginning of the output, and all prompts.  Also, ignore differences 
# in whitespace

%: %.cmd
# Print "Running FOO ..." on the terminal.  The @ means not to print
# the 'echo' command itself
	@echo Running $* ...
# Remove the file named OUTPUT, if any.  Don't complain if it's not there.
	rm -f OUTPUT
# Run the java interpreter on MAIN_CLASS. Use FOO.cmd, rather than the 
# terminal, as input.  Put the regular output and the error output in 
# the file ERROR.
	java -Djava.compiler $(MAIN_CLASS) < $*.cmd >OUTPUT 2>&1
# sed is an editor program.  This command removes the first line of
# OUTPUT (-e '1d') and also removes all >s and blanks at the beginning
# of each line (-e 's/^[> ]*//').  The result is given (piped) as input
# to the comparison program, diff, to compare against FOO.cmd.std (-b
# means "ignore trailing blanks and otherwise treat multiple blanks as
# a single blank".  The - means "compare with the standard input".)
	sed -e '1d' -e 's/^[> ]*//' OUTPUT | diff -b - $*.cmd.std
