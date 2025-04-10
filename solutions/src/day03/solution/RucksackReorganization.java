package day03.solution;

import enums.Part;
import interfaces.AOCSolution;
import utils.ListUtils;
import utils.StringUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.WARNING;

public class RucksackReorganization implements AOCSolution {
    public static final int PARTITION_SIZE = 3;

    @Override
    public String solve(Part puzzle_part, Path input_file_path, System.Logger logger) {
        try (Stream<String> rucksacks = Files.lines(input_file_path)) {
            List<List<String>> rucksack_groups = puzzle_part == Part.PART_1 ?
                    // Part 1 requires each rucksack be partitioned into two groups
                    rucksacks.map(StringUtils::bisect).toList() :
                    // Part 2 requires every 3 rucksacks be grouped
                    ListUtils.partition(rucksacks.toList(), PARTITION_SIZE);

            int priority_sum = rucksack_groups.stream()
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
