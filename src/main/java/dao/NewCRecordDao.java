package dao;

import utils.DBHelper;
import utils.Queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class NewCRecordDao {

    public static Integer getIDGroup(String faculty, String direction, String group_number) {
        ResultSet res = DBHelper.ExecuteQuery(Queries.getIDGroup(faculty, direction, group_number));
        try {
            while (res.next()) {
                try {
                    return res.getInt("id");
                } catch (SQLException e) {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }


    public static void setNewCRecord(LocalDate date, int id_group, int id_worker, int id_semestr) {
        Integer suc = DBHelper.ExecuteUpdate(Queries.setNewCRecord(date, id_group, id_worker, id_semestr));
    }

}
