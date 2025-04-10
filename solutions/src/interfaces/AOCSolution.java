package interfaces;

import enums.Part;

import java.nio.file.Path;

@FunctionalInterface
public interface AOCSolution {
    String solve(Part puzzle_part, Path input_file_path, System.Logger logger);
}
