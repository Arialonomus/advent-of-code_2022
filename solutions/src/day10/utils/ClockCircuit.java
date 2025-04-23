package day10.utils;

public class ClockCircuit {

    /* Data Members */

    private int register_x = 1;
    private int clock_cycle = 1;
    private int signal_strength = 0;

    /* Instructions */

    /**
     * Does nothing, incrementing the clock cycle and triggering a cycle check
     */
    public void noop() {
        incrementClock();
    }

    /**
     * Updates the contents of register_x after two cycles. Triggers a cycle check twice
     * @param value the value added to register_x
     */
    public void addx(int value) {
        incrementClock();
        register_x += value;
        incrementClock();
    }

    /* Accessors */

    public int getSignalStrength() {
        return signal_strength;
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
}
