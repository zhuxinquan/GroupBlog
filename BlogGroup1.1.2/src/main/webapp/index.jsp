<%@ page import="java.sql.Connection" %>
<%@ page import="com.get_blog_content.DBUtils" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.get_blog_content.Time" %>
<%@ page import="java.util.*" %>
<%@ page import="com.get_blog_content.BlogContentCrud" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.sun.org.apache.xerces.internal.impl.xpath.regex.Match" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>


<%
    int error = 0;
    if(request.getParameter("time") != null){
        String[] yearDay = request.getParameter("time").split(",");
        int day = 0;
        try{
            day = Integer.parseInt(yearDay[1]);
        }catch (Exception e){
            error++;
        }
        if(day > 12 || day < 1){
            error++;
        }
    }
    if(request.getParameter("page") != null && request.getParameter("page").startsWith("-")){
        error++;
    }
    if(error != 0){
        throw new Exception();
//        request.getRequestDispatcher("/404.html").forward(request, response);
    }

%>

<!DOCTYPE html>
<html>
<head>
    <title>Xiyou Linux Group博客系统</title>
    <meta charset="utf-8">
    <meta name="viewport"  content="width=device-width, initial-scale=1"/>
    <script type="text/javascript" src="http://7xs8o3.com1.z0.glb.clouddn.com/tagcloud.js"></script>
    <%--<script type="text/javascript" src="./js/tagcloud.js"></script>--%>
    <script type="text/javascript" src="http://7xs8o3.com1.z0.glb.clouddn.com/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="./css/index.css">
    <link rel="icon" href="http://7xs8o3.com1.z0.glb.clouddn.com/sign1.png" type="image/x-icon" />
    <link rel="shortcut icon" href="http://7xs8o3.com1.z0.glb.clouddn.com/sign1.png">
    <link rel="Bookmark" href="http://7xs8o3.com1.z0.glb.clouddn.com/sign1.png">
    <script type="text/javascript">
        $(document).ready(function() {
            $('.inactive').click(function(){
                if($(this).siblings('ul').css('display')=='none'){
                    $(this).parent('li').siblings('li').removeClass('inactives');
                    $(this).addClass('inactives');
                    $(this).siblings('ul').slideDown(500).children('li');
                    $(this).parents('li').siblings('li').children('ul').css('display','none');

                }else{
                    //控制自身变成+号
                    $(this).removeClass('inactives');
                    //控制自身菜单下子菜单隐藏
                    $(this).siblings('ul').slideUp(200);
                    //控制自身子菜单变成+号
                    $(this).siblings('ul').children('li').children('ul').parent('li').children('a').addClass('inactives');
                    //控制自身菜单下子菜单隐藏
                    $(this).siblings('ul').children('li').children('ul').slideUp(200);

                    //控制同级菜单只保持一个是展开的（-号显示）
                    $(this).siblings('ul').children('li').children('a').removeClass('inactives');
                }
            })
        });
    </script>
</head>
<body>
<%
    Connection conn = DBUtils.getConnection();
    Connection conn1 = DBUtils.getConnection();
    PreparedStatement ps = null;
    PreparedStatement ps1 = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
