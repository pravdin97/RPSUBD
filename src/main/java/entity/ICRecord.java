package entity;

import java.util.Map;

/// Запись в сводной ведомости
public class ICRecord implements Comparable {
    Integer ID;
    Integer StudentID;
    String StudentName;
    String StudentSurname;
    // Ключ - id предмета. Значение - оценка за предмет
    Map<Integer, String> Data;

    /// Ищет оценку студента за предмет по ID предмета
    public String GetRating(Integer key) {
        if (Data.containsKey(key))
            return Data.get(key);
        else
            return "0";
    }

    public String getStudent() {
        return StudentSurname + " " + StudentName;
    }

    public Integer getID() {
        return ID;
    }

    public Integer getStudentID() {
        return StudentID;
    }

    public void setData(Map<Integer, String> data) {
        Data = data;
    }

    public ICRecord(Integer ID, Integer studentID, String studentName, String studentSurname, Map<Integer, String> data) {
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

    /// Компоратор по "Фамилия + Имя" студента
    @Override
    public int compareTo(Object o) {
        return this.getStudent().compareTo(((ICRecord) o).getStudent());
    }
}
