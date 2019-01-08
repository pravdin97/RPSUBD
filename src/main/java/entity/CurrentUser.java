package entity;

public class CurrentUser {

    private int id;
    private String surname;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public CurrentUser(int id, String surname, String name) {
        this.id = id;
        this.surname = surname;
        this.name = name;
    }
}
