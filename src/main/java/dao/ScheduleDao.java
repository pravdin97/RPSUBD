package dao;

import entity.ScheduleItem;
import utils.DBHelper;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ScheduleDao {
    public static ArrayList<String> GetAllFaculty() {
        return ConsolidatedStatementDao.GetAllFaculty();
    }

    public static ArrayList<String> GetGroup(String faculty) {
        return ConsolidatedStatementDao.GetGroup(faculty);
    }

    public static ArrayList<ScheduleItem> GetSchedule(String dirNumGroup) {
        String direction = dirNumGroup.split("-")[0];
        String group = dirNumGroup.split("-")[1];
        ResultSet res = DBHelper.ExecuteQuery(Queries.GetSchedule(direction, group));
        ArrayList<ScheduleItem> result = new ArrayList<>();
        try {
            while (res.next()) {
                result.add(new ScheduleItem(res.getInt("id"),
                        res.getString("title"),
                        res.getString("room"),
                        res.getDate("date"),
                        res.getString("teacher")));
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}
