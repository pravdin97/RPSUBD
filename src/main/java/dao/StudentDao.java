package dao;

import entity.Student;
import utils.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDao {

    private static ArrayList<Student> cycleByStudents(ResultSet res, ArrayList result) throws SQLException {
        try {
            while (res.next()) {
                result.add(new Student(
                        res.getString("name"),
                        res.getString("surname"),
                        res.getString("midname")
                ));
            }
        } catch (Exception e) {
            return null;
        }

        return result;

    }


    // Возвращает всех студентов с факультета
    public static ArrayList<Student> GetAllStudentsFromFacultyForTable(String faculty) throws SQLException {

        ResultSet res = DBHelper.ExecuteQuery(Queries.GetAllStudentsFromFaculty(faculty));
        ArrayList<Student> result = new ArrayList<>();
        return cycleByStudents(res, result);
    }



    public static ArrayList<Student> GetAllStudentFromDirectionForTable(String direction) throws SQLException {
        ResultSet res = DBHelper.ExecuteQuery(Queries.GetAllStudentsFromDirection(direction));
        ArrayList<Student> result = new ArrayList<>();
        return cycleByStudents(res, result);

    }

}
