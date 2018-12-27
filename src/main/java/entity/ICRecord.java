package entity;

import java.util.ArrayList;

public class ICRecord {
    String StudentName;
    String StudentSurname;
    ArrayList<SRating> StudentRatingList;

    public String getStudentName() {
        return StudentName;
    }

    public String getStudentSurname() {
        return StudentSurname;
    }

    public ICRecord(String studentName, String studentSurname, ArrayList<SRating> studentRatingList){
        this.StudentName = studentName;
        this.StudentSurname = studentSurname;
        this.StudentRatingList = studentRatingList;
    }
}
