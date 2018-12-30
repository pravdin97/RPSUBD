package sample;

import dao.ToleranceDao;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class Tolerance {
    final Integer TeacherID = 1;
    Map<Integer, String> Students, Subjects;
    Integer studentID, subjectID;
    //region Def
    @FXML
    Label lb_info;

    @FXML
    ComboBox cb_faculty;

    @FXML
    ComboBox cb_group;

    @FXML
    ComboBox cb_fio;

    @FXML
    ComboBox cb_subject;

    @FXML
    DatePicker dp_date;

    @FXML
    TextField tf_old;

    @FXML
    TextField tf_new;

    @FXML
    Button bt_change;

    @FXML
    ComboBox cb_semestr;
    //endregion

    @FXML
    public void cb_faculty_show() {
        cb_faculty.getItems().clear();
        cb_faculty.getItems().addAll(ToleranceDao.GetAllFaculty());
    }

    @FXML
    public void cb_group_show() {
        cb_group.getItems().clear();
        if (cb_faculty.getValue() == null)
            return;
        cb_group.getItems().addAll(ToleranceDao.GetGroup(cb_faculty.getValue().toString()));
    }

    @FXML
    public void cb_fio_show() {
        cb_fio.getItems().clear();
        if (cb_faculty.getValue() == null || cb_group.getValue() == null)
            return;
        Students = ToleranceDao.GetAllStudents(cb_group.getValue().toString());
        cb_fio.getItems().addAll(Students.values());
    }

    /// При закрытии комбобоксов
    public void BoxHiding() {
        TryBox();
    }

    @FXML
    public void cb_subject_show() {
        cb_subject.getItems().clear();
        if (cb_semestr.getValue() == null)
            return;
        Subjects = ToleranceDao.GetSubjects(cb_group.getValue().toString(), cb_semestr.getValue().toString());
        cb_subject.getItems().addAll(Subjects.values());
    }

    @FXML
    public void bt_change_click() {
        ToleranceDao.UpdateTRatingFromIERecord(studentID, subjectID, Integer.parseInt(tf_new.getText()));
        lb_info.setText(String.format("Рейтинг обновлен: %s -> %s", tf_old.getText(),  ToleranceDao.GetTRatingFromIERecord(studentID, subjectID)));
        Instant instant = Instant.from(dp_date.getValue().atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        //А этот кусок не работает - ошибка sql
        //ToleranceDao.InsertTolerance(studentID, subjectID, TeacherID, date, Integer.parseInt(tf_new.getText()));
    }

    @FXML
    public void cb_semestr_show() {
        cb_semestr.getItems().clear();
        cb_semestr.getItems().addAll(ToleranceDao.GetAllSemestrs());
    }

    /// Устанавливает состояние для элементов
    void TryBox() {
        if (IsFilledRPanel()) {
            dp_date.setDisable(false);
            cb_subject.setDisable(false);
            cb_semestr.setDisable(false);
            lb_info.setText("Заполните правую часть полей");
        } else {
            dp_date.setDisable(true);
            cb_subject.setDisable(true);
            cb_semestr.setDisable(true);
        }
        if (IsFilledAllPanel()) {
            studentID = -1;
            subjectID = -1;
            for (Integer key : Students.keySet()) {
                if (Students.get(key) == cb_fio.getValue().toString()) {
                    studentID = key;
                    break;
                }
            }
            for (Integer key : Subjects.keySet()) {
                if (Subjects.get(key) == cb_subject.getValue().toString()) {
                    subjectID = key;
                    break;
                }
            }
            bt_change.setDisable(false);
            tf_old.setText(ToleranceDao.GetTRatingFromIERecord(studentID, subjectID));
            lb_info.setText("Введите новое значение");
        } else {
            bt_change.setDisable(true);
        }
    }

    /// Проверка на заполненость левой панели
    boolean IsFilledRPanel() {
        if (cb_faculty.getValue() == null || cb_group.getValue() == null || cb_fio.getValue() == null)
            return false;
        return true;
    }

    /// Проверка на заполненость всей инфы
    boolean IsFilledAllPanel() {
        if (!IsFilledRPanel() || dp_date.getValue() == null || cb_subject.getValue() == null || cb_semestr.getValue() == null)
            return false;
        return true;
    }
}
