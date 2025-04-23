package day09.utils;

import enums.Direction;

import static enums.Direction.*;

import java.util.HashSet;
import java.util.Set;

public class KnottedRope {
    private final int num_knots;
    private final KnotPosition[] knots;
    private final KnotPosition head;
    private final KnotPosition tail;
    private final Set<KnotPosition> visited_positions = new HashSet<>(Set.of(new KnotPosition(0,0)));

    public KnottedRope(int num_knots) {
        if (num_knots < 2)
            throw new IllegalArgumentException("Rope must have at least 2 knots");
        this.num_knots = num_knots;
        this.knots = new KnotPosition[num_knots];
        for (int i = 0; i < num_knots; i++) {
            knots[i] = new KnotPosition();
        }
        this.head = knots[0];
        this.tail = knots[num_knots - 1];
    }

    /* Accessors */

    public int getNumVisitedPositions() {
        return visited_positions.size();
    }

    /* Solution Methods */

    /**
     * Moves the head knot of the rope a number of spaces in the given direction,
     * updating the position of all knots in the rope and logging any new positions visited by the tail knot
     * @param direction the direction moved by the head knot
     * @param distance the number of spaces moved by the head knot
     * @return nothing, but the position for each knot is updated, and
     * any new positions visited by the tail knot are recorded in the object state
     */
    public void move(Direction direction, int distance) {
        // Move the head knot
        switch (direction) {
            case UP -> head.y += distance;
            case DOWN -> head.y -= distance;
            case RIGHT -> head.x += distance;
            case LEFT -> head.x -= distance;
        }

        // Update the position of each remaining knot in the rope
        int i = 1;
        boolean knot_moved = true;
        while (i < num_knots && knot_moved) {
            // Escape the loop early if any knot is determined to have not moved
            knot_moved = moveKnot(knots[i], knots[i - 1]);
            ++i;
        }
    }

    /* Helper Methods */

    /**
     * Determines if a knot's position changes based on the current position of the knot ahead of it.
     * If so, updates the position of that knot.
     * @param follower_knot the knot whose position we are looking to update
     * @param leader_knot the knot ahead of the current knot in the rope
     * @return true if the knot was moved, reflecting that the state of follower_knot was updated
     */
    private boolean moveKnot(KnotPosition follower_knot, KnotPosition leader_knot) {
        // Determine if the leader knot's move was large enough to move the follower knot
        if (areTouching(follower_knot, leader_knot))
            return false;

        // Update the knot position until
        do {
            if (follower_knot.x != leader_knot.x && follower_knot.y != leader_knot.y) {
                // Move the knot diagonally towards the leader
                follower_knot.x += follower_knot.x < leader_knot.x ? 1 : -1;
                follower_knot.y += follower_knot.y < leader_knot.y ? 1 : -1;
            } else if (follower_knot.x == leader_knot.x)
                // Move the knot vertically towards the leader
                follower_knot.y += follower_knot.y < leader_knot.y ? 1 : -1;
            else
                // Move the knot horizontally towards the leader
                follower_knot.x += follower_knot.x < leader_knot.x ? 1 : -1;

            // Store the tail position, if necessary
            if (follower_knot == tail)
                storeTailPosition();
        } while (!areTouching(follower_knot, leader_knot));

        // Knot was moved so return true
        return true;
    }

    private boolean areTouching(KnotPosition follower_knot, KnotPosition leader_knot) {
        return Math.abs(leader_knot.x - follower_knot.x) < 2 && Math.abs(leader_knot.y - follower_knot.y) < 2;
    }

    private void storeTailPosition() {
        visited_positions.add(new KnotPosition(tail.x, tail.y));
    }
}
