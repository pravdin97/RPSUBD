package dao;

public class Queries {
    public static final String GET_TITLE_FROM_FACULTY = "select faculty.title from faculty";
    public static String GetTitleNumberFromFacultyDirectionByFacultyTitle(String faculty) {
        return "select direction.title, groupp.number from groupp, direction, faculty where " +
                "direction.id = groupp.id_direction and direction.id_faculty = faculty.id and faculty.title = '" + faculty + "'";
    }

    public static final String GET_TITLE_FROM_SEMESTR = "select semestr.title from semestr";
}
