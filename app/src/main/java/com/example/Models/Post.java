package com.example.Models;

public class Post {
    private String idPost;
    private String image;
    private String caption;


    public Post(String idPost, String image, String caption) {
        this.idPost = idPost;
        this.image = image;
        this.caption = caption;
    }

    public String getIdPost() {
        return idPost;
    }

    public String getImage() {
        return image;
    }

    public String getCaption() {
        return caption;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
