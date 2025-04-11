package day05.utils;

import java.util.List;

public class CrateStacks {
    private final StringBuilder[] stacks;

    public CrateStacks(int num_stacks, List<String> input) {
        stacks = new StringBuilder[num_stacks];
        for (int i = 0; i < num_stacks; i++) {
            stacks[i] = new StringBuilder();
        }

        // Populate the stacks from input
        for (int i = input.size() - 1; i >= 0; i--) {
            int stack_index = 0;
            for (int j = 1; j < input.get(i).length(); j += 4) {
                char c = input.get(i).charAt(j);
                if (c != ' ') {
                    stacks[stack_index].append(c);
                }
                stack_index++;
            }
        }
    }

    /**
     * Simulates moving crates one at a time from one stack to another, updating the class state
     * @param num_crates the number of crates to be moved
     * @param from_ID the ID number (ID = index + 1) of the stack to be moved from
     * @param to_ID the ID number (ID = index + 1) of the stack to be moved to
     */
    public void moveCrates(int num_crates, int from_ID, int to_ID) {
        // Get the substring representing the stack of crates to be moved
        StringBuilder from_stack = stacks[from_ID - 1];
        int stack_move_index = from_stack.length() - num_crates;
        StringBuilder crates = new StringBuilder(from_stack.substring(stack_move_index));

        // Append in reverse to simulate crates being moved one at a time
        StringBuilder to_stack = stacks[to_ID - 1];
        to_stack.append(crates.reverse());

        // Trim the characters from the source stack to represent the crates being removed
        from_stack.delete(stack_move_index, from_stack.length());
    }

    /**
     * Returns the state of the crate stacks, defined as the top crate of each stack
     * @return a string showing the crate at the top of each stack from stack 1 to num_stacks
     */
    public String getTopCrates() {
        StringBuilder top_crates = new StringBuilder();
        for (StringBuilder stack : stacks) {
            top_crates.append(stack.charAt(stack.length() - 1));
        }
        return top_crates.toString();
    }
}
