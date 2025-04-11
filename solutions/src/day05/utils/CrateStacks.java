package day05.utils;

import enums.Part;

import java.util.List;

public class CrateStacks {
    private final StringBuilder[] m_stacks;
    private final Part puzzle_part;

    public CrateStacks(int num_stacks, List<String> input, Part part) {
        puzzle_part = part;
        m_stacks = new StringBuilder[num_stacks];
        for (int i = 0; i < num_stacks; i++) {
            m_stacks[i] = new StringBuilder();
        }

        // Populate the stacks from input
        for (int i = input.size() - 1; i >= 0; i--) {
            int stack_index = 0;
            for (int j = 1; j < input.get(i).length(); j += 4) {
                char c = input.get(i).charAt(j);
                if (c != ' ') {
                    m_stacks[stack_index].append(c);
                }
                stack_index++;
            }
        }
    }

    /**
     * Simulates moving crates from one stack to another, updating the class state
     * @param num_crates the number of crates to be moved
     * @param from_ID the ID number (ID = index + 1) of the stack to be moved from
     * @param to_ID the ID number (ID = index + 1) of the stack to be moved to
     */
    public void moveCrates(int num_crates, int from_ID, int to_ID) {
        // Get the substring representing the stack of crates to be moved
        StringBuilder from_stack = m_stacks[from_ID - 1];
        int stack_move_index = from_stack.length() - num_crates;
        StringBuilder crates = new StringBuilder(from_stack.substring(stack_move_index));

        // Add the moved crates to the new stack
        StringBuilder to_stack = m_stacks[to_ID - 1];
        if (puzzle_part == Part.PART_1)
            // Part 1: Append in reverse to simulate crates being moved one at a time
            to_stack.append(crates.reverse());
        else
            // Part 2: Move crates as a unit
            to_stack.append(crates);

        // Trim the characters from the source stack to represent the crates being removed
        from_stack.delete(stack_move_index, from_stack.length());
    }

    /**
     * Returns the state of the crate stacks, defined as the top crate of each stack
     * @return a string showing the crate at the top of each stack from stack 1 to num_stacks
     */
    public String getTopCrates() {
        StringBuilder top_crates = new StringBuilder();
        for (StringBuilder stack : m_stacks) {
            top_crates.append(stack.charAt(stack.length() - 1));
        }
        return top_crates.toString();
    }
}
