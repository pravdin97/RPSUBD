package dao;

import entity.UserPost;
import utils.DBHelper;

import java.sql.ResultSet;

public class LoginDao {
    public static boolean CheckLogin(String login, String password, UserPost post){
        ResultSet res = null;
        if (post == UserPost.TEACHER)
            res = DBHelper.ExecuteQuery(Queries.CheckLoginTeacher(login, password));
        if (post == UserPost.WORKER)
            res = DBHelper.ExecuteQuery(Queries.CheckLoginWorker(login, password));
        try {
            while (res.next()) {
                return res.getBoolean(1);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static String GetFIO(String login, String password, UserPost post){
        ResultSet res = null;
        if (post == UserPost.TEACHER)
            res = DBHelper.ExecuteQuery(Queries.GetLoginTeacher(login, password));
        if (post == UserPost.WORKER)
            res = DBHelper.ExecuteQuery(Queries.GetLoginWorker(login, password));
        try {
            while (res.next()) {
                return res.getString(2) + " " + res.getString(1);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
