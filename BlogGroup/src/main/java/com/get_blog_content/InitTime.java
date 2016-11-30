package com.get_blog_content;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by zhuxinquan on 16-8-4.
 */
public class InitTime {
    public static void main(String[] args) {
        Connection conn = DBUtils.getConnection();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            statement.executeUpdate("update T_user set UpdateTime = 0");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(null, statement, conn);
        }
    }
}
