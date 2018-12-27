package dao;

import utils.DBHelper;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ConsolidatedStatementDao {
    static ArrayList<String> GetStringGeneric(ResultSet result, String column){
        ArrayList<String> ret = new ArrayList<String>();
        try {
            while (result.next()) {
                ret.add(result.getString(column));
            }
        } catch (Exception e) { }
        return ret;
    }

    public static ArrayList<String> GetAllFaculty() {
        ResultSet result = DBHelper.ExecuteQuery(Queries.GET_TITLE_FROM_FACULTY);
        return GetStringGeneric(result, "title");
    }

    public static ArrayList<String> GetGroupByFaculty(String faculty) {
        ResultSet result = DBHelper.ExecuteQuery(Queries.GetTitleNumberFromFacultyDirectionByFacultyTitle(faculty));
        ArrayList<String> ret = new ArrayList<String>();
        try {
            while (result.next()) {
                ret.add(result.getString("title") + "-" + result.getString("number"));
            }
        } catch (Exception e) { }
        return ret;
    }

    public static ArrayList<String> GetAllSemestrs() {
        ResultSet result = DBHelper.ExecuteQuery(Queries.GET_TITLE_FROM_SEMESTR);
        return GetStringGeneric(result, "title");
    }
}
