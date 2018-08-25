<%--
  Created by IntelliJ IDEA.
  User: zhuxinquan
  Date: 16-8-4
  Time: 上午9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.get_blog_content.DBUtils" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.get_blog_content.Time" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Xiyou Linux Group博客系统</title>
    <meta charset="utf-8">
    <meta name="viewport"  content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="icon" href="http://blog-image.xiyoulinux.org/xiyou-linux-log.png" type="image/x-icon" />
    <link rel="shortcut icon" href="http://blog-image.xiyoulinux.org/xiyou-linux-log.png">
    <link rel="Bookmark" href="http://blog-image.xiyoulinux.org/xiyou-linux-log.png">
</head>

<body>
<div id="all">

    <header id="header">
        <hgroup>
            <div id="nave">
                <div id="nave1">
                    <div id="pictures">
                        <a href="index.jsp"><img src="http://blog-image.xiyoulinux.org/xiyou-linux-log.png" height="100px" width="100px"></a>
                    </div>
						<span id="nsize">
							<a href="index.jsp"><h2>Xiyou Linux Group群博</h2></a>
							<a href="index.jsp"><h4>free open share</h4></a>
						</span>
                </div>
                <div id="seart">
                    <table>
                        <tbody>
                        <tr>
                            <td>
                                <a href="index.jsp"><span class="tlb">返回首页</span></a>
                                <span>|</span>
                                <a href="https://www.xiyoulinux.org" target="_blank"><span class="tlb">官网链接</span></a>
                                <span>|</span>
                                <a href="http://cs.xiyoulinux.org" target="_blank"><span class="tlb">成员登陆</span></a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <form action="search.jsp" method="get">
                        <label>
                            <input type="text" name="search"></label>
                        <button class="btn" type="submit">搜索</button>
                    </form>
                </div>
            </div>
        </hgroup>
        <nav></nav>
    </header>
    <article id="article">
        <div id="laside"></div>
        <%
            request.setCharacterEncoding("UTF-8");
            String id = request.getParameter("id");


            Statement stml = null;
            Connection conn = null;
            ResultSet rs = null;

            String articledetail = "";
            String title = "";
            String author = "";
            Long pubdate = 0L;
            int uid = 0;


            try {
                conn = DBUtils.getConnection();
                stml = conn.createStatement();
                String sql = "select  uid, Name, Title, Author, PubDate, ArticleDetail from T_blog, T_user where T_blog.uid = T_user.Id and T_blog.id="+id+" " ;
//                System.out.println(sql);
                rs = stml.executeQuery(sql);
                if(rs.next()){
                    uid = Integer.parseInt(rs.getString("uid"));
                    articledetail = rs.getString("ArticleDetail");
                    title = rs.getString("Title");
                    author = rs.getString("Name");
                    pubdate = rs.getLong("PubDate");
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DBUtils.close(rs,stml,conn);
            }
        %>

        <section>
            <div class="vbox">
                <div>
                    <p class="fsize">
                        <a  target="_self" href="#" title="<%= title %>"><%= title %></a>
                    </p>
                </div>
                <div id="mbox">
                    <a href="index.jsp?uid=<%= uid %>" class="afont"><%= author %></a>
                    <span class="time" style="color:#996600; margin-left:40px;"><%= Time.formatTime(new Date(pubdate)) %></span>
                </div>
                <div style="text-indent:3em;">
                        <span class="node">
                          <%= articledetail %>
                        </span>
                </div>
            </div>
        </section>

    </article>

    <footer>
        <div id="foot">
            <p>
                <span>Xiyou Linux Group群博</span>
            </p>
            <p>
                <span>free open share</span>
            </p>
            <p>
                <span><a href="http://www.xiyoulinux.org">西邮Linux兴趣小组</a> 版权所有 ©
                    <script type="text/javascript">copyright=new Date();
                    update=copyright.getFullYear();
                    document.write(update);
                    </script>
                </span>
            </p>
        </div>
    </footer>
</div>
</body>
</html>
