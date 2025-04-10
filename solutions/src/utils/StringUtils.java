package utils;

import java.util.ArrayList;
import java.util.List;

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
}
