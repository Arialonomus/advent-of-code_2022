package day07.utils;

public class File implements Indexable {
    private final String name;
    private final int size;
    private Directory parent;

    File(String name, int size, Directory parent) {
        this.name = name;
        this.size = size;
        this.parent = parent;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Directory getParent() {
        return parent;
    }
}
