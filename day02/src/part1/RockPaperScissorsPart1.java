package part1;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.WARNING;

import utils.RoundResolver;

public class RockPaperScissorsPart1 {
    public static void main(String[] args) {
        System.Logger logger = System.getLogger(RockPaperScissorsPart1.class.getName());

        // Assumes the working directory is this day's module
        Path input_file_path = Path.of("./" + args[0]);

        // Each round should be resolved according to some scoring rule
        try (Stream<String> rounds = Files.lines(input_file_path)) {
            int total_score = rounds
                    .mapToInt(RoundResolver::getScore)
                    .sum();

            System.out.println(total_score);
        }
        catch (IOException | UncheckedIOException e) {  // Streams may throw UncheckedIOExceptions while fetching
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        } catch (IllegalArgumentException e) {
            logger.log(WARNING, "Invalid input file format ", input_file_path, e);
        }
    }
}
