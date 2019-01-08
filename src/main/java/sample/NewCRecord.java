package sample;

import dao.ConsolidatedStatementDao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

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

    private String group_id = "";

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

    //TODO: Получить id для этого надо написать запросы
    @FXML
    public void btn_add_new_crecord() {
        LocalDate localDate = dp_date.getValue();
        String semestr = cb_semestr.getValue();
        String faculty = cb_faculty.getValue();
        String group = cb_group.getValue();

        //group_id = ConsolidatedStatementDao.GetIDGroup(cb_faculty.getValue());

    }



}
