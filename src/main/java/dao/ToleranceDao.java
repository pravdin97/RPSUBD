package dao;

import utils.DBHelper;
import utils.Queries;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ToleranceDao {
    static ArrayList<String> GetStringGeneric(ResultSet result, String column) {
        ArrayList<String> ret = new ArrayList<String>();
        try {
            while (result.next()) {
                ret.add(result.getString(column));
            }
        } catch (Exception e) {
        }
        return ret;
    }

    /// Возвращает список факультетов
    public static ArrayList<String> GetAllFaculty() {
        return ConsolidatedStatementDao.GetAllFaculty();
    }

    /// Возвращает список групп
    public static ArrayList<String> GetGroup(String faculty) {
        return ConsolidatedStatementDao.GetGroup(faculty);
    }

    /// Возвращает список семестров
    public static ArrayList<String> GetAllSemestrs(){
        return ConsolidatedStatementDao.GetAllSemestrs();
    }

    /// Возвращает список студентов с их ИД
    public static Map<Integer, String> GetAllStudents(String dirNumGroup){
        String direction = dirNumGroup.split("-")[0];
        String group = dirNumGroup.split("-")[1];
        Map<Integer, String> result = new HashMap<>();
        ResultSet res = DBHelper.ExecuteQuery(Queries.GetIdSurnameNameFromStudent(direction, group));
        try {
            while (res.next()) {
                result.put(res.getInt(1), res.getString(2) + " " + res.getString(3));
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /// Получить предметы из экзам ведомостей и их ИД
    public static Map<Integer, String> GetSubjects(String dirNumGroup, String semestr){
        String direction = dirNumGroup.split("-")[0];
        String group = dirNumGroup.split("-")[1];
        Map<Integer, String> result = new HashMap<>();
        ResultSet res = DBHelper.ExecuteQuery(Queries.GetIdTitleSubjectFromERecord(direction, group, semestr));
        try {
            while (res.next()) {
                result.put(res.getInt(1), res.getString(2));
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /// Возвращает рейтинг студента по предмету
    public static String GetTRatingFromIERecord(Integer studentId, Integer subjectId){
        ResultSet res = DBHelper.ExecuteQuery(Queries.GetTRatingIERecord(studentId, subjectId));
        try {
            while (res.next()) {
                return res.getString(1);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /// Обновить тотал рейтинг в экзам. ведомости.
    public static Integer UpdateTRatingFromIERecord(Integer studentId, Integer subjectId, Integer newRating){
        return DBHelper.ExecuteUpdate(Queries.UpdateTRatingIERecord(studentId, subjectId, newRating));
    }

    /// Добавить запись в таблицу разрешений
    public static Integer InsertTolerance(Integer studentId, Integer subjectId, Integer teacherId, Date date, Integer rating){
        return DBHelper.ExecuteUpdate(Queries.InsertTolerance(studentId, subjectId, teacherId, date, rating));
    }
}
