package day07.utils;

import java.util.List;
import java.util.ListIterator;

public class FileSystem {
    /* Data Members */

    private final Directory root = new Directory("/", null);

    /* Constructor */

    public FileSystem(List<String> terminal_output) {
        Directory current_dir = root;
        ListIterator<String> iter = terminal_output.listIterator();
        String line = iter.next(); // First line establishes root dir, and thus can be skipped
        while (iter.hasNext()) {
            line = iter.next();
            String command = line.substring(2,4);
            if (command.equals("cd"))
                current_dir = cd(current_dir, line.substring(5));
            else
                ls(iter, current_dir);
        }
    }

    /* Helper Methods */

    /**
     * Changes the current directory by traversing up or down the tree, or to the root directory
     * @param current_dir a reference to the current directory
     * @param target_dir the name of the target directory
     * @return a reference to the new current directory
     */
    Directory cd(Directory current_dir, String target_dir) {
        return switch (target_dir) {
            case "/" -> root;
            case ".." -> current_dir.getParent();
            default -> current_dir.move(target_dir);
        };
    }

    /**
     * Adds the listed files and directories to the passed in directory
     * @param terminal_iter an iterator through the lines of terminal output, pointing at the
     *                      "ls" command which triggered this method call
     * @param current_dir the directory to which to add the files & subdirectories
     * @return nothing, but terminal_iter is left pointing at the output line of last added file/directory
     * so that a subsequent call to next() will return the next command
     */
    void ls(ListIterator<String> terminal_iter, Directory current_dir) {
        while (terminal_iter.hasNext()) {
            String line = terminal_iter.next();
            if (line.startsWith("$")) {
                terminal_iter.previous();
                return;
            } else if (line.startsWith("dir")) {
                current_dir.addDirectory(line.substring(4));
            } else {
                String[] file_desc = line.split(" ");
                current_dir.addFile(file_desc[1], Integer.parseInt(file_desc[0]));
            }
        }
    }
}
