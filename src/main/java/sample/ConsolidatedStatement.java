package sample;

import dao.ConsolidatedStatementDao;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    //endregion

    @FXML
    public void cb_faculty_show(){
        cb_faculty.getItems().clear();
        cb_faculty.getItems().addAll(ConsolidatedStatementDao.GetAllFaculty());
    }

    @FXML
    public void cb_group_show(){
        cb_group.getItems().clear();
        if (cb_faculty.getValue() == null)
            return;
        cb_group.getItems().addAll(ConsolidatedStatementDao.GetGroupByFaculty(cb_faculty.getValue().toString()));
    }

    @FXML
    public void cb_semestr_show(){
        cb_semestr.getItems().clear();
        if (cb_faculty.getValue() == null || cb_group.getValue() == null)
            return;
        cb_semestr.getItems().addAll(ConsolidatedStatementDao.GetAllSemestrs());
    }
}
