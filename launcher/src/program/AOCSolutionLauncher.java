package program;

import args.Arguments;
import interfaces.AOCSolution;
import load.SolutionLoader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.Logger.Level.WARNING;
import static load.FilepathConstants.COMPILED_SOLUTIONS_MODULE_DIR;

public class AOCSolutionLauncher {
    public static void main(String[] flags) {
        // Initialization
        System.Logger logger = System.getLogger(AOCSolutionLauncher.class.getName());

        try {
            // Parse launcher arguments
            Arguments args = new Arguments(flags);

            // Locate the class file for the puzzle solution
            Path solution_dir = Path.of(COMPILED_SOLUTIONS_MODULE_DIR + args.getPuzzleDayString() + "/solution/");
            String solution_class_name = SolutionLoader.getSolutionClassName(solution_dir);

            // Instantiate and run the solution
            AOCSolution solution = SolutionLoader.load(solution_dir, solution_class_name);
            for (Path input_file : args.getInputFilePaths()) {
                String result = solution.solve(args.getPuzzlePart(), input_file, logger);

                // Output result
                String puzzle_name = solution_class_name
                        .substring(solution_class_name.lastIndexOf('.') + 1)
                        .replaceAll("(?<!^)([A-Z])", " $1");
                String puzzle_header = String.format("Day %d - %s (%s)",
                        args.getPuzzleDay(), puzzle_name, args.getPuzzlePart());
                if (input_file.toString().contains("test")) {
                    Matcher matcher = Pattern.compile("test(.{1,2})$").matcher(input_file.toString());
                    String test_num = matcher.find() ? matcher.group(1) : null;
                    System.out.printf("%s, Test %s Solution: %s", puzzle_header, test_num, result);
                } else {
                    System.out.printf("%s Solution: %s", puzzle_header, result);
                }
            }

        } catch (IllegalArgumentException e) {
            logger.log(WARNING, "Error parsing solver arguments: ", e);
        } catch (MalformedURLException e) {
            logger.log(WARNING, "Error locating solution class directory: ", e);
        } catch (IOException | UncheckedIOException | ClassNotFoundException e) {
            logger.log(WARNING, "Error locating solution class file: ", e);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            logger.log(WARNING, "Error instantiating solution class file: ", e);
        }
    }
}