%>
<div class="all">
    <header id="header">
        <hgroup>
            <div id="nave">
                <div id="nave1">
                    <div id="pictures">
                        <a href="index.jsp"><img src="http://7xs8o3.com1.z0.glb.clouddn.com/sign1.png" height="100px" width="100px"></a>
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
                            <input type="text" name="search">
                        </label>
                        <button class="btn" type="submit">搜索</button>
                    </form>
                </div>
            </div>
        </hgroup>
        <nav></nav>
    </header>
    <article id="article">
        <div id="laside">
            <aside>
                <div class="list">
                    <ul class="yiji">
                        <li>
                            <span style="font-size: 15px; padding-left: 10px; color: #808080; display: block; font-weight: bold; height: 40px; line-height:36px; position: relative">年级分类</span>
                            <%--<a href="" style="font-size: 15px;">年级分类</a>--%>
                        </li>
                        <%
                            String queryGrades = "select DISTINCT Grade from T_user order by Grade";
                            ps = conn.prepareStatement(queryGrades);
                            rs = ps.executeQuery();
                            List<String> grades = new ArrayList<>();
                            while(rs.next()){
                                grades.add(rs.getString("Grade"));
                            }
                            for(String s : grades){
                                if(s == null){
                                    continue;
                                }
                        %>
                        <li>
                            <span class="inactive" style="font-size: 14px; padding-left: 10px; color: #808080; display: block; font-weight: bold; height: 40px; line-height:36px; position: relative"><%= s %>级</span>
                            <ul style="display: none">
                                <%
                                    String queryPeopleByGrade = "select * from T_user where Grade = " + s;
                                    ps = conn.prepareStatement(queryPeopleByGrade);
                                    rs = ps.executeQuery();
                                    while(rs.next()){
                                %>
                                <li class="last">
                                    <img  src="http://q.qlogo.cn/headimg_dl?dst_uin=<%= rs.getString("QQ") %>&spec=100&img_type=jpg" class="picture">
                                    <span><a href="index.jsp?uid=<%= rs.getInt("Id") %>"><%= rs.getString("Name") %></a></span>
                                </li>
                                <%
                                    }
                                %>
                            </ul>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </aside>
        </div>
        <section>
            <div id="bbox">
                <%
                    //                    ArrayList<String> errors = new ArrayList<>();
                    String queryBlog = "select T_blog.Id, uid, Title, PubDate, ArticleDetail, Summary, Name from T_blog, T_user where T_user.Id = T_blog.uid ";
                    String pageNum = "1";
                    String condition = "";
                    String condition1 = "";
                    String uid = request.getParameter("uid");
                    String time = request.getParameter("time");
                    String category = request.getParameter("category");
                    if(uid != null){
                        condition = condition + "uid=" + URLEncoder.encode(uid) + "&";
                        condition1 = condition1 + " uid = " + uid + " ";
                        queryBlog = queryBlog + " and uid = " + uid + " ";
                    }
                    if(time != null){
                        condition = condition + "time=" + URLEncoder.encode(time) + "&";
                        Set<Integer> big = new HashSet<>();
                        big.add(0);
                        big.add(2);
                        big.add(4);
                        big.add(6);
                        big.add(7);
                        big.add(9);
                        big.add(11);
                        int year = Integer.parseInt(time.substring(0, 4));
                        int month = Integer.parseInt(time.substring(5, time.length())) - 1;
                        int days = 0;
                        if(month == 1){
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
                        Long time1 = new Date(year, month, 1, 0, 0, 0).getTime();
                        Long time2 = new Date(year, month, days, 23, 59, 59).getTime();
                        condition1 = condition1 + " PubDate between " + time1 + " and " + time2 + " ";
                        queryBlog = queryBlog + " and PubDate between " + time1 + " and " + time2 + " ";
                    }
                    if(category != null){
                        String s = "'%" + category + "%'";
                        condition = condition + "category=" + category + "&";
                        condition1 = condition1 + " category like " + s + " or title like " + s + " ";
                        queryBlog = queryBlog + " and ( category like " + s + " or title like " + s + ") ";
                    }
                    if(request.getParameter("page") != null){
                        pageNum = request.getParameter("page");
                    }
                    queryBlog = queryBlog + " order by PubDate desc limit " + String.valueOf((Integer.parseInt(pageNum) - 1) * 10) + ", 10 ";
//                    System.out.println(queryBlog);
                    try{
                        ps = conn.prepareStatement(queryBlog);
                        rs = ps.executeQuery();
                    }catch (Exception e){
                        rs = null;
                    }
                    while(rs != null && (rs.next() != false)){
//                        System.out.println(rs.getString("Title"));
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
                                        Summary = Summary.replaceAll("<[^>]+?>", "");
                                        int len = Summary.length();
                                        if(len > 500){
                                            Summary = Summary.substring(0, 500);
                                        }else{
                                            Summary = Summary.substring(0, len);
                                        }
                                %>
                                <%= Summary %>
                                <%
                                }else{
                                %>
                                <%= rs.getString("Summary") %>
                                <%
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
                String sql = null;
                if(condition1.trim().length() == 0){
                    sql = "select count(Id) num from T_blog";
                }else{
//                    System.out.println("condition1:" + condition1);
                    sql = "select count(Id) num from T_blog where " + condition1;
                }
                try{
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                }catch (Exception e){
                    rs = null;
                }

                int count = 0;
                int t = 0;
                int list = 1;
                if(request.getParameter("page") != null){
                    list = Integer.parseInt(request.getParameter("page"));
                }
                if(rs != null && rs.next()){
                    count = rs.getInt("num")/10;
                    if(rs.getInt("num")%10 != 0){
                        count++;
                    }
                }

                if(count < list){
                    throw new Exception();
                }

                if(list != 1){
            %>
            <a href="index.jsp?<%= condition %>page=1">首页</a>
            <a href="index.jsp?<%= condition %>page=<%= list - 1 %>">上一页</a>
            <%
                }
                if(list >= 6){
            %>
            <a href="index.jsp?<%= condition %>page=<%= (list / 5 - 1) * 5 + 1 %>">[…]</a>
            <%
                }
                for(int i = (list - 1) / 5 * 5 + 1; i <= (list - 1) / 5 * 5 + 5 && i <= count; i++){
                    if(i == list){
            %>
            <strong style="color:blue;"><%= i %></strong>
            <%
            }else{
            %>
            <a href="index.jsp?<%= condition %>page=<%= i %>"><%= i %></a>
            <%
                    }
                }
                if((list - 1) / 5 * 5 + 1 != (count - 1) / 5 * 5 + 1){
            %>
            <a href="index.jsp?<%= condition %>page=<%= (list + 4) / 5 * 5 + 1 %>">[…]</a>
            <%
                }
                if(list != count && count != 0){
            %>
            <a href="index.jsp?<%= condition %>page=<%= list + 1 %>">下一页</a>
            <a href="index.jsp?<%= condition %>page=<%= count %>">尾页</a>
            <%
                }
            %>
        </div>
        <div>
            <aside id="raside">
                <div id="tagsList">
                    <%
                        String queryTag = "select category from T_category";
                        ps = conn.prepareStatement(queryTag);
                        rs = ps.executeQuery();
                        while(rs.next()){
                    %>
                    <span>
                        <%
                            String url = "index.jsp?category=" + rs.getString("category");
                        %>
							<a href="<%= url %>"><%= rs.getString("category") %></a>
                    </span>
                    <%
                        }
                    %>
                </div>
                <div id="rlook">
                    <span class="ssize">文章归档</span>
                    <%
                        Date date = new Date();
                        int year = date.getYear() + 1900;
                        int month = date.getMonth() + 1;
                        for(int i = 0; i < 10; i++){
                    %>
                    <p>
                        <a href="index.jsp?time=<%= year %>,<%= month %>"><%= year %>年<%= month %>月(<%= BlogContentCrud.CountTime(year, month, conn1, ps1, rs1) %>)</a>
                    </p>
                    <%
                            month--;
                            if(month == 0){
                                year--;
                                month = 12;
                            }
                        }
                    %>
                </div>
            </aside>
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
    DBUtils.close(rs1, ps1, conn1);
%>
<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan style='display:none;' id='cnzz_stat_icon_1260603825'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s95.cnzz.com/z_stat.php%3Fid%3D1260603825%26show%3Dpic1' type='text/javascript'%3E%3C/script%3E"));</script>
</body>
</html>