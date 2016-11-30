package com.control;

import com.get_blog_content.DBUtils;

import javax.servlet.Filter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhuxinquan on 16-9-5.
 */
public class AntiSqlInjectionfilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)servletRequest;
        HttpServletResponse res= (HttpServletResponse) servletResponse;
        //获得所有请求参数名
        Enumeration params = req.getParameterNames();
        String sql = "";
        while (params.hasMoreElements()) {
            //得到参数名
            String name = params.nextElement().toString();
            //System.out.println("name===========================" + name + "--");
            //得到参数对应值
            String[] value = req.getParameterValues(name);
            for (int i = 0; i < value.length; i++) {
                sql = sql + value[i];
            }
        }
//        System.out.println("============================SQL"+sql);
        //有sql关键字，跳转到error.html
        if (sqlValidate(sql)) {
            throw new IOException("您发送请求中的参数中含有非法字符");
            //String ip = req.getRemoteAddr();
        } else {
            Connection conn = DBUtils.getConnection();
            Statement statement = null;
            ResultSet rs = null;
            String uid = req.getParameter("uid");
            if(uid != null){
//                System.out.println("uid != null");
                try {
                    statement = conn.createStatement();
                    rs = statement.executeQuery("select count(*) from T_blog, T_user where T_user.Id = T_blog.uid and uid = " + uid);
                    if(rs.next()){
                        if(rs.getInt(1) == 0){
                            DBUtils.close(rs, statement, conn);
                            res.sendRedirect("/null.html");
                        }else {
                            filterChain.doFilter(req,  res);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    DBUtils.close(rs, statement, conn);
                }
            }else {
                filterChain.doFilter(req,  res);
            }
        }
    }

    //效验
    protected static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                "chr|mid|master|truncate|char|declare|or|;|-|--|like|%|#";//过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
                return true;
            }
        }
        return false;
    }

    public void destroy() {

    }
}
