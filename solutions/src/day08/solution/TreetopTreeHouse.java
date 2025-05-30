package day08.solution;

import day08.utils.TreeHeightGrid;
import enums.Part;
import interfaces.AOCSolution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static enums.Part.PART_1;
import static java.lang.System.Logger.Level.WARNING;

public class TreetopTreeHouse implements AOCSolution {
    @Override
    public String solve(Part puzzle_part, Path input_file_path, System.Logger logger) {
        try {
            List<String> input_lines = Files.readAllLines(input_file_path);
            TreeHeightGrid tree_grid = new TreeHeightGrid(input_lines);

            if (puzzle_part == PART_1) {
                return String.valueOf(tree_grid.calculateNumExternallyVisible());
            } else {
                return String.valueOf(tree_grid.calculateMaxScenicScore());
            }
        }
        catch (IOException e) {
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        }

        return "ERROR";
    }
}
