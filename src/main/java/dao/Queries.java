package dao;

public class Queries {
    /// Получить все названия факультетов
    public static final String GET_TITLE_FROM_FACULTY = "select faculty.title from faculty";

    /// Получить все направления + номер группы по факультету
    public static String GetTitleNumberFromFacultyDirection(String faculty) {
        return "select direction.title, groupp.number from groupp, direction, faculty where " +
                "direction.id = groupp.id_direction and direction.id_faculty = faculty.id and faculty.title = '" + faculty + "'";
    }

    /// Получить ИД сводной ведомости + ее составителя и дату ее создания заданной группы в данном семестре
    public static String GetIdDateWorkDateFromCRecord(String group, String direction, String semestr) {
        return String.format("select crecord.id, (worker.surname || ' ' || worker.name) as work, date from crecord, groupp, direction,worker,semestr where \n" +
                "groupp.id_direction = direction.id AND\n" +
                "crecord.id_semestr = semestr.id AND\n" +
                "crecord.id_group = groupp.id AND\n" +
                "groupp.number = '%s' AND\n" +
                "direction.title = '%s' AND\n" +
                "semestr.title = '%s';", group, direction, semestr);
    }

    /// Получить все ид предмета и его названия из экзаменационных ведомостей
    public static String GetIdSubjectFromERecord(String group, String direction, String semestr) {
        return String.format("select subject.id, subject.title from erecord, subject, groupp, direction, semestr where \n" +
                "erecord.id_subject = subject.id AND\n" +
                "groupp.id = erecord.id_group AND\n" +
                "subject.id_semestr = semestr.id AND\n" +
                "groupp.id_direction = direction.id AND\n" +
                "groupp.number = '%s' AND\n" +
                "direction.title = '%s' AND\n" +
                "semestr.title = '%s' ORDER BY title", group, direction, semestr);
    }

    /// Получить все записи сводной ведомости
    public static String GetDataFromCRecord(Integer CrecordID) {
        return String.format("SELECT * from GetCRecordData(%s)", CrecordID);
    }

    /// Получить все оценки для записи данного студента
    public static String GetDataFromICRecord(Integer ICrecordID) {
        return String.format("SELECT * from GetICRecordData(%s)", ICrecordID);
    }

    /// Получить все названия семестров
    public static final String GET_TITLE_FROM_SEMESTR = "select title from semestr ORDER BY title";
}
