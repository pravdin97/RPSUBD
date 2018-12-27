package dao;

import utils.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ConsolidatedStatementDao {
    public static ArrayList<String> GetAllSemestr() {
        ArrayList<String> ret = new ArrayList<String>();
        String query = "select faculty.title from faculty";
        ResultSet result = DBHelper.ExecuteQuery(query);
        try {
        while (result.next())
        {
            ret.add(result.getString("title"));
        }
        } catch (Exception e){
        }
        return ret;
    }
}
