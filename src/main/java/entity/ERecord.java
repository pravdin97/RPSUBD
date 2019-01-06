package entity;

import java.util.Date;

public class ERecord {

    private boolean status;
    private Date date;
    private String subject;
    private String group;
    private String teacher;
    private String worker;

    public boolean getStatus() {
        return status;
    }

    public Date getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getGroup() {
        return group;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getWorker() {
        return worker;
    }

    public ERecord(boolean status, Date date, String subject, String group, String teacher, String worker) {
        this.status = status;
        this.date = date;
        this.subject = subject;
        this.group = group;
        this.teacher = teacher;
        this.worker = worker;
    }
}
