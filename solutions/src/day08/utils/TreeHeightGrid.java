package day08.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

record GridPosition(int row, int col) {}

public class TreeHeightGrid {
    private final int width;
    private final int height;
    private final List<List<Integer>> grid;
    private final List<List<Integer>> transposed_grid;

    public TreeHeightGrid(List<String> input) {
        width = input.getFirst().length();
        height = input.size();
        grid = input.stream()
                .map(str -> str.chars()
                        .map(c -> c - '0')
                        .boxed()
                        .toList())
                .toList();
        transposed_grid = IntStream.range(0, width)
                .mapToObj(col -> IntStream.range(0, height)
                        .mapToObj(row -> grid.get(row).get(col))
                        .toList())
                .toList();
    }
}
