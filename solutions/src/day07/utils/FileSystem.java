package day07.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class FileSystem {
    /* Constants */

    private static final int DELETABLE_SIZE_LIMIT = 100000;
    private static final int TOTAL_DISK_SPACE = 70000000;
    private static final int UNUSED_SPACE_REQUIREMENT = 30000000;

    /* Data Members */

    private final Directory root = new Directory("/", null);
    private final List<Directory> directories = new ArrayList<>(List.of(root));

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

    /**
     * Traverses the list of directories and calculates the total size of all deletable directories, i.e.
     * directories whose size is <= SIZE_LIMIT
     * @return the sum of the sizes of the deletable directories
     */
    public int getTotalDeletableDirectoriesSize() {
        int sum = 0;
        for (Directory dir : directories) {
            if (dir.getSize() <= DELETABLE_SIZE_LIMIT) {
                sum += dir.getSize();
            }
        }
        return sum;
    }

    /**
     * Traverses the list of directories to find the eligible directory to delete to free up
     * the appropriate amount of space, then returns the size of that directory
     * @return the size of the directory to be deleted
     */
    public int deletedDirectorySize() {
        int current_unused_space = TOTAL_DISK_SPACE - root.getSize();
        int disk_space_needed = UNUSED_SPACE_REQUIREMENT - current_unused_space;
        int deleted_directory_size = UNUSED_SPACE_REQUIREMENT; // We need some large number for the first size comparison
        for (Directory dir : directories) {
            int dir_size = dir.getSize();
            if (dir_size >= disk_space_needed && dir_size <= deleted_directory_size) {
                deleted_directory_size = dir_size;
            }
        }
        return deleted_directory_size;
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
                String new_dir_name = line.substring(4);
                current_dir.addDirectory(new_dir_name);
                directories.add((Directory) current_dir.children.get(new_dir_name));
            } else {
                String[] file_desc = line.split(" ");
                current_dir.addFile(file_desc[1], Integer.parseInt(file_desc[0]));
            }
        }
    }
}
