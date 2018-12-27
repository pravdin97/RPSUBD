package dao;

import utils.DBHelper;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ConsolidatedStatementDao {
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

    public static ArrayList<String> GetAllFaculty() {
        ResultSet result = DBHelper.ExecuteQuery(Queries.GET_TITLE_FROM_FACULTY);
        return GetStringGeneric(result, "title");
    }

    public static ArrayList<String> GetGroup(String faculty) {
        ResultSet result = DBHelper.ExecuteQuery(Queries.GetTitleNumberFromFacultyDirection(faculty));
        ArrayList<String> ret = new ArrayList<String>();
        try {
            while (result.next()) {
                ret.add(result.getString("title") + "-" + result.getString("number"));
            }
        } catch (Exception e) {
        }
        return ret;
    }

    public static ArrayList<String> GetAllSemestrs() {
        ResultSet result = DBHelper.ExecuteQuery(Queries.GET_TITLE_FROM_SEMESTR);
        return GetStringGeneric(result, "title");
    }

    public static String GetCRecordInfo(String group, String semestr) {
        String direction = group.split("-")[0];
        group = group.split("-")[1];
        ResultSet result = DBHelper.ExecuteQuery(Queries.GetIdDateWorkDateFromCRecord(group, direction, semestr));
        String ret = null;
        try {
            while (result.next()) {
                return String.format("Сводная ведомость на %s для группы %s за %s. Сформировал: %s",
                        result.getString("date"),
                        direction + "-" + group,
                        semestr,
                        result.getString("work"));
            }
        } catch (Exception e) {
        }
        return "Ведомости с заданными параметрами не найдены";
    }
}
