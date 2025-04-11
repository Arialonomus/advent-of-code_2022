package day05.utils;

import java.util.List;

public class CrateStacks {
    private StringBuilder[] stacks;

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
}
