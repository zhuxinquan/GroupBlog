package com.get_blog_content;

/**
 * Created by zhuxinquan on 16-7-26.
 */
public class User {
    private int id;
    private String Name;
    private String BlogAddress;
    private String BlogType;
    private String Grade;
    private Long UpdateTime;

    public User() {
    }

    public User(int id, String name, String blogAddress, String blogType, String grade, Long updateTime) {
        this.id = id;
        Name = name;
        BlogAddress = blogAddress;
        BlogType = blogType;
        Grade = grade;
        UpdateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBlogAddress() {
        return BlogAddress;
    }

    public void setBlogAddress(String blogAddress) {
        BlogAddress = blogAddress;
    }

    public String getBlogType() {
        return BlogType;
    }

    public void setBlogType(String blogType) {
        BlogType = blogType;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public Long getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Long updateTime) {
        UpdateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", BlogAddress='" + BlogAddress + '\'' +
                ", BlogType='" + BlogType + '\'' +
                ", Grade='" + Grade + '\'' +
                ", UpdateTime='" + UpdateTime + '\'' +
                '}';
    }
}
