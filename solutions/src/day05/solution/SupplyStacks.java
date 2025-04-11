package day05.solution;

import day05.utils.CrateStacks;
import enums.Part;
import interfaces.AOCSolution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.Logger.Level.WARNING;

public class SupplyStacks implements AOCSolution {
    @Override
    public String solve(Part puzzle_part, Path input_file_path, System.Logger logger) {
        try {
            List<String> input = Files.readAllLines(input_file_path);
            int div_index = input.indexOf("");  // Input divided into sections by blank line

            // Populate the content of the stacks
            int num_stacks = input.get(div_index - 1).trim().split("\\s+").length;
            List<String> stacks_input = input.subList(0, div_index - 1);
            CrateStacks stacks = new CrateStacks(num_stacks, stacks_input);

            // Parse the moves into arrays of integers
            List<int[]> instructions = input.subList(div_index + 1, input.size()).stream()
                    .map(str -> str.substring(5).split(" from | to "))
                    .map(array -> Arrays.stream(array)
                            .mapToInt(Integer::parseInt)
                            .toArray())
                    .toList();

        } catch (IOException e) {
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        }

        return "ERROR";
    }
}
