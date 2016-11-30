package com.get_blog_content;

/**
 * 博文内容
 * Created by zhuxinquan on 16-4-14.
 */
public class BlogContentInfo {
    private int id;
    private int uid;
    private String BlogArticleLink;
    private String Title;
    private String Author;
    private Long PubDate;
    private String ArticleDetail;
    private String Summary;
    private String Category;

    public BlogContentInfo(){}

    @Override
    public int hashCode() {
        return BlogArticleLink.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return BlogArticleLink.equals(((BlogContentInfo) obj).BlogArticleLink);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Long getPubDate() {
        return PubDate;
    }

    public void setPubDate(Long pubDate) {
        PubDate = pubDate;
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

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    @Override
    public String toString() {
        return "BlogContentInfo{" +
                "id=" + id +
                ", uid=" + uid +
                ", BlogArticleLink='" + BlogArticleLink + '\'' +
                ", Title='" + Title + '\'' +
                ", Author='" + Author + '\'' +
                ", PubDate=" + PubDate +
                ", ArticleDetail='" + ArticleDetail + '\'' +
                ", Summary='" + Summary + '\'' +
                ", Category='" + Category + '\'' +
                '}';
    }
}
