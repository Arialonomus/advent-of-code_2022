package day09.utils;

import enums.Direction;

import java.util.HashSet;
import java.util.Set;

record Position2D(int x, int y) {}


public class KnottedRope {
    private int head_x = 0;
    private int head_y = 0;
    private int tail_x = 0;
    private int tail_y = 0;
    private final Set<Position2D> visited_positions = new HashSet<>(Set.of(new Position2D(0,0)));

    /**
     * Moves the head knot of the rope a number of spaces in the given direction,
     * logging any new positions visited by the tail knot
     * @param dir the direction moved by the head knot
     * @param distance the number of spaces moved by the head knot
     * @return nothing, but any new positions visited by the tail knot are recorded in the object state
     */
    public void move(Direction dir, int distance) {
        switch (dir) {
            case UP -> {
                head_y += distance;
                if (head_y - tail_y > 1) {
                    if (tail_x != head_x)
                        tail_x = head_x;
                    for (++tail_y; tail_y < head_y; ++tail_y)
                        visited_positions.add(new Position2D(tail_x, tail_y));
                    --tail_y;
                }
            }
            case DOWN -> {
                head_y -= distance;
                if (tail_y - head_y > 1) {
                    if (tail_x != head_x)
                        tail_x = head_x;
                    for (--tail_y; tail_y > head_y; --tail_y)
                        visited_positions.add(new Position2D(tail_x, tail_y));
                    ++tail_y;
                }
            }
            case RIGHT -> {
                head_x += distance;
                if (head_x - tail_x > 1) {
                    if (tail_y != head_y)
                        tail_y = head_y;
                    for (++tail_x; tail_x < head_x; ++tail_x)
                        visited_positions.add(new Position2D(tail_x, tail_y));
                    --tail_x;
                }
            }
            case LEFT -> {
                head_x -= distance;
                if (tail_x - head_x > 1) {
                    if (tail_y != head_y)
                        tail_y = head_y;
                    for (--tail_x; tail_x > head_x; --tail_x)
                        visited_positions.add(new Position2D(tail_x, tail_y));
                    ++tail_x;
                }
            }
        }
    }

    public int getNumVisitedPositions() {
        return visited_positions.size();
    }
}
