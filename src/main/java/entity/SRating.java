package entity;

public class SRating {
    String subject;
    String rating;

    public String getRating() {
        return rating;
    }

    public String getSubject() {
        return subject;
    }

    public SRating(String subject, String rating){
        this.subject = subject;
        this.rating = rating;
    }
}
