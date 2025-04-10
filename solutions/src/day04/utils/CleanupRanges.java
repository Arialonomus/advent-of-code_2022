package day04.utils;

import enums.Part;

public class CleanupRanges {
    /**
     * Checks if two cleanup section ranges overlap either fully (for Part 1) or partially for (Part 2)
     * @param range_limits a 4-element array of integers, with each pair representing the lowest- and highest-
     *                     numbered section to be cleaned by that elf
     * @param puzzle_part a flag indicating which part of the puzzle is being solved, affects return
     * @return True if one range fully contains the other (Part 1) or if the ranges overlap at all (Part 2).
     * Otherwise, returns false
     */
    public static boolean doOverlap(int[] range_limits, Part puzzle_part) {
        int min_a = range_limits[0];
        int max_a = range_limits[1];
        int min_b = range_limits[2];
        int max_b = range_limits[3];

        // Part 1: Full Overlaps
        if (puzzle_part == Part.PART_1) {
            return min_a >= min_b && max_a <= max_b || min_b >= min_a && max_b <= max_a;
        }

        // Part 2: Partial Overlaps
        return max_a >= min_b && max_a <= max_b || max_b >= min_a && max_b <= max_a;
    }
}
