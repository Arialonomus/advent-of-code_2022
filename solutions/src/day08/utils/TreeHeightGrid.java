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

    /**
     * Calculates the number of visible trees in this grid, where a visible tree is defined as a tree tall
     * enough to be seen from at least one grid edge
     * @return the total number of trees visible in this grid
     */
    public int calculateNumVisible() {
        // Generate a list of tree positions to test, excluding the border cells
        List<GridPosition> test_tree_positions = IntStream.range(1, height - 1)
                .boxed()
                .flatMap(row -> IntStream.range(1, width - 1)
                        .mapToObj(col -> new GridPosition(row, col)))
                .toList();

        // All border cells are visible, minus 4 to not double-count corners
        int num_visible = (2 * width) + (2 * height) - 4;

        // Each interior cell can be tested independently
        num_visible += test_tree_positions.parallelStream()
                .mapToInt(this::checkVisibility)
                .sum();

        return num_visible;
    }

    /* Helper Methods */

    /**
     * Checks if the tree at a given grid position is visible
     * @param pos a GridPosition object representing the row and column index of the tree being tested
     * @return 1 if the tree is visible from one of the four sides of the grid, otherwise returns 0
     */
    private int checkVisibility(GridPosition pos) {
        int row = pos.row();
        int col = pos.col();
        int tree_height = grid.get(row).get(col);

        // Test from left edge
        if (Collections.max(grid.get(row).subList(0, pos.col())) < tree_height)
            return 1;

        // Test from top edge
        if (Collections.max(transposed_grid.get(col).subList(0, pos.row())) < tree_height)
            return 1;

        // Test from right edge
        if (Collections.max(grid.get(row).subList(pos.col() + 1, width)) < tree_height)
            return 1;

        // Test from bottom edge
        if (Collections.max(transposed_grid.get(col).subList(pos.row() + 1, height)) < tree_height)
            return 1;

        return 0;
    }
}
