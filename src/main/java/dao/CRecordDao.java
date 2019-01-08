package dao;

import entity.CRecord;
import utils.DBHelper;
import utils.Queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CRecordDao {

    // Возвращает результаты для таблицы сводной ведомости
    public static ArrayList<CRecord> getAllCRecordForFaculty(String faculty) {

        ResultSet res = DBHelper.ExecuteQuery(Queries.getAllFromCRecord(faculty));
        ArrayList<CRecord> result = new ArrayList<>();
        try {
            while (res.next()) {
                result.add(new CRecord(
                        res.getDate("date"),
                        res.getString("group_name"),
                        res.getString("worker_info"),
                        res.getInt("number")));
            }
        } catch (SQLException e) {
            return null;
        }
        return result;
    }
}
