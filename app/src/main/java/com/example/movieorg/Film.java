package com.example.movieorg;

import java.io.Serializable;

public class Film implements Serializable {


    String id, name, status, ganre, link, description;

    int raiting;

    public Film(){}

    public Film(String id, String name, String status, String ganre, int raiting, String link, String description) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.ganre = ganre;
        this.raiting = raiting;
        this.link = link;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGanre() {
        return ganre;
    }

    public void setGanre(String ganre) {
        this.ganre = ganre;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRaiting() {
        return raiting;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }
}
