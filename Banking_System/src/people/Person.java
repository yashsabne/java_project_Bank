package people;

public abstract class Person {
    protected final long id;
    protected final String name;
    protected final String email;

    protected Person(long id, String name, String email) {
        this.id = id; this.name = name; this.email = email;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
