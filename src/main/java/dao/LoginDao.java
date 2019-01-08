package dao;

import entity.CurrentUser;
import entity.UserPost;
import utils.DBHelper;
import utils.Queries;

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

    public static CurrentUser getCurrentUser(String login, String password, UserPost userPost) {
        if (userPost == UserPost.WORKER) {
            CurrentUser currentUser = getInfoAboutCurrentUser(login, password);
            return currentUser;
        }
        if (userPost == UserPost.TEACHER) {
            CurrentUser currentUser = getInfoAboutCurrentUser(login, password);
            return currentUser;
        }
        return null;
    }

    private static CurrentUser getInfoAboutCurrentUser(String login, String password) {
        ResultSet res = DBHelper.ExecuteQuery(Queries.getCurrentWorker(login, password));
        try {
            while (res.next()) {
                CurrentUser currentUser = new CurrentUser(res.getInt("id"), res.getString("name"), res.getString("surname"));
                return currentUser;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
