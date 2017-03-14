package com.shanawaz.flink.flink.model;


public class BlogDetails extends  ErrorManger{

    private String blog_id;
    private String title;
    private String description;
    private String writtenby;
    private String date_blog;
    private String like_blog;
    private String dislike_blog;
    private String brief;


    public String getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(String blog_id) {
        this.blog_id = blog_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWrittenby() {
        return writtenby;
    }

    public void setWrittenby(String writtenby) {
        this.writtenby = writtenby;
    }

    public String getDate_blog() {
        return date_blog;
    }

    public void setDate_blog(String date_blog) {
        this.date_blog = date_blog;
    }

    public String getLike_blog() {
        return like_blog;
    }

    public void setLike_blog(String like_blog) {
        this.like_blog = like_blog;
    }

    public String getDislike_blog() {
        return dislike_blog;
    }

    public void setDislike_blog(String dislike_blog) {
        this.dislike_blog = dislike_blog;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
