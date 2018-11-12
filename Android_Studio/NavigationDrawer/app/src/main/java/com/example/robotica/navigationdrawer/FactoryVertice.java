package com.example.robotica.navigationdrawer;

import android.content.Context;
import android.widget.FrameLayout;

public class FactoryVertice extends AbstractFactory {
    private FrameLayout.LayoutParams verticeParams;

    @Override
    public Grafo criarElemento(Context context, int posicaoX, int posicaoY) {
        final Vertice vertice = new Vertice(context);
        verticeParams = new FrameLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
        verticeParams.setMargins(posicaoX - metadeTamanhoVertice, posicaoY - metadeTamanhoVertice, 0, 0);
        vertice.setLayoutParams(verticeParams);
        vertice.setBackgroundResource(R.drawable.vertice_button);
        vertice.setText(String.valueOf(observerMatrizAdjacencias.getQuantidadeVertices()));
        vertice.setId(observerMatrizAdjacencias.getQuantidadeVertices());

        vertice.setOnTouchListener(onClickVertice());

        observerMatrizAdjacencias.adicionarVertice(vertice);
        grafoLayout.addView(vertice);
        moverViewParaBaixo(vertice);
        //Log.d("ObserverMatrizAdjacencias", observerMatrizAdjacencias.toString());
        return vertice;
    }
}
