package people;

public class Admin extends Person {
    public Admin(long id, String name, String email) { super(id, name, email); }
    @Override public String toString() { return "Admin{id="+id+", name='"+name+"'}"; }
}
