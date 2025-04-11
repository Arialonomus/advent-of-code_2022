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
            int marker_end = 3;
            Set<Character> marker_characters = new HashSet<>(4);
            while (marker_characters.size() < 4) {
                marker_end++;
                marker_characters.clear();
                for (int i = marker_end - 4; i < marker_end; i++) {
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
