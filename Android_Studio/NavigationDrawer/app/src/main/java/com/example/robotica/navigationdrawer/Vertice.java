package com.example.robotica.navigationdrawer;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;

public class Vertice extends AppCompatButton implements Grafo {
    private String nome;
    private boolean visitado;
    private int tamanhoVertice;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public int getTamanhoVertice() {
        return tamanhoVertice;
    }

    public void setTamanhoVertice(int tamanhoVertice) {
        this.tamanhoVertice = tamanhoVertice;
    }

    public int getMetadeTamanhoVertice() {
        return this.tamanhoVertice / 2;
    }


    public Vertice(Context context) {
        super(context);

        tamanhoVertice = R.dimen.tamanho_vertice;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
