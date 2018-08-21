package com.example.jardel.grafostudio;

import android.content.Context;
import android.graphics.Color;

public class Vertice extends android.support.v7.widget.AppCompatButton{
    private String nome;
    private int grau;
    private Color cor;
    private boolean visitado;
    private boolean selecionado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getGrau() {
        return grau;
    }

    public void setGrau(int grau) {
        this.grau = grau;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public Vertice[] getVerticesAdjacentes() {
        return verticesAdjacentes;
    }

    public void setVerticesAdjacentes(Vertice[] verticesAdjacentes) {
        this.verticesAdjacentes = verticesAdjacentes;
    }

    private Vertice[] verticesAdjacentes;

    public Vertice(Context context) {
        super(context);
    }
}
