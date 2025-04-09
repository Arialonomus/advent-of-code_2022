package part2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.WARNING;

public class CalorieCountingPart2 {
    public static void main(String[] args) {
        // Assumes the working directory is this day's module
        Path input_file_path = Path.of("./" + args[0]);

        // Calories carried by each elf are delimited by blank lines
        try (Stream<String> groups = Stream.of(Files.readString(input_file_path).split("\\n\\n"))) {
            int total_calories = groups
                    .map(
                            // Each entry in the string stream represents the calories carried by a single elf
                            s -> Stream.of(s.split("\n"))
                            .mapToInt(Integer::parseInt)
                            .sum())
                    // We need the three highest calorie totals for the puzzle solution
                    .sorted(Comparator.reverseOrder())
                    .mapToInt(Integer::intValue)
                    .limit(3)
                    .sum();

            // Display puzzle solution
            System.out.println(total_calories);
        }
        catch (IOException | UncheckedIOException e) {  // Streams may throw UncheckedIOExceptions while fetching
            System.Logger logger = System.getLogger(CalorieCountingPart2.class.getName());
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        }
    }
}
