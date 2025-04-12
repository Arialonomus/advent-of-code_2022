package day07.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Directory implements Indexable {
    /* Data Members */

    public final Map<String, Indexable> children = new HashMap<>();
    private final String name;
    private Directory parent;

    /* Constructor */

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    /* Accessors */

    /**
     * Recursively calculates the total size of this directory
     * @return an int representing the sum of the size of all files in the directory
     */
    @Override
    public int getSize() {
        return children.values().stream()
                .mapToInt(Indexable::getSize)
                .sum();
    }

    public String getName() {
        return name;
    }

    @Override
    public Directory getParent() {
        return Objects.requireNonNullElse(parent, this);
    }

    public Directory move(String name) {
        Indexable child = children.get(name);
        if (child instanceof Directory) {
            return (Directory) children.get(name);
        } else {
            throw new IllegalArgumentException("Cannot move to a non-directory file: " + name);
        }
    }

    /* Mutators */

    public void addFile(String name, int size) {
        children.put(name, new File(name, size, this));
    }

    public void addDirectory(String name) {
        children.put(name, new Directory(name, this));
    }
}
