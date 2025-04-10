package utils;

import java.util.ArrayList;
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
     * Calculates the intersection of two or more strings
     * @param str_list a list of strings
     * @return A string representing the intersection of all the strings in the list,
     * or the first string if there is only one string in the list
     */
    public static String intersection(List<String> str_list) {
        if (str_list.size() < 2)
            return str_list.getFirst();

        Set<Character> identity_set = StringUtils.toCharSet(str_list.getFirst());
        return str_list.subList(1, str_list.size()).stream()
                .map(StringUtils::toCharSet)
                .reduce(identity_set, (x, y) -> {x.retainAll(y); return x;})
                .stream().map(String::valueOf)
                .collect(Collectors.joining());
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
