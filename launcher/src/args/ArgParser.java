package args;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import enums.Part;

public class ArgParser {
    public static LauncherArgs parse(String[] flags)
        throws IllegalArgumentException {
        // Filter invalid flag formats
        Pattern flag_pattern = Pattern.compile(
                "^-(d=0?[1-9]|d=1[0-9]|d=2[0-5]|p=0?[1-2]|t(=0?[1-9]|=[1-9][0-9])?)");
        Map<Character, List<String>> filtered_args = Stream.of(flags)
                .filter(flag_pattern.asMatchPredicate())
                .collect(Collectors.groupingBy(str -> str.charAt(1)));

        // There should only be one of each valid flag
        for (List<String> flag_list : filtered_args.values()) {
            if (flag_list.size() > 1)
                throw new IllegalArgumentException("Please provide only one flag for each argument");
        }

        // Minimum requirement is for a puzzle day and puzzle part
        if (filtered_args.containsKey('d') && filtered_args.containsKey('p')) {
            int puzzle_day = Integer.parseInt(filtered_args.get('d').getFirst().substring(3));
            Part puzzle_part = Integer.parseInt(filtered_args.get('p').getFirst().substring(3)) == 1
                    ? Part.PART_1 : Part.PART_2;

            String test_file_str = "";
            if (filtered_args.containsKey('t')) {
                if (filtered_args.get('t').getFirst().length() > 2) {
                    int test_num = Integer.parseInt(filtered_args.get('t').getFirst().substring(3));
                    test_file_str += test_num < 10 ? "-test0" + test_num : "-test" + test_num;
                } else {
                    test_file_str += "-test";
                }
            }

            // Package results and return
            String puzzle_day_str = puzzle_day < 10 ? "day0" + puzzle_day : "day" + puzzle_day;
            Path input_file_path = Path.of("solutions/input/input-" + puzzle_day_str + test_file_str);
            return new LauncherArgs(puzzle_day_str, puzzle_part, input_file_path);
        } else {
            throw new IllegalArgumentException("Program requires a puzzle Day ('-d=[01-25]') " +
                    "and a puzzle Part ('-p[01-02]') argument");
        }
    }
}
