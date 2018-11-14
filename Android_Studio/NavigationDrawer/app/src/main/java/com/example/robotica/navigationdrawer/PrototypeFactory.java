package com.example.robotica.navigationdrawer;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class PrototypeFactory {
    private static Vertice verticePrototipo;
    private static Aresta arestaPrototipo;
    private static FrameLayout.LayoutParams verticeParams;
    private static FrameLayout.LayoutParams arestaParams;

    public PrototypeFactory(Context contexto) {
        verticePrototipo = new Vertice(contexto);
        int tamanhoVertice = verticePrototipo.getTamanhoVertice();
        verticeParams = new FrameLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
        verticePrototipo.setLayoutParams(verticeParams);
        verticePrototipo.setBackgroundResource(R.drawable.vertice_button);



        arestaPrototipo = new Aresta(contexto);
        arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        arestaPrototipo.setLayoutParams(arestaParams);
    }

    public Vertice getVerticePrototipo() {
        return verticePrototipo;
    }

    public Aresta getArestaPrototipo() {
        return arestaPrototipo;
    }
}
