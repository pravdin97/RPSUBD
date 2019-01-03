package entity;

public class IERecord {
    private Integer ID;
    private Integer StudentID;
    private Integer ERecordID;
    private String StudentName;
    private String StudentSurname;
    private Integer SRating;
    private Integer ERating;
    private Integer TRating;

    public IERecord(Integer ID, Integer studentID, Integer ERecordID, String studentName, String studentSurname, Integer SRating, Integer ERating, Integer TRating) {
        this.ID = ID;
        StudentID = studentID;
        this.ERecordID = ERecordID;
        StudentName = studentName;
        StudentSurname = studentSurname;
        this.SRating = SRating;
        this.ERating = ERating;
        this.TRating = TRating;
    }


}
