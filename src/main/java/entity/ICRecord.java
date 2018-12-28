package entity;

import java.util.Map;

public class ICRecord {
    Integer ID;
    Integer StudentID;
    String StudentName;
    String StudentSurname;
    // Ключ - id предмета. Значение - оценка за предмет
    Map<Integer, Integer> Data;

    public String getStudent() {
        return StudentSurname + " " + StudentName;
    }

    public Integer getID() {
        return ID;
    }

    public Integer getStudentID() {
        return StudentID;
    }

    public void setData(Map<Integer, Integer> data) {
        Data = data;
    }

    public ICRecord(Integer ID, Integer studentID, String studentName, String studentSurname, Map<Integer, Integer> data) {
        this.ID = ID;
        StudentID = studentID;
        StudentSurname = studentSurname;
        StudentName = studentName;
        Data = data;
    }

    public ICRecord(Integer ID, Integer studentID, String studentName, String studentSurname) {
        this.ID = ID;
        StudentID = studentID;
        StudentSurname = studentSurname;
        StudentName = studentName;
    }
}
