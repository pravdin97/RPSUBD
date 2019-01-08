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

public class ConsolidatedStatementContent {

    @FXML
    TableView ctable;

    @FXML
    Label lb_info;

    String  group, semestr;

    public void setValues( String group, String semestr) {
        this.group = group;
        this.semestr = semestr;
    }


    public void init() {
        ctable.getColumns().clear();
        String crecordInfo = ConsolidatedStatementDao.GetCRecordInfo(group, semestr);
        if (crecordInfo == null) {
            lb_info.setText("Ведомости с заданными параметрами не найдены");
            return;
        }

        lb_info.setText(crecordInfo);
        Map<Integer, String> subjcts = ConsolidatedStatementDao.GetSubjects(group, semestr);
        ArrayList<ICRecord> icRecords = ConsolidatedStatementDao.GetICRecords(group, semestr);
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
