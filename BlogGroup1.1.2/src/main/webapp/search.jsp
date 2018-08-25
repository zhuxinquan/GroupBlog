<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.get_blog_content.DBUtils" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.get_blog_content.Time" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URLDecoder" %>

<%
    request.setCharacterEncoding("UTF-8");
    int error = 0;
    int tmp = -1;
    if(request.getParameter("page") != null){
        try{
            tmp = Integer.parseInt(request.getParameter("page"));
        }catch (Exception e){
            error++;
        }
        if(tmp <= 0){
            error++;
        }
    }
    if(error != 0){
//        response.sendRedirect("/404.html");
        request.getRequestDispatcher("/404.html").forward(request, response);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Xiyou Linux Group博客系统</title>
    <meta charset="utf-8">
    <meta name="viewport"  content="width=device-width, initial-scale=1"/>
    <link type="text/css" rel="stylesheet" href="/css/search.css">
    <link rel="icon" href="http://blog-image.xiyoulinux.org/xiyou-linux-log.png" type="image/x-icon" />
    <link rel="shortcut icon" href="http://blog-image.xiyoulinux.org/xiyou-linux-log.png">
    <link rel="Bookmark" href="http://blog-image.xiyoulinux.org/xiyou-linux-log.png">
</head>
<body>
<%
    Connection conn = DBUtils.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;
%>
<div class="all">
    <header id="header">
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
    </header>
    <article id="article">
        <div id="laside">
            <aside>
                <div id="lsbox1"><span>相关成员</span></div>
                <%
                    String str = request.getParameter("search");
                    String dstr = null;
                    if(str != null){
                        dstr = URLEncoder.encode(str);
                    }
//                    System.out.println(dstr);
//                    System.out.println(str);
                    if(str != null && str.trim().length() != 0){
                        String sql = "select Id, Name, QQ from T_user where Name like '%" + str + "%' order by Grade desc";
                        ps = conn.prepareStatement(sql);
                        rs = ps.executeQuery();
                    }
                    while(rs != null && rs.next()){
                %>
                <div class="lsbox">
                    <div class="spicture"><img src="http://q.qlogo.cn/headimg_dl?dst_uin=<%= rs.getString("QQ") %>&spec=100&img_type=jpg" height="40px" width="40px"></div>
                    <div class="lsbox3"><a href="index.jsp?uid=<%= rs.getInt("Id") %>"><p><%= rs.getString("Name") %></p></a></div>
                </div>
                <%
                    }
                %>
            </aside>
        </div>
        <section>
            <div id="bbox">
                <%
                    String pageNum = "1";
                    if(request.getParameter("page") != null){
                        pageNum = request.getParameter("page");
                    }
                    if(str != null && str.trim().length() != 0){
                        String sql = "select T_blog.Id, uid, Title, PubDate, ArticleDetail, Summary, Name from T_blog, T_user where T_user.Id = T_blog.uid  and ( category like '%" + str + "%' or title like '%" + str + "%') " + "order by PubDate desc limit " + String.valueOf((Integer.parseInt(pageNum) - 1) * 10) + ", 10";
//                        System.out.println(sql);
                        ps = conn.prepareStatement(sql);
                        rs = ps.executeQuery();
                    }
                    while(rs != null && rs.next()){
                %>
                <div class="vbox">
                    <div>
                        <p class="fsize">
                            <a  target="_blank" href="detail.jsp?id=<%= rs.getInt("Id") %>" title="<%= rs.getString("Title") %>"><%= rs.getString("Title") %></a>
                        </p>
                    </div>
                    <div style="text-indent:3em;">
                        <span class="node">
                            <%
                                if(rs.getString("Summary") == null){
                                    String Summary = rs.getString("ArticleDetail");
                                    int len = Summary.length();
                                    if(len > 300){
                                        Summary = Summary.substring(0, 300);
                                    }else{
                                        Summary = Summary.substring(0, len);
                                    }
                            %>
                            <![CDATA[<%= Summary %>]]>
                            <%
                                }else{
                            %>
                            <%= rs.getString("Summary") %>
                            <%
                                }
                            %>
                                }
                            %>
                        </span>
                        <span>
                            <a href="detail.jsp?id=<%= rs.getInt("Id") %>" rel="nofollow" class="more-link">继续阅读 >></a>
                        </span>
                    </div>
                    <br/>
                    <br/>
                    <div>
                        <a href="index.jsp?uid=<%= rs.getInt("uid") %>" class="afont">
                            <%= rs.getString("Name") %>
                        </a>
                        <span class="time">
                            <%= Time.formatTime(new Date(rs.getLong("PubDate"))) %>
                        </span>
                    </div>
                </div>
                <%
                    }
                %>
            </div>
        </section>
        <div id="pagenum" class="pagenum">
            <%
                if(str != null && str.trim().length() != 0){
                    String sql = "select count(T_blog.Id) num from T_blog, T_user where T_user.Id = T_blog.uid  and (category like '%" + str + "%' or title like '%" + str + "%') ";
//                    System.out.println(sql);
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                }
                int count = 0;
                int t = 0;
                int list = Integer.parseInt(pageNum);
                if( rs != null && rs.next() ){
                    count = rs.getInt("num")/10;
                    if(rs.getInt("num")%10 != 0){
                        count++;
                    }
                }

                if(list != 1){
            %>
            <a href="search.jsp?search=<%= dstr %>&page=1">首页</a>
            <a href="search.jsp?search=<%= dstr %>&page=<%= list - 1 %>">上一页</a>
            <%
                }
                if(list >= 6){
            %>
            <a href="search.jsp?search=<%= dstr %>&page=<%= (list / 5 - 1) * 5 + 1 %>">[…]</a>
            <%
                }
                for(int i = (list - 1) / 5 * 5 + 1; i <= (list - 1) / 5 * 5 + 5 && i <= count; i++){
                    if(i == list){
            %>
            <strong style="color:blue;"><%= i %></strong>
            <%
            }else{
            %>
            <a href="search.jsp?search=<%= dstr %>&page=<%= i %>"><%= i %></a>
            <%
                    }
                }
                if((list - 1) / 5 * 5 + 1 != (count - 1) / 5 * 5 + 1){
            %>
            <a href="search.jsp?search=<%= dstr %>&page=<%= (list + 4) / 5 * 5 + 1 %>">[…]</a>
            <%
                }
                if(list != count && count != 0){
            %>
            <a href="search.jsp?search=<%= dstr %>&page=<%= list + 1 %>">下一页</a>
            <a href="search.jsp?search=<%= dstr %>&page=<%= count %>">尾页</a>
            <%
                }
            %>
        </div>

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
<%
    DBUtils.close(rs, ps, conn);
%>
</body>
</html>