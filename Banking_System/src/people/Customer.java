package people;

public class Customer extends Person {
    public Customer(long id, String name, String email) { super(id, name, email); }
    @Override public String toString() { return "Customer{id="+id+", name='"+name+"'}"; }
}
