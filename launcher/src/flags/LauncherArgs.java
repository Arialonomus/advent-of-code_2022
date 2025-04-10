package flags;

import enums.Part;

import java.nio.file.Path;

public record LauncherArgs(String puzzle_day, Part puzzle_part, Path input_file_path) { }
