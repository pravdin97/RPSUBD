package sample;

import dao.ConsolidatedStatementDao;
import dao.NewCRecordDao;
import entity.CurrentUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class NewCRecord {

    @FXML
    private DatePicker dp_date;

    @FXML
    private ComboBox<String> cb_semestr;

    @FXML
    private ComboBox<String> cb_faculty;

    @FXML
    private ComboBox<String> cb_group;

    @FXML
    private Button btn_add_new_crecord;

    private Integer group_id;

    private CurrentUser currentUser;

    private WorkerDepartment workerDepartment;

    @FXML
    public void cb_semestr_show() {
        cb_semestr.getItems().clear();
        cb_semestr.getItems().addAll(ConsolidatedStatementDao.GetAllSemestrs());
    }

    @FXML
    public void cb_faculty_show() {
        cb_faculty.getItems().clear();
        if (cb_semestr.getValue() == null) {
            cb_faculty.getItems().add("Заполните поля выше");
            return;
        }
        cb_faculty.getItems().addAll(ConsolidatedStatementDao.GetAllFaculty());
    }

    @FXML
    public void cb_group_show() {
        cb_group.getItems().clear();
        if (cb_semestr.getValue() == null || cb_faculty.getValue() == null) {
            cb_group.getItems().add("Заполните поля выше");
            return;
        }
        cb_group.getItems().addAll(ConsolidatedStatementDao.GetGroup(cb_faculty.getValue()));

    }

    @FXML
    public void btn_add_new_crecord() {
        LocalDate localDate = dp_date.getValue();
        String semestr = cb_semestr.getValue();
        String faculty = cb_faculty.getValue();

        String group_info = cb_group.getValue();

        String direction = group_info.split("-")[0];
        String group_number = group_info.split("-")[1];
        int semestr_number = Integer.parseInt(semestr.split(" ")[0]);

        group_id = NewCRecordDao.getIDGroup(faculty, direction, group_number);

        NewCRecordDao.setNewCRecord(localDate, group_id, currentUser.getId(), semestr_number);


        ((Stage) btn_add_new_crecord.getScene().getWindow()).close();
    }

    public void setUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

}
