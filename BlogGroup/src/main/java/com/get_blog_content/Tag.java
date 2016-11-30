package com.get_blog_content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhuxinquan on 16-4-25.
 */
public class Tag {
    private String BlogType;
    private String Rss;
    private String Item;
    private String BlogArticleLink;
    private String Title;
    private String Published;
    private String Author;
    private String Content;
    private String Category;
    private String Summary;

    public static Collection<Tag> getAllTags(){
        List<Tag> tags = new LinkedList<Tag>();
        Tag t = null;
        Connection conn = DBUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from T_tags";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                t = new Tag();
                t.setBlogType(rs.getString("BlogType"));
                t.setRss(rs.getString("Rss"));
                t.setItem(rs.getString("Item"));
                t.setBlogArticleLink(rs.getString("BlogArticleLink"));
                t.setTitle(rs.getString("Title"));
                t.setPublished(rs.getString("Published"));
                t.setAuthor(rs.getString("Author"));
                t.setContent(rs.getString("Content"));
                t.setCategory(rs.getString("Category"));
                t.setSummary(rs.getString("Summary"));
                tags.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(rs, ps, conn);
        }
        return tags;
    }

    /**
     * 通过类型获取单个tag标签
     * @param blogType 博客类型
     * @return 博客标签对象
     */
    public static Tag getTag(String blogType){
        Tag tag = new Tag();
        Connection conn = DBUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from T_tags where BlogType = '" + blogType + "'";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                tag.setBlogType(rs.getString("BlogType"));
                tag.setRss(rs.getString("Rss"));
                tag.setItem(rs.getString("Item"));
                tag.setBlogArticleLink(rs.getString("BlogArticleLink"));
                tag.setTitle(rs.getString("Title"));
                tag.setPublished(rs.getString("Published"));
                tag.setAuthor(rs.getString("Author"));
                tag.setContent(rs.getString("Content"));
                tag.setCategory(rs.getString("Category"));
                tag.setSummary(rs.getString("Summary"));
            }else {
                DBUtils.close(rs, ps, conn);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.close(rs, ps, conn);
        }
        return tag;
    }

    public String getBlogType() {
        return BlogType;
    }

    public void setBlogType(String blogType) {
        BlogType = blogType;
    }

    public String getRss() {
        return Rss;
    }

    public void setRss(String rss) {
        Rss = rss;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getBlogArticleLink() {
        return BlogArticleLink;
    }

    public void setBlogArticleLink(String blogArticleLink) {
        BlogArticleLink = blogArticleLink;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPublished() {
        return Published;
    }

    public void setPublished(String published) {
        Published = published;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "BlogType='" + BlogType + '\'' +
                ", Rss='" + Rss + '\'' +
                ", Item='" + Item + '\'' +
                ", BlogArticleLink='" + BlogArticleLink + '\'' +
                ", Title='" + Title + '\'' +
                ", Published='" + Published + '\'' +
                ", Author='" + Author + '\'' +
                ", Content='" + Content + '\'' +
                ", Category='" + Category + '\'' +
                ", Summary='" + Summary + '\'' +
                '}';
    }
}
