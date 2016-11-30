package com.view;

/**
 * Created by zhuxinquan on 16-11-29.
 */
public class Blog {
    private String Title;
    private String Author;
    private Long PubDate;
    private String ArticleDetail;
    private String Summary;

    @Override
    public String toString() {
        return "Blog{" +
                "Title='" + Title + '\'' +
                ", Author='" + Author + '\'' +
                ", PubDate=" + PubDate +
                ", ArticleDetail='" + ArticleDetail + '\'' +
                ", Summary='" + Summary + '\'' +
                '}';
    }

    public Blog(String title, String author, Long pubDate, String articleDetail, String summary) {
        Title = title;
        Author = author;
        PubDate = pubDate;
        ArticleDetail = articleDetail;
        Summary = summary;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public Long getPubDate() {
        return PubDate;
    }

    public void setPubDate(Long pubDate) {
        PubDate = pubDate;
    }

    public String getArticleDetail() {
        return ArticleDetail;
    }

    public void setArticleDetail(String articleDetail) {
        ArticleDetail = articleDetail;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }
}
