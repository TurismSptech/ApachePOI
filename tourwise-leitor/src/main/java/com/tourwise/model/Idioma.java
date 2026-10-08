package com.tourwise.model;

public class Idioma {
    private Integer id;
    private String idiomas;

    public Idioma(){

    }

    public Idioma(Integer id, String idiomas) {
        this.id = id;
        this.idiomas = idiomas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    @Override
    public String toString() {
        return "Idioma{" +
                "id=" + id +
                ", idiomas='" + idiomas + '\'' +
                '}';
    }
}
