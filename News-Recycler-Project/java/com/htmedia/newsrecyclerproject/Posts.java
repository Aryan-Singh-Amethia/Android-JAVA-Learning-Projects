package com.htmedia.newsrecyclerproject;

public class Posts {

    String title,body;

    public Posts(String title, String body) {
        this.title = title;
        this.body = body;
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

    @Override
    public String toString() {
        return "Posts{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
