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
    public int calculateNumExternallyVisible() {
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

    /**
     * Calculates the highest scenic score for all trees in this tree grid
     * @return an int representing the highest scenic score for all trees in this grid
     */
    public int calculateMaxScenicScore() {
        // Generate a list of all tree positions
        List<GridPosition> test_tree_positions = IntStream.range(0, height)
                .boxed()
                .flatMap(row -> IntStream.range(0, width)
                        .mapToObj(col -> new GridPosition(row, col)))
                .toList();

        return test_tree_positions.parallelStream()
                .mapToInt(this::calculateScenicScore)
                .max().orElse(-1);  // -1 is an error value, should never be returned
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
        if (Collections.max(grid.get(row).subList(0, col)) < tree_height)
            return 1;

        // Test from top edge
        if (Collections.max(transposed_grid.get(col).subList(0, row)) < tree_height)
            return 1;

        // Test from right edge
        if (Collections.max(grid.get(row).subList(col + 1, width)) < tree_height)
            return 1;

        // Test from bottom edge
        if (Collections.max(transposed_grid.get(col).subList(row + 1, height)) < tree_height)
            return 1;

        return 0;
    }

    /**
     * Calculates the scenic score for a tree at a given position
     * @param pos a GridPosition object representing the row and column index of the tree being tested
     * @return an int representing the scenic score for that tree
     */
    private int calculateScenicScore(GridPosition pos) {
        int row = pos.row();
        int col = pos.col();
        int tree_height = grid.get(row).get(col);

        int left_score = 0; // Cells on an edge have a score of zero for that direction
        if (col > 0) {
            left_score = countVisibleTrees(grid.get(row).subList(0, col).reversed(), tree_height);
        }

        int right_score = 0;
        if (col < width - 1) {
            right_score = countVisibleTrees(grid.get(row).subList(col + 1, width), tree_height);
        }

        int up_score = 0;
        if (row > 0) {
            up_score = countVisibleTrees(transposed_grid.get(col).subList(0, row).reversed(), tree_height);
        }

        int down_score = 0;
        if (row < height - 1) {
            down_score = countVisibleTrees(transposed_grid.get(col).subList(row + 1, height), tree_height);
        }

        return left_score * right_score * up_score * down_score;
    }

    /**
     * Calculates the number of trees in row that are visible from a starting tree of a given height
     * @param height_list a list of heights in a row from a starting position to a grid edge
     * @param blocking_height the height of starting tree from which the row is considered
     * @return an int representing the number of trees visible from that starting tree
     */
    private static int countVisibleTrees(List<Integer> height_list, int blocking_height) {
        // Find the first tree the same height or greater in the row, if present
        int blocking_tree_index = IntStream.range(0, height_list.size())
                .filter(i -> height_list.get(i) >= blocking_height)
                .findFirst().orElse(-1);

        // If no blocking tree is found, all trees are visible
        return blocking_tree_index == -1 ? height_list.size() : 1 + blocking_tree_index;
    }
}
