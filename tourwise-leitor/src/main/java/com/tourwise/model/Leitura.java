package com.tourwise.model;

public class Leitura {
    private Integer id_leitura;
    private String nome_arquivo;
    private String data_hora_leitura;

    public Integer getId_leitura() {
        return id_leitura;
    }

    public void setId_leitura(Integer id_leitura) {
        this.id_leitura = id_leitura;
    }

    public String getNome_arquivo() {
        return nome_arquivo;
    }

    public void setNome_arquivo(String nome_arquivo) {
        this.nome_arquivo = nome_arquivo;
    }

    public String getData_hora_leitura() {
        return data_hora_leitura;
    }

    public void setData_hora_leitura(String data_hora_leitura) {
        this.data_hora_leitura = data_hora_leitura;
    }
}
