package com.example.famousblog.models;

public class Post {
    private int id;
    private String title;
    private String body;
    private String date;
    private User postedBy;

    public Post(String title, String body, String date, User postedBy) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.postedBy = postedBy;
    }

    public Post() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", date='" + date + '\'' +
                ", postedBy=" + postedBy +
                '}';
    }
}
