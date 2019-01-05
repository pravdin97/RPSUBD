package sample;

import dao.WorkerDepartmentDao;
import entity.GroupWorkerDepartment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WorkerDepartment implements Initializable {


    // Все что относится к таблице "Группы"
    @FXML
    private TableView<GroupWorkerDepartment> tv_group;
    @FXML
    private TableColumn<GroupWorkerDepartment, String> id_faculty;
    @FXML
    private TableColumn<GroupWorkerDepartment, String> id_direction;
    @FXML
    private TableColumn<GroupWorkerDepartment, String> id_number;
    private ObservableList<GroupWorkerDepartment> groupDepartmentData = FXCollections.observableArrayList();


    @FXML
    private Button btn_add_student;

    @FXML
    private Tab tp_group;


    @FXML
    private Button bt_add_group;

    @FXML
    private Tab tp_record;

    @FXML
    private Button btn_delete_student;

    @FXML
    private Button bt_delete_group;

    @FXML
    private TabPane tp_navigation;

    @FXML
    private Tab tp_student;

    @FXML
    private TableView<String> tv_student;

    @FXML
    private TreeView<String> treev_navigation;

    // Дерево навигации
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TreeItem<String> root = new TreeItem<>("АлтГТУ им. И.И. Ползунова");
        treev_navigation.setRoot(root);
        root.setExpanded(true);

        ArrayList<String> facultyTreeItems = WorkerDepartmentDao.GetAllFaculty();
        for (String faculty: facultyTreeItems) {
            TreeItem<String> facultyTreeNode = new TreeItem<>(faculty);

            ArrayList<String> directionTreeItems = WorkerDepartmentDao.GetAllDirections(faculty);
            for (String direction: directionTreeItems) {
                TreeItem<String> directionTreeNode = new TreeItem<>(direction);
                facultyTreeNode.getChildren().add(directionTreeNode);
            }
            root.getChildren().add(facultyTreeNode);
        }
    }


    // Обработка нажатия клавиши мышки для определения направления
    @FXML
    public void clickMouse(MouseEvent mouseEvent) {

        try {
            TreeItem<String> item = treev_navigation.getSelectionModel().getSelectedItem();
            if (item.getParent() != null && item.getParent().getParent() == null) {
                System.out.println(item.getValue());
                // Открыть для каждого направления все группы
                tp_navigation.getSelectionModel().select(tp_group);
                getInfoAboutDirection(item.getValue());
                id_faculty.setCellValueFactory(new PropertyValueFactory<GroupWorkerDepartment, String>("faculty"));
                id_number.setCellValueFactory(new PropertyValueFactory<GroupWorkerDepartment, String>("number"));
                id_direction.setCellValueFactory(new PropertyValueFactory<GroupWorkerDepartment, String>("direction"));

                tv_group.setItems(groupDepartmentData);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Вывод в таблицу группы
    private void getInfoAboutDirection(String direction) {
        tv_group.getItems().clear();
        ArrayList<GroupWorkerDepartment> data = WorkerDepartmentDao.GetDirectionWithNumber(direction);
        for (GroupWorkerDepartment item: data) {
            groupDepartmentData.add(new GroupWorkerDepartment(item.getFaculty(), item.getDirection(), item.getNumber()));
        }
    }




}
