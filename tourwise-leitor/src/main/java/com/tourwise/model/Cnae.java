package com.tourwise.model;

public class Cnae {
    private Integer id;
    private String codigo;

    public Cnae(){

    }

    public Cnae(Integer id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Cnae{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}
