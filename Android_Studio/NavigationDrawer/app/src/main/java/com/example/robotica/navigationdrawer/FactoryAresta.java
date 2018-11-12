package com.example.robotica.navigationdrawer;

import android.graphics.PointF;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

public class FactoryAresta extends AbstractFactory {
    Handler handler;
    private PointF pointCentroA;
    private PointF pointCentroB;

    @Override
    public Grafo criarElemento() {
        //Criar Aresta
        pointCentroA = new PointF(verticeA.getX() + metadeTamanhoVertice, verticeA.getY() + metadeTamanhoVertice);
        pointCentroB = new PointF(verticeB.getX() + metadeTamanhoVertice, verticeB.getY() + metadeTamanhoVertice);
        List interseccaoA = calculos.getPontoInterseccao(pointCentroA, pointCentroB, pointCentroA, metadeTamanhoVertice);
        List interseccaoB = calculos.getPontoInterseccao(pointCentroA, pointCentroB, pointCentroB, metadeTamanhoVertice);

        final Aresta aresta = new Aresta(grafoFragment.getActivity());
        arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        aresta.setLayoutParams(arestaParams);
        aresta.setPointA((PointF) interseccaoA.get(1));
        aresta.setPointB((PointF) interseccaoB.get(0));
        aresta.setVerticeInicial(verticeA);
        aresta.setVerticeFinal(verticeB);

        observerMatrizAdjacencias.adicionarAresta(aresta, false);
        grafoLayout.addView(aresta);
        moverViewParaBaixo(verticeA);
        moverViewParaBaixo(verticeB);
        //Log.d("ObserverMatrizAdjacencias", observerMatrizAdjacencias.toString());

        return aresta;
    }
}
