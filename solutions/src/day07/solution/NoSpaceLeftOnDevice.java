package day07.solution;

import day07.utils.FileSystem;
import enums.Part;
import interfaces.AOCSolution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static enums.Part.PART_1;
import static java.lang.System.Logger.Level.WARNING;

public class NoSpaceLeftOnDevice implements AOCSolution {
    @Override
    public String solve(Part puzzle_part, Path input_file_path, System.Logger logger) {
        try {
            List<String> terminal_lines = Files.readAllLines(input_file_path);
            FileSystem device_fs = new FileSystem(terminal_lines);
            if (puzzle_part == PART_1) {
                return String.valueOf(device_fs.getTotalDeletableDirectoriesSize());
            } else {
                return String.valueOf(device_fs.deletedDirectorySize());
            }
        }
        catch (IOException e) {
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        }

        return "ERROR";
    }
}
