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

    // Получить все направления
    public static String GetAllDirections(String faculty) {
        return "select direction.title from direction, faculty where " +
                "direction.id_faculty = faculty.id and faculty.title = '" + faculty + "'";
    }

    // Получить всех студентов с факультета
    public static String GetAllStudentsFromFaculty(String faculty) {
        return String.format("select * from student, faculty, direction, groupp where\n" +
                "student.id_group = groupp.id and\n" +
                "groupp.id_direction = direction.id and\n" +
                "faculty.id = direction.id_faculty and\n" +
                "faculty.title = '%s'", faculty);
    }

    // Получить всех студентов с напрвления
    public static String GetAllStudentsFromDirection(String direction) {
        return String.format("select * from student, groupp, direction where\n" +
                "student.id_group = groupp.id and\n" +
                "groupp.id_direction = direction.id and\n" +
                "direction.title = '%s'", direction);
    }

    // Получить напрваление с номером(группу) из факультета
    public static String GetFacultyAndDirectionWithNumber(String faculty) {
        return String.format("select faculty.title, direction.title, groupp.number from direction, faculty, groupp where " +
                "faculty.id = direction.id_faculty and "+
                "direction.id = groupp.id_direction and " +
                "faculty.title = '%s'", faculty);
    }

    // Получить все записи сводной ведомости для факультета
    public static String getAllFromCRecord(String faculty) {
        return String.format("select date, " +
                "(direction.title || ' ' || groupp.number) as group_name, \n" +
                "(worker.surname || ' ' || worker.name || ' ' || worker.midname) as worker_info,\n" +
                "semestr.number from\n" +
                "crecord, worker, semestr, groupp, direction, faculty\n" +
                "where\n" +
                "direction.id_faculty = faculty.id and\n" +
                "groupp.id_direction = direction.id and\n" +
                "crecord.id_group = groupp.id and\n" +
                "crecord.id_semestr = semestr.id and\n" +
                "crecord.id_worker = worker.id and\n" +
                "faculty.title = '%s'" +
                "order by date", faculty);
    }

    // Получить все записи экзаменнационной ведомости для факультета
    public static String getAllFromERecord(String faculty) {
        return String.format("select \tdate, \n" +
                "\t\t(direction.title || ' ' || groupp.number) as group_name, \n" +
                "\t\t(worker.surname || ' ' || worker.name || ' ' || worker.midname) as worker_info,\n" +
                "\t\t(teacher.surname || ' ' || teacher.name || ' ' || teacher.midname) as teacher_info,\n" +
                "\t\tsubject.title as subject_name,\n" +
                "\t\terecord.iscompleted as status \n" +
                "\t\t\n" +
                "\t\tfrom\n" +
                "\t\terecord, worker, teacher, groupp, direction, faculty, subject\n" +
                "\t\t\n" +
                "\t\twhere\n" +
                "\t\tdirection.id_faculty = faculty.id and\n" +
                "\t\tgroupp.id_direction = direction.id and\n" +
                "\t\terecord.id_group = groupp.id and\n" +
                "\t\terecord.id_worker = worker.id and\n" +
                "\t\terecord.id_subject = subject.id and\n" +
                "\t\terecord.id_teacher = teacher.id and\n" +
                "\t\tfaculty.title = '%s'\n" +
                "\t\torder by date", faculty);
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

    /// Проверка логина препода
    public static String CheckLoginTeacher(String login, String password) {
        return String.format("select * from checkloginteacher('%s', '%s')", login, password);
    }

    /// Проверка логина работяги
    public static String CheckLoginWorker(String login, String password) {
        return String.format("select * from checkloginworker('%s', '%s')", login, password);
    }

    /// Достать ФИО препода
    public static String GetLoginTeacher(String login, String password) {
        return String.format("select surname, name from teacher where teacher.login = '%s' AND teacher.pass = '%s'", login, password);
    }

    /// Достать ФИО работяги
    public static String GetLoginWorker(String login, String password) {
        return String.format("select surname, name from worker where worker.login = '%s' AND worker.pass = '%s'", login, password);
    }

    /// Возвращает расписание экзаменов данной группы
    public static String GetSchedule(String direction, String group) {
        return String.format("SELECT ischedule.id, subject.title, ischedule.room, ischedule.date, teacher.surname || ' ' ||teacher.name || ' ' || teacher.midname as teacher FROM ischedule, schedule, subject, teacher, groupp, direction WHERE " +
                "ischedule.id_schedule = schedule.id AND " +
                "schedule.id_group = groupp.id AND " +
                "ischedule.id_subject = subject.id AND " +
                "ischedule.id_teacher = teacher.id AND " +
                "groupp.id_direction = direction.id AND " +
                "direction.title = '%s' AND " +
                "groupp.number = '%s'", direction, group);
    }

    //Возвращает все записи экзаменационной ведомости
    public static String GetAllExamPaperItems(String subject, String direction, String group) {
        return "select ierecord.id, erecord.id_student, ierecord.id_erecord, student.surname, student.name, ierecord.srating, ierecord.erating, ierecord.trating \n" +
                "from ierecord, erecord, groupp, direction, subject, student\n" +
                "where ierecord.id_student = student.id AND\n" +
                "ierecord.id_erecord = erecord.id AND\n" +
                "erecord.id_group = groupp.id AND\n" +
                "groupp.id_direction = direction.id AND\n" +
                "erecord.id_subject = subject.id AND\n" +
                "subject.title = '" + subject + "' AND\n" +
                "direction.title = ' " + direction + "' AND\n" +
                "groupp.number = '" + group + "'\n";
    }

    public static String GetExamPaperInfo(String subject, String direction, String group) {
        return "select erecord.id, iscompleted, date, subject.title as subject, direction.title as direction, groupp.number as group, teacher.name as teacherName, teacher.surname as teacherSurname, worker.name as workerName, worker.surname as workerSurname\n" +
                "from erecord, subject, direction, groupp, teacher, worker\n" +
                "where erecord.id_subject = subject.id AND\n" +
                "erecord.id_group = groupp.id AND\n" +
                "groupp.id_direction = direction.id AND\n" +
                "erecord.id_teacher = teacher.id AND\n" +
                "erecord.id_worker = worker.id AND\n" +
                "subject.title = '" + subject + "' AND\n" +
                "direction.title = ' " + direction + "' AND\n" +
                "groupp.number = '" + group + "'\n";
    }

    public static void CreateNewExamPaper() {

    }
}
