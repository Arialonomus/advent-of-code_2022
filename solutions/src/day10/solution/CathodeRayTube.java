package day10.solution;

import day10.utils.CRTMonitor;
import enums.Part;
import interfaces.AOCSolution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.System.Logger.Level.WARNING;

public class CathodeRayTube implements AOCSolution {

    @Override
    public String solve(Part puzzle_part, Path input_file_path, System.Logger logger) {
        try {
            List<String> input_lines = Files.readAllLines(input_file_path);
            CRTMonitor circuit = new CRTMonitor();

            for (String line : input_lines) {
                if (line.startsWith("noop"))
                    circuit.noop();
                else
                    circuit.addx(Integer.parseInt(line.substring(5)));
            }

            return String.valueOf(circuit.getSignalStrength());
        }
        catch (IOException e) {
            logger.log(WARNING, "Error reading input file ", input_file_path, e);
        }

        return "ERROR";
    }
}
