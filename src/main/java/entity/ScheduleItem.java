package entity;

import java.util.Date;

public class ScheduleItem {
    Integer id;
    String subject;
    String room;
    Date date;
    String teacher;

    public String getSubject() {
        return subject;
    }

    public Date getDate() {
        return date;
    }

    public String getRoom() {
        return room;
    }

    public String getTeacher() {
        return teacher;
    }

    public ScheduleItem(Integer id, String subject, String room, Date date, String teacher) {
        this.id = id;
        this.subject = subject;
        this.room = room;
        this.date = date;
        this.teacher = teacher;
    }
}
