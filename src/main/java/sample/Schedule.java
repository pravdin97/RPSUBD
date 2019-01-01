package sample;

import dao.ScheduleDao;
import entity.ScheduleItem;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class Schedule {

    //region Def
    @FXML
    ComboBox cb_group;

    @FXML
    ComboBox cb_faculty;

    @FXML
    TableView stable;
    //endregion

    @FXML
    public void cb_faculty_show() {
        cb_faculty.getItems().clear();
        cb_faculty.getItems().addAll(ScheduleDao.GetAllFaculty());
    }

    @FXML
    public void cb_group_show() {
        cb_group.getItems().clear();
        if (cb_faculty.getValue() == null) {
            cb_group.getItems().add("Выберите факультет");
            return;
        }
        cb_group.getItems().addAll(ScheduleDao.GetGroup(cb_faculty.getValue().toString()));
    }

    @FXML
    public void cb_group_hide() {
        if (cb_faculty.getValue() == null)
            return;
        ArrayList<ScheduleItem> tableItems = ScheduleDao.GetSchedule(cb_group.getValue().toString());
        stable.getColumns().clear();
        stable.setItems(FXCollections.observableArrayList(tableItems));

        if (tableItems.size() == 0)
            return;
        TableColumn<ScheduleItem, String> tcSubject = new TableColumn<ScheduleItem, String>("Дисциплина");
        tcSubject.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("subject"));
        stable.getColumns().add(tcSubject);

        TableColumn<ScheduleItem, String> tcRoom = new TableColumn<ScheduleItem, String>("Аудитория");
        tcRoom.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("room"));
        stable.getColumns().add(tcRoom);

        TableColumn<ScheduleItem, String> tcDate = new TableColumn<ScheduleItem, String>("Дата");
        tcDate.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("date"));
        stable.getColumns().add(tcDate);

        TableColumn<ScheduleItem, String> tcTeacher = new TableColumn<ScheduleItem, String>("Преподаватель");
        tcTeacher.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("teacher"));
        stable.getColumns().add(tcTeacher);
    }
}
