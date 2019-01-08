package sample;

import dao.ConsolidatedStatementDao;
import dao.ExamPapersDao;
import entity.CRecord;
import entity.ICRecord;
import entity.IERecord;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class ExamPaperContent {

    @FXML
    TableView<IERecord> ctable;

    @FXML TableColumn<IERecord, String> studentName;
    @FXML TableColumn<IERecord, String> studentSurname;
    @FXML TableColumn<IERecord, Integer> sRating;
    @FXML TableColumn<IERecord, Integer> eRating;
    @FXML TableColumn<IERecord, Integer> tRating;

    private ObservableList<IERecord> items = FXCollections.observableArrayList();


    @FXML
    Label lb_info;

    String  group, subject;

    public void setValues( String group, String subject) {
        this.group = group;
        this.subject = subject;
    }


    public void init() {
        ctable.getColumns().clear();
        String erecordInfo = ExamPapersDao.getInfo(subject, group);
        if (erecordInfo == null) {
            lb_info.setText("Ведомости с заданными параметрами не найдены");
            return;
        }

        lb_info.setText(erecordInfo);

        ArrayList<IERecord> data = ExamPapersDao.getAllRecords(subject, group);
//        for (IERecord ieRecord : data) {
//            items.add(ieRecord);
//        }

        items.addAll(data);
    }
}
