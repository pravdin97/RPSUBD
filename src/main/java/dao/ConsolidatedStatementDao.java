package dao;

import entity.ICRecord;
import utils.DBHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public static ArrayList<ICRecord> GetICRecords(String group, String semestr, Integer CRecordID) {
        ArrayList<ICRecord> result = new ArrayList<ICRecord>();
        Map<Integer, String> subjects = GetSubjects(group, semestr);
        // Предмет - Рейтинг
        Map<String, String> ratings;
        Integer ICRecordId, StudentID;
        String StudentName, StudentSurname;

        ResultSet lines = DBHelper.ExecuteQuery(Queries.GetDataFromCRecord(CRecordID));

        try {
            while (lines.next()) {
                ICRecordId = lines.getInt("icrecordid");
                StudentID = lines.getInt("studentid");
                StudentName = lines.getString("studentname");
                StudentSurname = lines.getString("studentsurname");
                // ИД - Рейтинг
                result.add(new ICRecord(ICRecordId, StudentID, StudentName, StudentSurname));
            }
        } catch (Exception e) {}
        for (ICRecord item : result) {
            ratings = new HashMap<String, String>();
            Map<Integer, String> data = GetSRatings(item.getID());
            for (int i = 0; i < subjects.size(); i++) {
                Object key = subjects.keySet().toArray()[i];
                if (data.containsKey(key)) {
                    ratings.put(subjects.get(key), data.get(key));
                } else {
                    ratings.put(subjects.get(key), "-");
                }
            }
        }
        return result;
    }

    // ID - Рейтинг
    static Map<Integer, String> GetSRatings(Integer ICRecordID) {
        ResultSet lines = DBHelper.ExecuteQuery(Queries.GetDataFromICRecord(ICRecordID));
        Map<Integer, String> ratings = new HashMap<Integer, String>();
        try {
            while (lines.next()) {
                ratings.put(lines.getInt("subjectid"), lines.getString("rating"));
            }
        } catch (Exception e) {
        }
        return ratings;
    }

    // ID и Предмет
    static Map<Integer, String> GetSubjects(String group, String semestr) {
        String direction = group.split("-")[0];
        group = group.split("-")[1];
        ResultSet subj_ = DBHelper.ExecuteQuery(Queries.GetIdSubjectFromERecord(group, direction, semestr));
        Map<Integer, String> result = new HashMap<Integer, String>();
        try {
            while (subj_.next()) {
                result.put(subj_.getInt(1), subj_.getString(2));
            }
        } catch (Exception e) {
        }
        return result;
    }
}
