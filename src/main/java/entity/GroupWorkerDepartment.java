package entity;

public class GroupWorkerDepartment {
    private String faculty;
    private String direction;
    private String number;

    public String getFaculty() {
        return faculty;
    }

    public String getDirection() {
        return direction;
    }

    public String getNumber() {
        return number;
    }

    public GroupWorkerDepartment(String faculty, String direction, String number) {
        this.faculty = faculty;
        this.direction = direction;
        this.number = number;
    }
}
