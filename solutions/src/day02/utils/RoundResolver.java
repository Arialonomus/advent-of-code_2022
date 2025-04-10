package day02.utils;

import java.util.HashMap;
import java.util.Map;

import static day02.utils.Constants.*;
import enums.Part;

public class RoundResolver {
    private final Map<Character, Map<Character, Integer>> scoring_map;

    public RoundResolver(Part puzzle_part){
        // Define the sub-mappings for round outcomes
        Map<Character, Integer> rock = new HashMap<>();
        rock.put('X', puzzle_part == Part.PART_1 ? ROCK + DRAW : LOSE + SCISSORS);
        rock.put('Y', puzzle_part == Part.PART_1 ? PAPER + WIN : DRAW + ROCK);
        rock.put('Z', puzzle_part == Part.PART_1 ? SCISSORS + LOSE : WIN + PAPER);

        Map<Character, Integer> paper = new HashMap<>();
        paper.put('X', ROCK + LOSE);
        paper.put('Y', PAPER + DRAW);
        paper.put('Z', SCISSORS + WIN);

        Map<Character, Integer> scissors = new HashMap<>();
        scissors.put('X', puzzle_part == Part.PART_1 ? ROCK + WIN : LOSE + PAPER);
        scissors.put('Y', puzzle_part == Part.PART_1 ? PAPER + LOSE : DRAW + SCISSORS);
        scissors.put('Z', puzzle_part == Part.PART_1 ? SCISSORS + DRAW : WIN + ROCK);

        // Build the nested hashmap
        scoring_map = new HashMap<>();
        scoring_map.put('A', rock);
        scoring_map.put('B', paper);
        scoring_map.put('C', scissors);
    }

    public int getScore(String round) {
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
