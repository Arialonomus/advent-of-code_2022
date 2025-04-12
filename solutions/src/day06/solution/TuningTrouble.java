package day06.solution;

import enums.Part;
import interfaces.AOCSolution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static java.lang.System.Logger.Level.WARNING;

public class TuningTrouble implements AOCSolution {
    @Override
    public String solve(Part puzzle_part, Path input_file_path, System.Logger logger) {
        try {
            CharSequence characters = Files.readString(input_file_path);
            int marker_len = puzzle_part == Part.PART_1 ? 4 : 14;

            // Check for repeats until a match is found by adding characters to a set
            int marker_end = marker_len - 1;  // Minus 1 since loop increments on first iteration
            Set<Character> marker_characters = new HashSet<>(marker_len);
            while (marker_characters.size() < marker_len) {
                marker_end++;
                marker_characters.clear();
                for (int i = marker_end - marker_len; i < marker_end; i++) {
                    marker_characters.add(characters.charAt(i));
                }
            }

            return String.valueOf(marker_end);
        }
        catch (IOException e) {
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        }

        return "ERROR";
    }
}
