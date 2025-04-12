package args;

import enums.Part;
import static enums.Part.*;
import static load.FilepathConstants.INPUT_DIR;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Arguments {
    /* Data Members */

    private final int puzzle_day;
    private final Part puzzle_part;
    private final List<Path> input_file_paths;

    /* Constructor */

    public Arguments(String[] flags) throws IllegalArgumentException, IOException {
        // Values of each flag are mapped, with a guarantee to have at least the day flag
        Map<Character, String> parsed_args = parseFlags(flags);
        puzzle_day = Integer.parseInt(parsed_args.get('d'));

        // Default to puzzle Part 1 if no part provided
        if (parsed_args.containsKey('p'))
            puzzle_part = Integer.parseInt(parsed_args.get('p')) == 1 ? PART_1 : PART_2;
        else
            puzzle_part = PART_1;

        // Compute test input filepath(s), if applicable
        if (parsed_args.containsKey('t')) {
            try { input_file_paths = getTestPaths(parsed_args.get('t'), puzzle_day); }
            catch (IOException e) { throw new IOException(e); }
        }
        else {
            input_file_paths = List.of(Path.of(INPUT_DIR + "input-" + getPuzzleDayString()));
        }
    }

    /* Accessors */

    public int getPuzzleDay() {
        return puzzle_day;
    }

    public String getPuzzleDayString() {
        return getDayXXString(puzzle_day);
    }

    public Part getPuzzlePart() {
        return puzzle_part;
    }

    public List<Path> getInputFilePaths() {
        return input_file_paths;
    }

    /* Helper Methods */

    /**
     * Parses the argument flags into a map associating the flag character to the contents of its string
     * @param flags a list of flags taken from the program arguments
     * @return a character to string map containing the contents of each flag
     * @throws IllegalArgumentException each valid flag should appear no more than once,
     * and there must at least be a flag for the puzzle day provided
     */
    private Map<Character, String> parseFlags(String[] flags) throws IllegalArgumentException {
        // Filter invalid flag formats
        Pattern flag_pattern = Pattern.compile(
                "^-(d=0?[1-9]|d=1[0-9]|d=2[0-5]|p=0?[1-2]|t(=0?[0-9]|=[1-9][0-9]|=all)?)",
                Pattern.CASE_INSENSITIVE);
        Map<Character, List<String>> filtered_args = Stream.of(flags)
                .filter(flag_pattern.asMatchPredicate())
                .collect(Collectors.groupingBy(str -> str.charAt(1)));

        // There should only be one of each valid flag
        for (List<String> flag_list : filtered_args.values()) {
            if (flag_list.size() > 1)
                throw new IllegalArgumentException("Only one flag may be provided for each argument");
        }

        // Program args must at minimum contain a day flag
        if (!filtered_args.containsKey('d'))
            throw new IllegalArgumentException("Program requires a puzzle Day ('-d=[01-25]') argument");

        return filtered_args.values().stream()
                .map(List::getFirst)
                .map(String::toLowerCase)
                .collect(Collectors.toMap(
                        s -> s.charAt(1),   // Flag Character
                        s -> s.replaceAll("-[dpt]=?", "")   // Flag Contents
                ));
    }

    /**
     * Loads the input file path for one or all tests for this project
     * @param test_num the contents of the test string, which can be any of the following:
     *            an empty string, a number 0-99 (with optional leading zero) or "all"
     * @return a list of one or more Path objects corresponding to the test inputs to run
     * @throws IllegalArgumentException there must be a test input corresponding to test_num, or at
     * least one test input if "all" is provided
     * @throws IOException if there is an issue querying the input files
     */
    private List<Path> getTestPaths(String test_num, int day) throws IllegalArgumentException, IOException {
        // Query all the input files for this puzzle day
        try(Stream<Path> input_files = Files.list(Path.of(INPUT_DIR))) {
            List<Path> test_files = input_files
                    .map(Path::toString)
                    .filter(str -> str.contains(getDayXXString(day) + "-test"))
                    .map(Path::of)
                    .sorted()
                    .toList();

            // Handle non-existent test input request
            if (test_files.isEmpty())
                throw new IllegalArgumentException("No matching test files found");

            // Default to returning the first test file found
            if (test_num.isEmpty() || test_num.equals("0") || test_num.equals("00"))
                return List.of(test_files.getFirst());

            // Return the full list of all test inputs are to be run
            if (test_num.equals("all"))
                return test_files;

            // Return the test_num-th test file
            int test_num_index = Integer.parseInt(test_num) - 1;
            if (test_num_index < test_files.size()) {
                return List.of(test_files.get(Integer.parseInt(test_num) - 1));
            } else {
                throw new IllegalArgumentException("No test file for day " + day + " with number " + test_num);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Generates a string in the format of dayXX for use in file paths
     * @param day the day to generate the string for
     * @return a string in the format "dayXX"
     */
    private static String getDayXXString(int day) {
        return day < 10 ? "day0" + day : "day" + day;
    }
}
