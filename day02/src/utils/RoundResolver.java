package utils;

import java.util.HashMap;
import java.util.Map;

import static utils.Constants.*;

public class RoundResolver {
    private static final Map<Character, Map<Character, Integer>> scoring_map;
    static {
        // Define the sub-mappings for round outcomes
        Map<Character, Integer> rock = new HashMap<>();
        rock.put('X', ROCK + DRAW);
        rock.put('Y', PAPER + WIN);
        rock.put('Z', SCISSORS + LOSE);

        Map<Character, Integer> paper = new HashMap<>();
        paper.put('X', ROCK + LOSE);
        paper.put('Y', PAPER + DRAW);
        paper.put('Z', SCISSORS + WIN);

        Map<Character, Integer> scissors = new HashMap<>();
        scissors.put('X', ROCK + WIN);
        scissors.put('Y', PAPER + LOSE);
        scissors.put('Z', SCISSORS + DRAW);

        // Build the nested hashmap
        scoring_map = new HashMap<>();
        scoring_map.put('A', rock);
        scoring_map.put('B', paper);
        scoring_map.put('C', scissors);
    }

    public static int getScore(String round) {
        // Read in round moves
        if (round.length() != 3)
            throw new IllegalArgumentException("Rounds must only be 3 characters long. Round provided: " + round);

        char opponent_move = round.charAt(0);
        if ("ABC".indexOf(opponent_move) < 0)
            throw new IllegalArgumentException("Opponent move must be 'A', 'B', or 'C'. Round provided: " + round);

        char player_move = round.charAt(2);
        if ("XYZ".indexOf(player_move) < 0)
            throw new IllegalArgumentException("Player move must be 'X', 'Y', 'Z'. Round provided: " + round);

        // Calculate the score based on the mapped outcomes
        return scoring_map.get(opponent_move).get(player_move);
    }
}
