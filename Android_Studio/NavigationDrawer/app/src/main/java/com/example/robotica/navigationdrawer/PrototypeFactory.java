package com.example.robotica.navigationdrawer;

import android.content.Context;
import android.graphics.PointF;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

public class PrototypeFactory {
    private static Vertice verticePrototipo;
    private static Aresta arestaPrototipo;
    private FrameLayout.LayoutParams verticeParams;
    private FrameLayout.LayoutParams arestaParams;


    public PrototypeFactory(Context contexto) {
        verticePrototipo = new Vertice(contexto);
        int tamanhoVertice = verticePrototipo.getTamanhoVertice();
        verticeParams = new FrameLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
        verticePrototipo.setLayoutParams(verticeParams);
        verticePrototipo.setBackgroundResource(R.drawable.vertice_button);
        verticePrototipo.setText(String.valueOf(observerMatrizAdjacencias.getQuantidadeVertices()));
        verticePrototipo.setId(observerMatrizAdjacencias.getQuantidadeVertices());

        verticePrototipo.setOnTouchListener(new ClickVertice());

        //observerMatrizAdjacencias.adicionarVertice(vertice);

        arestaPrototipo = new Aresta(contexto);
        arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        arestaPrototipo.setLayoutParams(arestaParams);


        //observerMatrizAdjacencias.adicionarAresta(arestaPrototipo, false);



    }

    public Vertice getVerticePrototipo() {
        return verticePrototipo;
    }

    public Aresta getArestaPrototipo() {
        return arestaPrototipo;
    }
}
