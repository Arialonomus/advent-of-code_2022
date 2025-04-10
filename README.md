This repository contains my solutions for the 2022 Advent of Code puzzles. These solutions are implemented in Java, which was chosen as the implementation language in order to practice for development interviews.

# Program Structure

The program is divided into two sub-modules contained within the ```AdventOfCode2022``` module. First is the ```solutions``` module which contains the solution for each day's puzzle as well as various interfaces and utility classes shared by all solutions. 

The solution for each day's puzzle is located in a package labelled ```dayXX.solution```. Each ```dayXX.solution``` package contains a single class file bearing the name of the puzzle, and will solve both Part 1 and Part 2 of a puzzle, depending on a passed in flag. Each solution is an implementation of the ```AOCSolution``` functional interface, which contains a ```solve()``` method recieving which puzzle part it is solving, the path for the input file, and a reference to the program logger. Some solutions also include puzzle-specific helper classes contained in ```dayXX.utils```.

The second sub-module is the is the ```launcher``` module. This is a simple dynamic launcher program that runs a given puzzle solution by locating the class file and creating an instance, passing in the appropriate input file path. The solution to a given puzzle is output to the console by default, with some formatting about which day and puzzle part's solution is being displayed.

### Running the Program

The program is designed to be run out of the box and can be started from the command line. The launcher requires 2 flags and allows for one optional flag, all prefaced with ```-```, as follows:

**Required Flags**
- ```d=``` - Indicates which day's puzzle solution you would like to run. Accepts numbers 1-25, with or without a leading zero for single digits.
- ```p=``` - Indicates which part of the puzzle you are solving. Accepts 1 or 2, with or without a leading zero.

**Optional Flags**
- ```t``` - Indicates that you would like to provide the test input for that day's solution. If there is more than one test input available, an ```=XX``` can be provided to specify which input to run, otherwise the first test input will be selected.

For example, in order to run the test input for Day 3's puzzle "Rucksack Reorganization" Part 1, you would enter the following command (assuming the root directory) ```java out.production.launcher.program.AOCSolutionLauncher -d=3 -p=2 -t```
