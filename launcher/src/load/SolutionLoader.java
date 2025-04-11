package load;

import interfaces.AOCSolution;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static load.FilepathConstants.COMPILED_SOLUTIONS_MODULE_DIR;

public class SolutionLoader {
    /**
     * Dynamically calculates a string representing the full class name of the desired solution
     * @param solution_dir the directory containing the puzzle solution
     * @return a string representing the full class name for the puzzle's solution
     * @throws IOException thrown if there are issues locating the class file
     */
    public static String getSolutionClassName(Path solution_dir) throws IOException {
        try (Stream<Path> class_files = Files.list(solution_dir)) {
            Path solution_class_path = class_files
                    .filter(p -> p.toString().endsWith(".class"))
                    .findFirst()
                    .orElseThrow(() -> new IOException("No solution class file found"));
            return solution_class_path.toString()
                    .replace(COMPILED_SOLUTIONS_MODULE_DIR, "")
                    .replace(FileSystems.getDefault().getSeparator(), ".")
                    .replace(".class", "");
        }
    }

    /**
     * Returns an instance of the specified solution class
     * @param solution_dir the directory containing the compiled solution class file
     * @param solution_class_name the full name (including package hierarchy) of the solution class
     * @return a newly-created instance of the desired solution class
     * @throws MalformedURLException if the URL is not valid
     * @throws ClassNotFoundException if a class with that name does not exist
     * @throws NoSuchMethodException if the solution class does not have a default constructor
     * @throws InvocationTargetException if the default constructor throws an exception
     * @throws InstantiationException if solution class is abstract
     * @throws IllegalAccessException if the solution class is inaccessible based on permissions
     */
    public static AOCSolution load(Path solution_dir, String solution_class_name)
            throws MalformedURLException, ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        Path working_dir = Path.of(System.getProperty("user.dir")).toAbsolutePath();
        URL[] urls = { URI.create("file://" + working_dir + "/" + solution_dir).toURL() };
        URLClassLoader loader = new URLClassLoader(urls);
        Class<?> solution_class = Class.forName(solution_class_name, true, loader);

        // Ensure class is of the correct interface
        if (!AOCSolution.class.isAssignableFrom(solution_class))
            throw new IllegalArgumentException("Class " + solution_class_name + " is not a solution class");

        // We can safely cast because of the above check
        return (AOCSolution) solution_class.getDeclaredConstructor().newInstance();
    }
}
