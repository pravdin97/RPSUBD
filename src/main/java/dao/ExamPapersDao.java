package dao;

import entity.IERecord;
import utils.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExamPapersDao {
    public static ArrayList<IERecord> getAllRecords(String subject, String group) {
        String direction = group.split("-")[0];
        group = group.split("-")[1];

        ArrayList<IERecord> items = new ArrayList<>();

        ResultSet result = DBHelper.ExecuteQuery(Queries.GetAllExamPaperItems(subject, direction, group));
        try {
            while (result.next()) {
                Integer id, studentId, erecordId, srating, erating, trating;
                String studentName, studentSurname;
                id = result.getInt("id");
                studentId = result.getInt("id_student");
                erecordId = result.getInt("id_erecord");
                srating = result.getInt("srating");
                erating = result.getInt("erating");
                trating = result.getInt("trating");
                studentName = result.getString("name");
                studentSurname = result.getString("surname");

                items.add(new IERecord(id, studentId, erecordId, studentName, studentSurname, srating, erating, trating));
            }
        }catch (SQLException e) {
            return null;
        }
        return items;
    }

    public static String getInfo(String subject, String group) {
        String direction = group.split("-")[0];
        group = group.split("-")[1];

        ResultSet result = DBHelper.ExecuteQuery(Queries.GetExamPaperInfo(subject, direction, group));
        String info = "";
        try {
            if (result.next())
                result.getInt("group");
            String dir = result.getString("direction");

            info = "Экзаменационная ведомость группы " + result.getString("direction") + "-" +
                    result.getInt("group") + " по дисциплине \"" + result.getString("subject") +
                    "\" была сформирована " + result.getString("workerName") + " " +
                    result.getString("workerSurname") + " " + result.getDate("date");
        } catch (SQLException e) {
            return null;
        }
        return info;
    }
}
