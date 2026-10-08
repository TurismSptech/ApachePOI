package com.tourwise.model;

public class Localizacao {
    private Integer id;
    private String endereco;
    private String municipio;
    private String uf;

    public Localizacao(){

    }

    public Localizacao(Integer id, String endereco, String municipio, String uf) {
        this.id = id;
        this.endereco = endereco;
        this.municipio = municipio;
        this.uf = uf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return "Localizacao{" +
                "id=" + id +
                ", endereco='" + endereco + '\'' +
                ", municipio='" + municipio + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }
}
