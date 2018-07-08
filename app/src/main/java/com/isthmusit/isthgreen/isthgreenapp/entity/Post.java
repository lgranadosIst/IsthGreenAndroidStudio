package com.isthmusit.isthgreen.isthgreenapp.entity;

public class Post {

    private Long id;

    private String name;

    private String description;

    private String image;

    private Long idUser;

    private String date;

    public Post() {
    }

    public Post(Long id, String name, String description, String image, Long idUser, String date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.idUser = idUser;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
