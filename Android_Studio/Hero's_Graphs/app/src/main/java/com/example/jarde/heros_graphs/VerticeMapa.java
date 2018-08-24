package com.example.jarde.heros_graphs;

import java.util.Random;
import java.util.ArrayList;

public class VerticeMapa {
    private String nome;
    private String cor;
    public boolean entrada;
    public boolean saida;
    public Vertice v;

    public VerticeMapa(){ }

    public VerticeMapa(String nome){
        this.nome=nome;
        this.saida=false;
        this.entrada=false;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String toString(){
        return this.nome;
    }


}
