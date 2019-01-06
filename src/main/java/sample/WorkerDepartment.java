package sample;

import dao.StudentDao;
import dao.WorkerDepartmentDao;
import entity.GroupWorkerDepartment;
import entity.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WorkerDepartment implements Initializable {


    // Таблица "Группы"
    @FXML
    private Tab tp_group;
    @FXML
    private TableView<GroupWorkerDepartment> tv_group;
    @FXML
    private TableColumn<GroupWorkerDepartment, String> id_faculty;
    @FXML
    private TableColumn<GroupWorkerDepartment, String> id_direction;
    @FXML
    private TableColumn<GroupWorkerDepartment, String> id_number;
    @FXML
    private Button bt_add_group;
    @FXML
    private Button bt_delete_group;

    private ObservableList<GroupWorkerDepartment> groupDepartmentData = FXCollections.observableArrayList();


    // Таблица "Студенты"
    @FXML
    private Tab tp_student;
    @FXML
    private TableView<Student> tv_student;
    @FXML
    private TableColumn<Student, String> id_name;
    @FXML
    private TableColumn<Student, String> id_surname;
    @FXML
    private TableColumn<Student, String> id_midname;
    @FXML
    private Button btn_add_student;
    @FXML
    private Button btn_delete_student;

    private ObservableList<Student> studentData = FXCollections.observableArrayList();

    // Таблица "Ведомости"
    @FXML
    private Tab tp_record;



    // Навигация по табам
    @FXML
    private TabPane tp_navigation;

    // Боковая древовидная навигация
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
                setTypeAndValueForGroup();
                tv_group.setItems(groupDepartmentData);

                getInfoAboutAllStudentsFromFaculty(item.getValue());
                setTypeAndValueForStudent();
                tv_student.setItems(studentData);
            } else {
                System.out.println(item.getValue());
                getInfoAboutAllStudentFromDirection(item.getValue());
                setTypeAndValueForStudent();
                tv_student.setItems(studentData);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Устанавливаем тип и значение которое должно хранится в колонке таблицы "Группа"
    private void setTypeAndValueForGroup() {
        id_faculty.setCellValueFactory(new PropertyValueFactory<GroupWorkerDepartment, String>("faculty"));
        id_number.setCellValueFactory(new PropertyValueFactory<GroupWorkerDepartment, String>("number"));
        id_direction.setCellValueFactory(new PropertyValueFactory<GroupWorkerDepartment, String>("direction"));
    }

    // Устанавливаем тип и значение которое должно хранится в колонке таблицы "Студент"
    private void setTypeAndValueForStudent() {
        id_name.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        id_surname.setCellValueFactory(new PropertyValueFactory<Student, String>("surname"));
        id_midname.setCellValueFactory(new PropertyValueFactory<Student, String>("midname"));

    }

    // Вывод в таблицу "Группы"
    private void getInfoAboutDirection(String direction) {
        tv_group.getItems().clear();
        ArrayList<GroupWorkerDepartment> data = WorkerDepartmentDao.GetDirectionWithNumber(direction);
        for (GroupWorkerDepartment item: data) {
            groupDepartmentData.add(new GroupWorkerDepartment(item.getFaculty(), item.getDirection(), item.getNumber()));
        }
    }

    // Вывод в таблицу "Студенты" для всего факультета
    private void getInfoAboutAllStudentsFromFaculty(String faculty) throws SQLException {
        tv_student.getItems().clear();
        ArrayList<Student> data = StudentDao.GetAllStudentsFromFacultyForTable(faculty);
        for (Student item: data) {
            studentData.add(new Student(item.getSurname(), item.getName(), item.getMidname()));
        }
    }


    // Вывод в таблицу всех студентов из направления
    private  void getInfoAboutAllStudentFromDirection(String direction) throws SQLException {
        tv_student.getItems().clear();
        ArrayList<Student> data = StudentDao.GetAllStudentFromDirectionForTable(direction);
        for (Student item: data) {
            studentData.add(new Student(item.getSurname(), item.getName(), item.getMidname()));
        }
    }


}
