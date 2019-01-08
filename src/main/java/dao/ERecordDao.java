package dao;

import entity.ERecord;
import utils.DBHelper;
import utils.Queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ERecordDao {


    // Возвращает результаты для таблицы сводной ведомости
    public static ArrayList<ERecord> getAllERecordForFaculty(String faculty) {

        ResultSet res = DBHelper.ExecuteQuery(Queries.getAllFromERecord(faculty));
        ArrayList<ERecord> result = new ArrayList<>();
        try {
            while (res.next()) {
                result.add(new ERecord(
                        res.getBoolean("status"),
                        res.getDate("date"),
                        res.getString("subject_name"),
                        res.getString("group_name"),
                        res.getString("teacher_info"),
                        res.getString("worker_info")));
            }
        } catch (SQLException e) {
            return null;
        }
        return result;
    }
}

