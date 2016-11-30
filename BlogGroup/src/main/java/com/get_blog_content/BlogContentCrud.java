package com.get_blog_content;

import java.sql.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhuxinquan on 16-7-26.
 */
public class BlogContentCrud {

    public static void storeBlogContentCollection(Collection<BlogContentInfo> c, User u){
        int size = c.size();
        int i = 1;
        PreparedStatement ps = null;
        Connection conn = null;
        conn = DBUtils.getConnection();
        PreparedStatement ps1 = null;
        Connection conn1 = null;
        conn1 = DBUtils.getConnection();
        String sql = "insert into T_blog(uid, BlogArticleLink, Title, Author, PubDate, ArticleDetail, Summary, Category) values(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql);
            for(BlogContentInfo blog: c){
                if(blog.getPubDate() < u.getUpdateTime()){
                    continue;
                }
                if(blog.getPubDate() > u.getUpdateTime()){
                    String sqlTime = "update T_user set UpdateTime = " + blog.getPubDate() + " where id = " + blog.getUid();
                    ps1 = conn1.prepareStatement(sqlTime);
                    ps1.executeUpdate();
                }
                ps.setInt(1, blog.getUid());
                ps.setString(2, blog.getBlogArticleLink());
                ps.setString(3, blog.getTitle());
                ps.setString(4, blog.getAuthor());
                ps.setLong(5, blog.getPubDate());
                ps.setString(6, blog.getArticleDetail());
                ps.setString(7, blog.getSummary());
                ps.setString(8, blog.getCategory());
                ps.executeUpdate();
                System.out.println("store : " + i++ + "/" + size);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(null, ps, conn);
            DBUtils.close(null, ps1, conn1);
        }
    }

    public static int CountTime(int year, int month, Connection conn, PreparedStatement ps, ResultSet rs){
        Collection<Integer> big = new HashSet<Integer>();
        big.add(1);
        big.add(3);
        big.add(5);
        big.add(7);
        big.add(8);
        big.add(10);
        big.add(12);
        int days = 0;
        int ret = 0;
        if(month == 2){
            if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
                days = 29;
            }else{
                days = 28;
            }
        }else if(big.contains(month)){
            days = 31;
        }else{
            days = 30;
        }
        Long time1 = new Date(year, month - 1, 1, 0, 0, 0).getTime();
        Long time2 = new Date(year, month - 1, days, 23, 59, 59).getTime();
        String sql = "select count(Id) num from T_blog where PubDate between " + time1 + " and " + time2 + " ";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                ret = rs.getInt("num");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static void insert(BlogContentInfo blog) {
        PreparedStatement ps = null;
        Connection conn = null;

        conn = DBUtils.getConnection();
        try {
            String sql = "insert into T_blog(uid, BlogArticleLink, Title, Author, PubDate, ArticleDetail, Summary, Category) values(?, ?, ?, ?, ?, ?, ?, ?))";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, blog.getUid());
            ps.setString(2, blog.getBlogArticleLink());
            ps.setString(3, blog.getTitle());
            ps.setString(4, blog.getAuthor());
            ps.setLong(5, blog.getPubDate());
            ps.setString(6, blog.getArticleDetail());
            ps.setString(7, blog.getSummary());
            ps.setString(8, blog.getCategory());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(null, ps, conn);
        }
    }
}
