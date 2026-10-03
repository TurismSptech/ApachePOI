package com.tourwise;

import java.time.LocalDate;

public class Hospedagem {
    private String cnpj;
    private String nomeFantasia;
    private String emailComercial;
    private String website;
    private String porte;
    private String tipoHospedagem;
    private LocalDate dtAbertura;
    private Integer uniHabit;
    private Integer leitos;
    private Integer uniHabitAcess;
    private Integer leitosAcess;
    private String endereco;
    private String municipio;
    private String uf;
    private String idiomas;
    private String cnaes;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEmailComercial() {
        return emailComercial;
    }

    public void setEmailComercial(String emailComercial) {
        this.emailComercial = emailComercial;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public String getTipoHospedagem() {
        return tipoHospedagem;
    }

    public void setTipoHospedagem(String tipoHospedagem) {
        this.tipoHospedagem = tipoHospedagem;
    }

    public LocalDate getDtAbertura() {
        return dtAbertura;
    }

    public void setDtAbertura(LocalDate dtAbertura) {
        this.dtAbertura = dtAbertura;
    }

    public Integer getUniHabit() {
        return uniHabit;
    }

    public void setUniHabit(Integer uniHabit) {
        this.uniHabit = uniHabit;
    }

    public Integer getLeitos() {
        return leitos;
    }

    public void setLeitos(Integer leitos) {
        this.leitos = leitos;
    }

    public Integer getUniHabitAcess() {
        return uniHabitAcess;
    }

    public void setUniHabitAcess(Integer uniHabitAcess) {
        this.uniHabitAcess = uniHabitAcess;
    }

    public Integer getLeitosAcess() {
        return leitosAcess;
    }

    public void setLeitosAcess(Integer leitosAcess) {
        this.leitosAcess = leitosAcess;
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

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public String getCnaes() {
        return cnaes;
    }

    public void setCnaes(String cnaes) {
        this.cnaes = cnaes;
    }

    @Override
    public String toString() {
        return nomeFantasia + " - " + cnpj + " - " + municipio + "-" + uf
                + " - " + tipoHospedagem + " - leitos: " + leitos;
    }
}
