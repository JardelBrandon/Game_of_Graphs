package com.example.robotica.grafostudio;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

import java.io.Serializable;

public class VerticeLight extends AppCompatButton implements Grafo, Vertice {
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

    @Override
    public int getTamanhoVertice() {
        return tamanhoVertice;
    }

    public void setTamanhoVertice(int tamanhoVertice) {
        this.tamanhoVertice = tamanhoVertice;
    }

    @Override
    public int getMetadeTamanhoVertice() {
        return this.tamanhoVertice / 2;
    }

    public VerticeLight(Context context) {
        super(context);

        tamanhoVertice = getResources().getDimensionPixelSize(R.dimen.tamanho_vertice);
        this.setBackgroundResource(R.drawable.vertice_light);
    }

    @Override
    public void selecionar() {
        this.setBackgroundResource(R.drawable.vertice_light_pressed);
    }

    @Override
    public void deselecionar() {
        this.setBackgroundResource(R.drawable.vertice_light);
    }
}