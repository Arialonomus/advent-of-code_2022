package day03.solution;

import enums.Part;
import interfaces.AOCSolution;
import utils.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.WARNING;

public class RucksackReorganization implements AOCSolution {
    @Override
    public String solve(Part puzzle_part, Path input_file_path, System.Logger logger) {
        try (Stream<String> rucksacks = Files.lines(input_file_path)) {
            int priority_sum = rucksacks
                    .map(StringUtils::bisect)
                    .map(StringUtils::intersection)
                    .map(str -> str.charAt(0))
                    // Map [a-z] to [1-26] and [A-Z] to [27-52]
                    .mapToInt(c -> Character.isUpperCase(c) ? c - 'A' + 27 : c - 'A' - 31)
                    .sum();

            return String.valueOf(priority_sum);
        }
        catch (IOException | UncheckedIOException e) {  // Streams may throw UncheckedIOExceptions while fetching
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        }

        return "";
    }
}
