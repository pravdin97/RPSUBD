package dao;

import java.util.Date;

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

    /// Получить всех студентов с данной группы
    public static String GetIdSurnameNameFromStudent(String direction, String group) {
        return String.format("SELECT student.id, student.surname, student.name from student, groupp, direction WHERE " +
                "groupp.id_direction = direction.id AND " +
                "student.id_group = groupp.id AND " +
                "groupp.number = %s AND " +
                "direction.title = '%s'", group, direction);
    }

    /// Получить предметы по всем экз. ведомостям данной группы в данном семестре
    public static String GetIdTitleSubjectFromERecord(String direction, String group, String semestr) {
        return String.format("SELECT subject.id, subject.title from erecord, subject, semestr, groupp, direction WHERE\n" +
                "groupp.id_direction = direction.id AND\n" +
                "erecord.id_group = groupp.id AND\n" +
                "erecord.id_subject = subject.id AND\n" +
                "subject.id_semestr = semestr.id AND\n" +
                "semestr.title = '%s' AND\n" +
                "groupp.number = %s AND\n" +
                "direction.title = '%s'", semestr, group, direction);
    }

    /// Обновить тотал рейтинг в экзам. ведомости.
    public static String UpdateTRatingIERecord(Integer studentId, Integer subjectId, Integer newRating) {
        return String.format("UPDATE ierecord SET trating = %s WHERE (SELECT ierecord.id from ierecord, erecord WHERE\n" +
                "ierecord.id_erecord = erecord.id AND\n" +
                "erecord.id_subject = %s AND\n" +
                "ierecord.id_student = %s) = ierecord.id;", newRating, subjectId, studentId);
    }

    /// Достать рейтинг студента по предмету
    public static String GetTRatingIERecord(Integer studentId, Integer subjectId) {
        return String.format("SELECT ierecord.trating from ierecord, erecord WHERE\n" +
                "ierecord.id_erecord = erecord.id AND\n" +
                "erecord.id_subject = %s AND\n" +
                "ierecord.id_student = %s", subjectId, studentId);
    }

    /// Добавить запись в таблицу разрешений
    public static String InsertTolerance(Integer studentId, Integer subjectId, Integer teacherId, Date date, Integer rating) {
        java.sql.Date sDate = new java.sql.Date(date.getTime());
        return String.format("INSERT INTO tolerance " +
                "(date, erating, id_teacher, id_student, id_subject) " +
                "VALUES('%s', %s, %s, %s, %s)", sDate, rating, teacherId, studentId, subjectId);
    }
}
