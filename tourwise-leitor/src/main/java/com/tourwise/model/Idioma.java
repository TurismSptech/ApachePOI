package com.tourwise.model;

public class Idioma {
    private Integer id;
    private String idioma;
    private String codigo_idioma;

    public Idioma(){

    }

    public Idioma(Integer id, String idioma) {
        this.id = id;
        this.idioma = idioma;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdiomas(String idiomas) {
        this.idioma = idiomas;
    }

    @Override
    public String toString() {
        return "Idioma{" +
                "id=" + id +
                ", idiomas='" + idioma + '\'' +
                '}';
    }
}
