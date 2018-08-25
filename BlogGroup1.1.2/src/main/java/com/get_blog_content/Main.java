package com.get_blog_content;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhuxinquan on 16-4-14.
 */
public class Main {
    public static void main(String[] args) {
        doWork();
    }

    public static  void doWork(){

        //要执行的任务
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {

                    //为了将程序运行的输出保存成日志文件新建一个文件，并设置标准输出和错误输出
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
                    FileOutputStream fos = new FileOutputStream("./blog-log/blog.log-" + dateFormat.format(new Date()), true);
                    BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
                    PrintStream ps = new PrintStream(bos, true);
//                    System.setOut(ps);
//                    System.setErr(ps);
                    System.out.println(new Date());

                    //取得所有的成员列表
                    Collection<User> users = GetGroupUsers.getGroupusers();

                    //取得所有博客类型使用的标签，Tag类定义了所有要使用到的标签
                    Collection<Tag> tags = Tag.getAllTags();

                    //用来暂时保存抓取返回的博文集合
                    List blogContentInfos = new LinkedList<BlogContentInfo>();
                    GetContentInfo getContentInfo = null;

                    //遍历成员
                    for (User u: users) {
                        //判断要不要进行抓取
                        if(u.getFlag() == 0){
                            continue;
                        }
                        //遍历Tag集合，找到对应博客类型的Tag
                        for(Tag t : tags){
                            if(t.getBlogType().equals(u.getBlogType())){
                                //实例化一个获取博文信息的类，传入成员信息类和标签
                                getContentInfo = new GetContentInfo(u, t);
                                //使用刚才实例化的类获取博文的集合并将返回的集合保存
                                blogContentInfos.addAll(getContentInfo.getContentInfo());
                                break;
                            }
                        }
                        System.out.println(u.getId() + "/" + users.size());
                        //存储该用户的博文集合
                        BlogContentCrud.storeBlogContentCollection(blogContentInfos, u);
                        //清空博文集合
                        blogContentInfos.clear();
                    }
                    ps.close();
                    bos.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(year,month,day, 6, 0, 0);
        Date date = calendar.getTime();
        Timer timer = new Timer();

        //新建一个定时任务，每隔6小时执行一次task
        timer.schedule(task, date, 1000 * 60 * 60 * 6);
    }
}
