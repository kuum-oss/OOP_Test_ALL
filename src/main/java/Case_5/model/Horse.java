package Case_5.model;

public class Horse {
    private final String id;
    private final String name;

    public Horse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() { return name; }
    public String getId() { return id; }

    @Override
    public String toString() { return name; }
}