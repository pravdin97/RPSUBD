package sample;

import dao.ConsolidatedStatementDao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class ConsolidatedStatement {
    //region Def
    @FXML
    Button bt_action;

    @FXML
    TableView ctable;

    @FXML
    ComboBox cb_faculty;

    @FXML
    ComboBox cb_semestr;

    @FXML
    ComboBox cb_group;

    @FXML
    Label lb_info;
    //endregion

    @FXML
    public void cb_faculty_show() {
        cb_faculty.getItems().clear();
        cb_faculty.getItems().addAll(ConsolidatedStatementDao.GetAllFaculty());
    }

    @FXML
    public void cb_group_show() {
        cb_group.getItems().clear();
        if (cb_faculty.getValue() == null) {
            cb_group.getItems().add("Заполните поля выше");
            return;
        }
        cb_group.getItems().addAll(ConsolidatedStatementDao.GetGroup(cb_faculty.getValue().toString()));
    }

    @FXML
    public void cb_semestr_show() {
        cb_semestr.getItems().clear();
        if (cb_faculty.getValue() == null || cb_group.getValue() == null) {
            cb_semestr.getItems().add("Заполните поля выше");
            return;
        }
        cb_semestr.getItems().addAll(ConsolidatedStatementDao.GetAllSemestrs());
    }

    @FXML
    public void bt_action_form() {
        if (cb_faculty.getValue() == null || cb_group.getValue() == null || cb_group == null) {
            lb_info.setText("Заполните все параметры выбора сводной ведомости");
            return;
        }
        lb_info.setText(ConsolidatedStatementDao.GetCRecordInfo(cb_group.getValue().toString(), cb_semestr.getValue().toString()));
    }
}
