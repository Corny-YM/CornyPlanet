package com.example.planetcorny;

public class Items {
    private int id;
    private String name;
    private int image;
    private String desc;
    private String descDetail;

    public Items() {
    }

    public Items(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public Items(int id, String name, int image, String desc, String descDetail) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.desc = desc;
        this.descDetail = descDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescDetail() {
        return descDetail;
    }

    public void setDescDetail(String descDetail) {
        this.descDetail = descDetail;
    }
}
