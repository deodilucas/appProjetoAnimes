package com.example.appprojetoanimes;

public class Anime {

    private String id;
    private String nome;
    private String temp;
    private String genero;
    private String ep;
    private String ano;
    private String material;
    private String status;

    public Anime(){}

    public Anime(String id, String nome, String temp, String ep, String genero, String ano, String material, String status) {
        this.id = id;
        this.nome = nome;
        this.temp = temp;
        this.genero = genero;
        this.ep = ep;
        this.ano = ano;
        this.material = material;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getEp() {
        return ep;
    }

    public String getAno() {
        return ano;
    }

    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}
    public String getGenero(){return genero;}
    public void setGenero(String genero){this.genero = genero;}
    public String getMaterial(){return material;}
    public void setMaterial(String material){this.material = material;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status = status;}
}
