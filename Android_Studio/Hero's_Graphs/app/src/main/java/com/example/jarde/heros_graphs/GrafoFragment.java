package com.example.jarde.heros_graphs;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class GrafoFragment extends Fragment {
    private FrameLayout grafoLayout;
    private int _xDelta;
    private int _yDelta;
    private int tamanhoVertice;
    private int metadeTamanhoVertice;
    private int dobroTamanhoVertice;
    private FrameLayout.LayoutParams verticeParams;
    private FrameLayout.LayoutParams arestaParams;
    private PointF pointA;
    private PointF pointB;
    private MatrizAdjacencias matrizAdjacencias;

    public GrafoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafo, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Inicializando Variávies
        tamanhoVertice = getResources().getDimensionPixelSize(R.dimen.tamanho_vertice);
        metadeTamanhoVertice = tamanhoVertice / 2;
        dobroTamanhoVertice = tamanhoVertice * 2;
        grafoLayout = (FrameLayout) view.findViewById(R.id.grafoLayout);
        matrizAdjacencias = new MatrizAdjacencias();

        grafoLayout.setOnTouchListener(onClickTela());
    }


    private View.OnTouchListener onClickTela() {
        return new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int x = (int) event.getX();
                final int y = (int) event.getY();

                //desmarcarVerticesSelecionados();

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    criarVertice(x, y);
                }
                return false;
            }
        };
    }

    private void criarVertice(int posicaoX, int posicaoY) {
        final Vertice vertice = new Vertice(getActivity());
        verticeParams = new FrameLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
        verticeParams.setMargins(posicaoX - metadeTamanhoVertice, posicaoY - metadeTamanhoVertice, 0, 0);
        vertice.setLayoutParams(verticeParams);
        vertice.setBackgroundResource(R.drawable.vertice_button);
        vertice.setText(String.valueOf(matrizAdjacencias.getQuantidadeVertices()));
        vertice.setId(matrizAdjacencias.getQuantidadeVertices());

        vertice.setOnTouchListener(onClickVertice(vertice));

        matrizAdjacencias.adicionarVertice(vertice);
        grafoLayout.addView(vertice);
    }

    private View.OnTouchListener onClickVertice(final Vertice vertice) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        vertice.setSelecionado(!vertice.isSelecionado());
                        break;

                    case MotionEvent.ACTION_MOVE:
                        verticeParams = new FrameLayout.LayoutParams(v.getWidth(), v.getHeight());
                        verticeParams.setMargins((int) event.getRawX() - metadeTamanhoVertice, (int) event.getRawY() - dobroTamanhoVertice, 0, 0);
                        vertice.setLayoutParams(verticeParams);
                        vertice.setSelecionado(false);
                        desmarcarVerticesSelecionados();
                        break;

                    case MotionEvent.ACTION_UP:
                        if (vertice.isSelecionado()) {
                            Vertice verticeSelecionado = vertice;
                            vertice.setBackgroundResource(R.drawable.fab_oval);

                            for (Vertice vertice : matrizAdjacencias.getListaVertices()) {
                                if (vertice.isSelecionado() && verticeSelecionado != vertice) {
                                    if (matrizAdjacencias.getmapaVerticesAdjacentes().get(verticeSelecionado).contains(vertice)) {
                                        desmarcarVerticesSelecionados();
                                        vertice.setSelecionado(false);
                                        verticeSelecionado.setSelecionado(false);
                                    }
                                    else {
                                        criarAresta(verticeSelecionado, vertice);
                                        desmarcarVerticesSelecionados();
                                        vertice.setSelecionado(false);
                                        verticeSelecionado.setSelecionado(false);
                                    }
                                }
                            }
                        }
                        else {
                            vertice.setBackgroundResource(R.drawable.vertice_button);
                        }
                        break;
                }
                return false;
            }
        };
    }

    private void criarAresta(Vertice verticeA, Vertice verticeB) {
        pointA = new PointF(verticeA.getX() + metadeTamanhoVertice, verticeA.getY() + metadeTamanhoVertice);
        pointB = new PointF(verticeB.getX() + metadeTamanhoVertice, verticeB.getY() + metadeTamanhoVertice);
        final Aresta aresta = new Aresta(getActivity());
        arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        aresta.setLayoutParams(arestaParams);
        aresta.setPointA(pointA);
        aresta.setPointB(pointB);
        aresta.setVerticeInicial(verticeA);
        aresta.setVerticeFinal(verticeB);
        matrizAdjacencias.adicionarAresta(aresta, false);
        grafoLayout.addView(aresta);
    }

    private void desmarcarVerticesSelecionados() {
        for (Vertice vertice : matrizAdjacencias.getListaVertices()) {
            if (vertice.isSelecionado()) {
                vertice.setBackgroundResource(R.drawable.vertice_button);
                vertice.setSelecionado(false);
            }
        }
    }
}
