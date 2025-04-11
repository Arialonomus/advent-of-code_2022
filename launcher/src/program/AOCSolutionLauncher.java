package program;

import enums.Part;
import args.ArgParser;
import args.LauncherArgs;
import interfaces.AOCSolution;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.System.Logger.Level.WARNING;

public class AOCSolutionLauncher {
    public static void main(String[] args) {
        // Initialization
        System.Logger logger = System.getLogger(AOCSolutionLauncher.class.getName());

        try {
            // Parse launcher arguments
            LauncherArgs loader_args = ArgParser.parse(args);

            // Locate the class file for the puzzle solution
            Path solution_dir = Path.of("out/production/solutions/" + loader_args.puzzle_day() + "/solution/");
            Path solution_class_path = Files.list(solution_dir)
                    .filter(p -> p.toString().endsWith(".class"))
                    .findFirst()
                    .orElseThrow(() -> new IOException("No solution class file found"));
            String solution_class_name = solution_class_path.toString()
                    .replace("out/production/solutions/", "")
                    .replace(FileSystems.getDefault().getSeparator(), ".")
                    .replace(".class", "");

            // Load the solution class
            Path working_dir = Path.of(System.getProperty("user.dir")).toAbsolutePath();
            URL[] urls = { URI.create("file://" + working_dir + "/" + solution_dir).toURL() };
            URLClassLoader loader = new URLClassLoader(urls);
            Class<?> solution_class = Class.forName(solution_class_name, true, loader);
            if (!AOCSolution.class.isAssignableFrom(solution_class))
                throw new IllegalArgumentException("Class " + solution_class_name + " is not a solution class");

            // Instantiate and run the solution
            AOCSolution solution = (AOCSolution) solution_class.getDeclaredConstructor().newInstance();
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
