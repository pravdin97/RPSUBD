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
        cb_faculty.getItems().addAll(ConsolidatedStatementDao.GetAllSemestr());
        System.out.println(cb_faculty.getValue());
    }
}
