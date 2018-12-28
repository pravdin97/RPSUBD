package sample;

import dao.ConsolidatedStatementDao;
import entity.ICRecord;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

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
        if (cb_faculty.getValue() == null || cb_group.getValue() == null || cb_semestr.getValue() == null) {
            lb_info.setText("Заполните все параметры выбора сводной ведомости");
            return;
        }
        ctable.getColumns().clear();
        String crecordInfo = ConsolidatedStatementDao.GetCRecordInfo(cb_group.getValue().toString(), cb_semestr.getValue().toString());
        if (crecordInfo == null) {
            lb_info.setText("Ведомости с заданными параметрами не найдены");
            return;
        }

        lb_info.setText(crecordInfo);
        Map<Integer, String> subjcts = ConsolidatedStatementDao.GetSubjects(cb_group.getValue().toString(), cb_semestr.getValue().toString());
        ArrayList<ICRecord> icRecords = ConsolidatedStatementDao.GetICRecords(cb_group.getValue().toString(), cb_semestr.getValue().toString());
        Collections.sort(icRecords);
        ctable.setItems(FXCollections.observableArrayList(icRecords));
        TableColumn<ICRecord, String> tableColumnStudents = new TableColumn<ICRecord, String>("Студент");
        tableColumnStudents.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getStudent()));
        ctable.getColumns().add(tableColumnStudents);

        // Проходит по предметам и заносит информацию в колонки
        for (Integer key : subjcts.keySet()) {
            String value = subjcts.get(key);
            TableColumn<ICRecord, String> tableColumn = new TableColumn<ICRecord, String>(value);
            tableColumn.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().GetRating(key)));
            ctable.getColumns().add(tableColumn);
        }
    }
}
