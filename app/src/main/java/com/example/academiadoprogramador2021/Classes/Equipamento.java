package com.example.academiadoprogramador2021.Classes;

public class Equipamento {

    private int id;
    private String nome, preco, numeroSerie, data_fabricacao, fabricante;

    public Equipamento(String nome, String preco, String numeroSerie, String data_fabricacao, String fabricante) {
        this.nome = nome;
        this.preco = preco;
        this.numeroSerie = numeroSerie;
        this.data_fabricacao = data_fabricacao;
        this.fabricante = fabricante;
    }

    public Equipamento() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getData_fabricacao() {
        return data_fabricacao;
    }

    public void setData_fabricacao(String data_fabricacao) {
        this.data_fabricacao = data_fabricacao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

}
