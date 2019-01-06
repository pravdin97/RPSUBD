package entity;

import java.util.Date;

public class CRecord {

    private Date date;
    private String group;
    private String worker;
    private int semestr;

    public Date getDate() {
        return date;
    }

    public String getGroup() {
        return group;
    }

    public String getWorker() {
        return worker;
    }

    public int getSemestr() {
        return semestr;
    }

    public CRecord(Date date, String group, String worker, int semestr) {
        this.date = date;
        this.group = group;
        this.worker = worker;
        this.semestr = semestr;
    }
}
