package day09.utils;

import java.util.Objects;

public class KnotPosition {
    public int x, y;

    KnotPosition() {
        this.x = 0;
        this.y = 0;
    }

    KnotPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof KnotPosition other) {
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash("x=" + this.x, "y=" + this.y);
    }
}
