package day04.solution;

import enums.Part;
import interfaces.AOCSolution;
import utils.ListUtils;
import utils.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
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
                    .mapToInt(sections -> {
                        // Range 1 contained within Range 2
                        if (sections[0] >= sections[2] && sections[1] <= sections[3])
                            return 1;
                        // Range 2 contained within Range 1
                        if (sections[2] >= sections[0] && sections[3] <= sections[1])
                            return 1;
                        return 0;
                    })
                    .sum();

            return String.valueOf(num_full_overlaps);
        }
        catch (IOException | UncheckedIOException e) {  // Streams may throw UncheckedIOExceptions while fetching
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        }

        return "ERROR";
    }
}
