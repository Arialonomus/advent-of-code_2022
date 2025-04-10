package day02.solution;

import day02.utils.RoundResolver;
import enums.Part;
import interfaces.AOCSolution;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static java.lang.System.Logger.Level.WARNING;

public class RockPaperScissors implements AOCSolution {
    public String solve(Part puzzle_part, Path input_file_path, System.Logger logger) {
        // Initialize the Round Resolver
        RoundResolver resolver = new RoundResolver(puzzle_part);

        // Each round should be resolved according to some scoring rule
        try (Stream<String> rounds = Files.lines(input_file_path)) {
            return String.valueOf(rounds
                    .mapToInt(resolver::getScore)
                    .sum());
        }
        catch (IOException | UncheckedIOException e) {  // Streams may throw UncheckedIOExceptions while fetching
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        } catch (IllegalArgumentException e) {
            logger.log(WARNING, "Invalid input file format ", input_file_path, e);
        }

        return "ERROR";
    }
}
