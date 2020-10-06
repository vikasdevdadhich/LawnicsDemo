package com.example.lawnicsdemo;

public class Data {
    private String id;
    private String name;
    private String type;
    private String genre;


    public Data(   ) {

    }

    public Data( String id, String name, String genre, String type) {
        this.id=id;
        this.name = name;
        this.type = type;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
