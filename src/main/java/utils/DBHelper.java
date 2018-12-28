package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHelper {
    static Connection connection = null;
    static Statement state = null;

    private class RemoveDB {
        static final String DB_URL = "jdbc:postgresql://examserverdb.postgres.database.azure.com:5432/ExamDB";
        static final String USER = "main_admin@examserverdb";
        static final String PASS = "exampassword.01";
    }

    private class LocalDB {
        static final String DB_URL = "jdbc:postgresql://localhost:5432/exam";
        static final String USER = "postgres";
        static final String PASS = "1111";
    }

    public static void Connect() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(RemoveDB.DB_URL, RemoveDB.USER, RemoveDB.PASS);
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
