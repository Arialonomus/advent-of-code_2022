package program;

import enums.Part;
import args.ArgParser;
import args.LauncherArgs;
import interfaces.AOCSolution;
import load.SolutionLoader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import static java.lang.System.Logger.Level.WARNING;
import static load.FilepathConstants.COMPILED_SOLUTIONS_MODULE_DIR;

public class AOCSolutionLauncher {
    public static void main(String[] args) {
        // Initialization
        System.Logger logger = System.getLogger(AOCSolutionLauncher.class.getName());

        try {
            // Parse launcher arguments
            LauncherArgs loader_args = ArgParser.parse(args);

            // Locate the class file for the puzzle solution
            Path solution_dir = Path.of(COMPILED_SOLUTIONS_MODULE_DIR + loader_args.puzzle_day() + "/solution/");
            String solution_class_name = SolutionLoader.getSolutionClassName(solution_dir);

            // Instantiate and run the solution
            AOCSolution solution = SolutionLoader.load(solution_dir, solution_class_name);
            String result = solution.solve(loader_args.puzzle_part(), loader_args.input_file_path(), logger);

            // Output result
            String day_str = loader_args.puzzle_day().replaceAll("day0?", "");
            String puzzle_name = solution_class_name
                    .substring(solution_class_name.lastIndexOf('.') + 1)
                    .replaceAll("(?<!^)([A-Z])", " $1");
            String part_str = loader_args.puzzle_part() == Part.PART_1 ? "Part 1" : "Part 2";
            System.out.println("Day " + day_str + " - " + puzzle_name + " (" + part_str + ") Solution: " + result);

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
