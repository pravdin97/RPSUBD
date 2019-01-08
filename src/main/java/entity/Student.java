package entity;

public class Student {

    private String surname;
    private String name;
    private String midname;

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMidname() {
        return midname;
    }

    public Student(String surname, String name, String midname) {
        this.surname = surname;
        this.name = name;
        this.midname = midname;
    }
}
