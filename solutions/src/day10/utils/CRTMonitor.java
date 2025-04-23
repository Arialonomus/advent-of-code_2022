package day10.utils;

public class CRTMonitor {

    /* Data Members */

    private int register_x = 1;
    private int clock_cycle = 1;
    private int signal_strength = 0;
    private final int SCREEN_WIDTH = 40;
    private final StringBuilder row_buffer = new StringBuilder(SCREEN_WIDTH);
    private final StringBuilder monitor_buffer = new StringBuilder("\n");

    /* Instructions */

    /**
     * Does nothing, incrementing the clock cycle and triggering a cycle check
     */
    public void noop() {
        draw();
        incrementClock();
    }

    /**
     * Updates the contents of register_x after two cycles. Triggers a cycle check twice
     * @param value the value added to register_x
     */
    public void addx(int value) {
        draw();
        incrementClock();
        draw();
        register_x += value;
        incrementClock();
    }

    /* Accessors */

    public int getSignalStrength() {
        return signal_strength;
    }

    public String displayMonitor() {
        return monitor_buffer.toString();
    }

    /* Helper Methods */

    /**
     * Checks the current cycle and updates the content of signal_strength on the 20th cycle, and every 40th
     * cycle following.
     */
    private void checkCycle() {
        final int INITIAL_CHECK_CYCLE = 20;
        final int SUBSEQUENT_CHECK_INTERVAL = 40;
        if (clock_cycle == INITIAL_CHECK_CYCLE
                || (clock_cycle - INITIAL_CHECK_CYCLE) % SUBSEQUENT_CHECK_INTERVAL == 0)
            signal_strength += register_x * clock_cycle;
    }

    /**
     * Increments the cycle count and triggers a cycle check
     */
    private void incrementClock() {
        ++clock_cycle;
        checkCycle();
    }

    /**
     * Checks if a row's horizontal position is covered by the sprite, and draws the result at the
     * horizontal position of the current row
     */
    private void draw() {
        int horizontal_position = (clock_cycle - 1) % SCREEN_WIDTH;

        // Light up the screen position if it is covered by the sprite
        if (horizontal_position >= register_x - 1 && horizontal_position <= register_x + 1)
            row_buffer.append("#");
        else
            row_buffer.append(".");

        // Start a new row buffer once the row has been drawn
        if (clock_cycle % SCREEN_WIDTH == 0) {
            monitor_buffer.append(row_buffer).append("\n");
            row_buffer.setLength(0);
        }
    }
}
