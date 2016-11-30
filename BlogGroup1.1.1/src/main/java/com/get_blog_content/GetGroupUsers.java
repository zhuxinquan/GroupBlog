package com.get_blog_content;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by zhuxinquan on 16-7-26.
 */
public class GetGroupUsers {
    public  static Collection<User> getGroupusers(){
        java.sql.Statement stml = null;
        Connection conn = null;
        ResultSet rs = null;
        Collection<User> collection = new ArrayList<User>();
        conn = DBUtils.getConnection();

        try{
            stml = conn.createStatement();
            String sql = "select * from T_user";
            rs = stml.executeQuery(sql);

            User user = null;
            while(rs.next()){
                user = new User();
                user.setId(rs.getInt("Id"));
                user.setName(rs.getString("Name"));
                user.setBlogAddress(rs.getString("BlogAddress"));
                user.setBlogType(rs.getString("BlogType"));
                user.setGrade(rs.getString("Grade"));
                user.setUpdateTime(rs.getLong("UpdateTime"));
                user.setFlag(rs.getInt("flag"));
                collection.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(rs, stml, conn);

        }
        return collection;
    }
}
