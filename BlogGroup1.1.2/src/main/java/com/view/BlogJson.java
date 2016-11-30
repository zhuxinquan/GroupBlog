package com.view;

import com.get_blog_content.DBUtils;
import org.json.JSONObject;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhuxinquan on 16-11-29.
 */
@WebServlet("/blogjson")
public class BlogJson extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Map<String, String> map = null;
        Map<String, Map<String, String>> mapMap = new HashMap();
        Connection connection = DBUtils.getConnection();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT T_blog.id, Name, BlogArticleLink, Title, Author, PubDate, ArticleDetail from T_blog, T_user where T_blog.uid = T_user.Id order by PubDate desc limit 1, 10");
            Integer i = 0;
            while(resultSet.next()){
                map = new HashMap<String, String>();
                map.put("BlogArticleLink", "http://blog.xiyoulinux.org/detail.jsp?id=" + resultSet.getString("T_blog.id"));
                map.put("Title", resultSet.getString("Title"));
                map.put("Author", resultSet.getString("Name"));
                map.put("PubDate", simpleDateFormat.format(new Date(resultSet.getLong("PubDate"))).toString());
                String string = resultSet.getString("ArticleDetail").replaceAll("<[^>]+?>", "");
                int len = string.length();
                len = len < 500 ? len : 500;
                map.put("ArticleDetail", string.substring(0, len));
                mapMap.put("blog-" + i.toString(), map);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(mapMap);
        out.print(jsonObject.toString());
        out.close();
    }
}
