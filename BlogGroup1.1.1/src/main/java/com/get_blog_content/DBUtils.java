package com.get_blog_content;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * 数据库操作工具类
 * Created by zhuxinquan on 16-3-5.
 */
public class DBUtils {
    public static String URL;
    public static String USERNAME;
    public static String PASSWD;
    public static String Driver;

    //加载配置文件
    private static ResourceBundle rb = ResourceBundle.getBundle("db-config");

    //构造方法私有化，可防止类创建对象
    private DBUtils(){}

    //静态代码块在加载类时只执行一次
    static {
        //读取配置文件信息
        URL = rb.getString("jdbc.URL");
        USERNAME = rb.getString("jdbc.USERNAME");
        PASSWD = rb.getString("jdbc.PASSWD");
        Driver = rb.getString("jdbc.Driver");
        try {
            Class.forName(Driver);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取链接失败");
        }
        return conn;
    }

    /**
     * 关闭数据库链接
     * @param rs：结果集
     * @param stat：statement
     * @param conn：Connection
     */
    public static void close(ResultSet rs, Statement stat, Connection conn) {
        try {
            if(rs != null) rs.close();
            if(stat != null) stat.close();
            if(conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
