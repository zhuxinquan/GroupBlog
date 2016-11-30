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
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
                    FileOutputStream fos = new FileOutputStream("./blog.log-" + dateFormat.format(new Date()), true);
                    BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
                    PrintStream ps = new PrintStream(bos, true);
                    System.setOut(ps);
                    System.out.println(new Date());
                    Collection<User> users = GetGroupUsers.getGroupusers();
                    Collection<Tag> tags = Tag.getAllTags();
                    List blogContentInfos = new LinkedList<BlogContentInfo>();
                    GetContentInfo getContentInfo = null;
                    for (User u: users) {
                        if(u.getFlag() == 0){
                            continue;
                        }
                        for(Tag t : tags){
                            if(t.getBlogType().equals(u.getBlogType())){
                                getContentInfo = new GetContentInfo(u, t);
                                blogContentInfos.addAll(getContentInfo.getContentInfo());
                                break;
                            }
                        }
                        System.out.println(u.getId() + "/" + users.size());
                        BlogContentCrud.storeBlogContentCollection(blogContentInfos, u);
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
        timer.schedule(task, date, 1000 * 60 * 60 * 12);
    }
}
