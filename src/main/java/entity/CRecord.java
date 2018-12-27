package entity;

public class CRecord {
    String Direction;
    String Group;
    String Date;
    String Worker;
    String Semestr;
    ICRecord data;

    public ICRecord getData() {
        return data;
    }

    public String getDate() {
        return Date;
    }

    public String getDirection() {
        return Direction;
    }

    public String getGroup() {
        return Group;
    }

    public String getSemestr() {
        return Semestr;
    }

    public String getWorker() {
        return Worker;
    }


}
