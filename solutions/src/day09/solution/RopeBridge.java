package day09.solution;

import day09.utils.KnottedRope;
import enums.Direction;
import enums.Part;
import interfaces.AOCSolution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static enums.Part.PART_1;
import static java.lang.System.Logger.Level.WARNING;

public class RopeBridge implements AOCSolution {
    record KnotMove(Direction dir, int distance) {}

    @Override
    public String solve(Part puzzle_part, Path input_file_path, System.Logger logger) {
        try (Stream<String> lines = Files.lines(input_file_path)) {
            List<KnotMove> moves = lines
                    .map(str -> str.split(" "))
                    .map(pair -> {
                        Direction dir = switch (pair[0]) {
                            case "U" -> Direction.UP;
                            case "D" -> Direction.DOWN;
                            case "L" -> Direction.LEFT;
                            case "R" -> Direction.RIGHT;
                            default -> throw new IllegalStateException("Unexpected direction value: " + pair[0]);
                        };
                        return new KnotMove(dir, Integer.parseInt(pair[1]));
                    })
                    .toList();

            int num_knots = puzzle_part == PART_1 ? 2 : 10;
            KnottedRope rope = new KnottedRope(num_knots);
            for (KnotMove move : moves) {
                rope.move(move.dir, move.distance);
            }
            return String.valueOf(rope.getNumVisitedPositions());
        }
        catch (IOException e) {
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        }

        return "ERROR";
    }
}
