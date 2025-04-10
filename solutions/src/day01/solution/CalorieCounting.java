package day01.solution;

import enums.Part;
import interfaces.AOCSolution;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.WARNING;

public class CalorieCounting implements AOCSolution {
    public String solve(Part puzzle_part, Path input_file_path, System.Logger logger) {
        // Calories carried by each elf are delimited by blank lines
        try (Stream<String> groups = Stream.of(Files.readString(input_file_path).split("\\n\\n"))) {
            List<Integer> highest_calorie_totals = groups
                    .map(
                            // Each entry in the string stream represents the calories carried by a single elf
                            s -> Stream.of(s.split("\n"))
                            .mapToInt(Integer::parseInt)
                            .sum())
                    // We need the three highest calorie totals for the puzzle solution
                    .sorted(Comparator.reverseOrder())
                    .limit(3)
                    .toList();

            // Return puzzle solution depending on Puzzle Part
            if (puzzle_part == Part.PART_1) {
                return highest_calorie_totals.getFirst().toString();
            } else {
                return String.valueOf(highest_calorie_totals.stream()
                        .mapToInt(Integer::intValue)
                        .sum());
            }
        }
        catch (IOException | UncheckedIOException e) {  // Streams may throw UncheckedIOExceptions while fetching
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        }

        return "ERROR";
    }
}
