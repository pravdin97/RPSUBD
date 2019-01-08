package dao;

import utils.DBHelper;

import java.time.LocalDate;

public class NewCRecordDao {

    public void setNewCRecord(LocalDate date, String semestr, String faculty, String group) {

        DBHelper.ExecuteUpdate(Queries.setNewCRecord(date, semestr, faculty, group));

    }

}
