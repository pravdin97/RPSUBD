package dao;

import entity.GroupWorkerDepartment;
import entity.ScheduleItem;
import utils.DBHelper;

import java.sql.ResultSet;
import java.util.ArrayList;

import static dao.ConsolidatedStatementDao.GetStringGeneric;

public class WorkerDepartmentDao {

    // Возвращает список факультетов
    public static ArrayList<String> GetAllFaculty() {
        return ConsolidatedStatementDao.GetAllFaculty();
    }

    // Возвращает все напрвления
    public static  ArrayList<String> GetAllDirections(String faculty) {
        ResultSet result = DBHelper.ExecuteQuery(Queries.GetAllDirections(faculty));
        return GetStringGeneric(result, "title");
    }

    //
    public static ArrayList<GroupWorkerDepartment> GetDirectionWithNumber(String faculty) {

        ResultSet res = DBHelper.ExecuteQuery(Queries.GetFacultyAndDirectionWithNumber(faculty));
        ArrayList<GroupWorkerDepartment> result = new ArrayList<>();
        try {
            while (res.next()) {
                result.add(new GroupWorkerDepartment(
                        res.getString(1),
                        res.getString(2),
                        res.getString(3)));
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}
