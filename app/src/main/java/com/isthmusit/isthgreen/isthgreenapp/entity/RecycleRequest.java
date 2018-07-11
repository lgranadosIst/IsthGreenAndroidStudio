package com.isthmusit.isthgreen.isthgreenapp.entity;

public class RecycleRequest {

    private Long id;

    private String date;

    private Long idUser;

    private String image;

    private String cant;

    private String type;

    private String total;

    public RecycleRequest(Long id, String date, Long idUser, String image, String cant, String type, String total) {
        this.id = id;
        this.date = date;
        this.idUser = idUser;
        this.image = image;
        this.cant = cant;
        this.type = type;
        this.total = total;
    }

    public RecycleRequest() {
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getImage() {
        return image;
    }

    public String getCant() {
        return cant;
    }

    public String getType() {
        return type;
    }

    public String getTotal() {
        return total;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
