package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHelper {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/exam";
    static final String USER = "postgres";
    static final String PASS = "1111";
    static Connection connection = null;
    static Statement state = null;

    public static void Connect() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
                state = connection.createStatement();
            } catch (Exception e) {
                Logger.getLogger(DBHelper.class.getName()).log(
                        Level.SEVERE, "Ошибка подключения", e);
            }
        }
    }

    public static ResultSet ExecuteQuery(String query) {
        if (state != null) {
            try {
                return state.executeQuery(query);
            } catch (Exception e) {
                Logger.getLogger(DBHelper.class.getName()).log(
                        Level.SEVERE, "Запрос не был выполнен", e);
            }
        }
        return null;
    }
}
