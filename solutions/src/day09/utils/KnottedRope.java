package day09.utils;

import java.util.HashSet;
import java.util.Set;

record Position2D(int x, int y) {}

public class KnottedRope {
    private int head_x = 0;
    private int head_y = 0;
    private int tail_x = 0;
    private int tail_y = 0;
    private final Set<Position2D> visited_positions = new HashSet<>();
}
