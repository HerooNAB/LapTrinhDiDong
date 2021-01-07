package com.example.Models;

public class Post {
    private String idPost;
    private String image;
    private String caption;
    private String postBy;


    public Post(String idPost, String image, String caption, String postBy) {
        this.idPost = idPost;
        this.image = image;
        this.caption = caption;
        this.postBy = postBy;
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

    public String getPostBy() {
        return postBy;
    }
}
