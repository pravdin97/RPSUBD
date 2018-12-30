package sample;

import entity.UserPost;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static dao.LoginDao.CheckLogin;
import static dao.LoginDao.GetFIO;

public class Login {
    UserPost post;
    //region Def
    @FXML
    Label lb_info;

    @FXML
    TextField tf_login;

    @FXML
    PasswordField tf_pass;

    @FXML
    Label lb_studentLogin;

    @FXML
    RadioButton rb_teacher;

    @FXML
    RadioButton rb_worker;

    @FXML
    ToggleGroup tgroup;
    //endregion


    @FXML
    public void bt_tryLogin_click() throws Exception {
        if (((RadioButton) tgroup.getSelectedToggle()).getText().contains("отник")) {
            if (CheckLogin(tf_login.getText(), tf_pass.getText(), UserPost.WORKER)) {
                String fio = GetFIO(tf_login.getText(), tf_pass.getText(), UserPost.WORKER);
                post = UserPost.WORKER;
                ShowWindow(FXMLLoader.load(getClass().getResource("/tolerance.fxml")), fio + ": Разрешение");
                ((Stage) lb_info.getScene().getWindow()).close();
                return;
            } else {
                lb_info.setText("Вы неверные данные для входа");
                return;
            }
        }
        if (((RadioButton) tgroup.getSelectedToggle()).getText().contains("репод"))
            if (CheckLogin(tf_login.getText(), tf_pass.getText(), UserPost.TEACHER)) {
                String fio = GetFIO(tf_login.getText(), tf_pass.getText(), UserPost.TEACHER);
                post = UserPost.TEACHER;
                ShowWindow(FXMLLoader.load(getClass().getResource("/consolidated_statement.fxml")), fio + ": Сводная ведомость");
                ((Stage) lb_info.getScene().getWindow()).close();
                return;
            } else {
                lb_info.setText("Вы неверные данные для входа");
                return;
            }
    }

    @FXML
    public void lb_studentLogin_click() {
        post = UserPost.STUDENT;
        ((Stage) lb_info.getScene().getWindow()).close();
    }

    void ShowWindow(Parent root, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
