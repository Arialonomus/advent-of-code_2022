package day04.solution;

import day04.utils.CleanupRanges;
import enums.Part;
import interfaces.AOCSolution;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.WARNING;

public class CampCleanup implements AOCSolution {
    @Override
    public String solve(Part puzzle_part, Path input_file_path, System.Logger logger) {
        try (Stream<String> assignments = Files.lines(input_file_path)) {
            int num_full_overlaps = assignments
                    .map(line -> line.split("[,-]"))
                    .map(sections -> Arrays.stream(sections)
                            .mapToInt(Integer::parseInt)
                            .toArray())
                    .mapToInt(sections -> CleanupRanges.doOverlap(sections, puzzle_part) ? 1 : 0)
                    .sum();

            return String.valueOf(num_full_overlaps);
        }
        catch (IOException | UncheckedIOException e) {  // Streams may throw UncheckedIOExceptions while fetching
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        }

        return "ERROR";
    }
}
