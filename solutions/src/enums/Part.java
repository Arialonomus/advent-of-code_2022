package enums;

public enum Part{
    PART_1("Part 1"),
    PART_2("Part 2");

    private final String render_name;

    Part(String render_name) {
        this.render_name = render_name;
    }

    @Override
    public String toString() {
        return render_name;
    }
}
