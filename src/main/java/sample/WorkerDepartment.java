package sample;

import com.sun.istack.internal.NotNull;
import dao.CRecordDao;
import dao.ERecordDao;
import dao.StudentDao;
import dao.WorkerDepartmentDao;
import entity.CRecord;
import entity.ERecord;
import entity.GroupWorkerDepartment;
import entity.Student;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

    // TODO: Сделать добавление группы
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

    // TODO: Сделать добавление студента
    @FXML
    private Button btn_add_student;
    @FXML
    private Button btn_delete_student;

    private ObservableList<Student> studentData = FXCollections.observableArrayList();

    // Таблица "Ведомости"
    @FXML
    private Tab tp_record;

    // Сводная
    @FXML
    private Tab tp_crecord;
    @FXML
    private TableView<CRecord> tv_crecord;
    @FXML
    private TableColumn<CRecord, Date> id_data_crecord;
    @FXML
    private TableColumn<CRecord, String> id_group_crecord;
    @FXML
    private TableColumn<CRecord, String> id_worker_crecord;
    @FXML
    private TableColumn<CRecord, Integer> id_semestr_crecord;

    private ObservableList<CRecord> crecordData = FXCollections.observableArrayList();

    // TODO: Сделать добавление сводной ведомости
    @FXML
    private Button bt_add_crecord;
    @FXML
    private Button bt_delete_crecord;

    // TODO: Экзаменационная
    // Сводная
    @FXML
    private Tab tp_erecord;
    @FXML
    private TableView<ERecord> tv_erecord;
    @FXML
    private TableColumn<ERecord, Date> id_data_erecord;
    @FXML
    private TableColumn<ERecord, String> id_group_erecord;
    @FXML
    private TableColumn<ERecord, String> id_worker_erecord;
    @FXML
    private TableColumn<ERecord, String> id_teacher_erecord;
    @FXML
    private TableColumn<ERecord, String> id_subject_erecord;
    @FXML
    private TableColumn<ERecord, Boolean> id_status_erecord;

    private ObservableList<ERecord> erecordData = FXCollections.observableArrayList();

    // TODO: Сделать добавление экзаменационной ведомости ведомости
    @FXML
    private Button bt_add_erecord;
    @FXML
    private Button bt_delete_erecord;



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

        //обработка клика на строку, содержащую данные о сводной ведомости
        tv_crecord.setRowFactory( tv -> {
            TableRow<CRecord> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    CRecord rowData = row.getItem();

                    String group = rowData.getGroup();
                    String semestr = rowData.getSemestr() + " семестр";

                    getConsolidatedStatementContent(group, semestr);
                    System.out.println(rowData);
                }
            });
            return row ;
        });

        //обработка клика на строку, содержащую данные о экзаменационной ведомости
        tv_erecord.setRowFactory( tv -> {
            TableRow<ERecord> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ERecord rowData = row.getItem();

                    String group = rowData.getGroup();
                    String subject = rowData.getSubject();

                    getExamPapertContent(group, subject);
                    System.out.println(rowData);
                }
            });
            return row ;
        });
    }




    // Обработка нажатия клавиши мышки для определения направления
    @FXML
    public void clickMouse(MouseEvent mouseEvent) {

        try {
            TreeItem<String> item = treev_navigation.getSelectionModel().getSelectedItem();
            if (item.getParent() != null && item.getParent().getParent() == null) {
                System.out.println(item.getValue());

                tp_navigation.getSelectionModel().select(tp_record);

                getAllCRecordFromFaculty(item.getValue());
                setTypeAndValueForCRecord();
                tv_crecord.setItems(crecordData);

                getAllERecordFromFaculty(item.getValue());
                setTypeAndValueForERecord();
                tv_erecord.setItems(erecordData);

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
    private void getInfoAboutAllStudentFromDirection(String direction) throws SQLException {
        tv_student.getItems().clear();
        ArrayList<Student> data = StudentDao.GetAllStudentFromDirectionForTable(direction);
        for (Student item: data) {
            studentData.add(new Student(item.getSurname(), item.getName(), item.getMidname()));
        }
    }

    // Вывод в таблицу всех сводных ведомостей для данного факультета
    private void getAllCRecordFromFaculty(String faculty) throws SQLException {
        tv_crecord.getItems().clear();
        ArrayList<CRecord> data = CRecordDao.getAllCRecordForFaculty(faculty);
        for (CRecord item: data) {
            crecordData.add(new CRecord(item.getDate(), item.getGroup(), item.getWorker(), item.getSemestr()));
        }
    }

    // Устанавливаем тип и значение которое должно хранится в колонке таблицы "Сводные ведомости"
    private void setTypeAndValueForCRecord() {
        id_data_crecord.setCellValueFactory(new PropertyValueFactory<CRecord, Date>("date"));
        id_group_crecord.setCellValueFactory(new PropertyValueFactory<CRecord, String>("group"));
        id_worker_crecord.setCellValueFactory(new PropertyValueFactory<CRecord, String>("worker"));
        id_semestr_crecord.setCellValueFactory(new PropertyValueFactory<CRecord, Integer>("semestr"));
    }

    // Вывод в таблицу всех экзаменнационных ведомостей для данного факультета
    private void getAllERecordFromFaculty(String faculty) {
        tv_erecord.getItems().clear();
        ArrayList<ERecord> data = ERecordDao.getAllERecordForFaculty(faculty);
        for (ERecord item: data) {
            erecordData.add(new ERecord(item.getStatus(), item.getDate(), item.getGroup(), item.getWorker(), item.getTeacher(), item.getSubject()));
        }
    }

    // Устанавливаем тип и значение которое должно хранится в колонке таблицы "Сводные ведомости"
    private void setTypeAndValueForERecord() {
        id_data_erecord.setCellValueFactory(new PropertyValueFactory<ERecord, Date>("date"));
        id_group_erecord.setCellValueFactory(new PropertyValueFactory<ERecord, String>("group"));
        id_worker_erecord.setCellValueFactory(new PropertyValueFactory<ERecord, String>("worker"));
        id_teacher_erecord.setCellValueFactory(new PropertyValueFactory<ERecord, String>("teacher"));
        id_subject_erecord.setCellValueFactory(new PropertyValueFactory<ERecord, String>("subject"));
        id_status_erecord.setCellValueFactory(new PropertyValueFactory<ERecord, Boolean>("status"));
    }


    private void getConsolidatedStatementContent(String group, String semestr) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/consolidate2.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConsolidatedStatementContent controller = loader.getController();
        controller.setValues(group, semestr);


        Stage stage = new Stage();
        stage.setTitle("Сводная ведомость");
        stage.setScene(new Scene(root));
        controller.init();
        stage.show();
    }

    @FXML
    public void add_crecord() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/crecord.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Создание сводной ведомости");
        stage.setScene(new Scene(root, 341, 584));
        stage.showAndWait();
    }

    private void getExamPapertContent(String group, String subject) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/exam_paper.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExamPaperContent controller = loader.getController();
        controller.setValues(group, subject);


        Stage stage = new Stage();
        stage.setTitle("Экзаменационная ведомость");
        stage.setScene(new Scene(root));
        controller.init();
        stage.show();
    }
}
