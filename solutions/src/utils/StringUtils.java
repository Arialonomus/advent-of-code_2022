package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StringUtils {
    /**
     * Splits a string in half
     * @param str The string to be split
     * @return A list containing the two halves of the original string,
     * or the initial string if it is too short to be bisected
     */
    public static List<String> bisect(String str) {
        if (str.length() < 2)
            return new ArrayList<>(List.of(str));

        int middle = str.length() / 2;
        return new ArrayList<>(List.of(str.substring(0, middle), str.substring(middle)));
    }

    /**
     * Converts a string to a Set of Characters, for use in processing strings
     * @param str the string to be converted
     * @return a Set of unique characters found in the passed-in string
     */
    public static Set<Character> toCharSet(String str) {
        return str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
    }
}
