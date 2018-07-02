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
public class    GrafoFragment extends Fragment {
    private FrameLayout grafoLayout;
    private int _xDelta;
    private int _yDelta;
    private int contadorVertices;
    private int contadorArestas;
    private int tamanhoVertice;
    private int metadeTamanhoVertice;
    private int dobroTamanhoVertice;
    private FrameLayout.LayoutParams verticeParams;
    private FrameLayout.LayoutParams arestaParams;
    private PointF pointA;
    private PointF pointB;
    private Map<String, Vertice> mapaVertices;
    private Map<String, Aresta> mapaArestas;

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
        contadorVertices = 0;
        contadorArestas = 0;
        mapaVertices = new HashMap<>();
        mapaArestas = new HashMap<>();
        tamanhoVertice = getResources().getDimensionPixelSize(R.dimen.tamanho_vertice);
        metadeTamanhoVertice = tamanhoVertice / 2;
        dobroTamanhoVertice = tamanhoVertice * 2;
        grafoLayout = (FrameLayout) view.findViewById(R.id.grafoLayout);

        grafoLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int x = (int) event.getX();
                final int y = (int) event.getY();

                for (Vertice vertice : mapaVertices.values()) {
                    if (vertice.isSelecionado()) {
                        vertice.setBackgroundResource(R.drawable.vertice_button);
                        vertice.setSelecionado(false);
                    }
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    final Vertice vertice = new Vertice(getActivity());
                    verticeParams = new FrameLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
                    verticeParams.setMargins(x - metadeTamanhoVertice, y - metadeTamanhoVertice, 0, 0);
                    vertice.setLayoutParams(verticeParams);
                    vertice.setBackgroundResource(R.drawable.vertice_button);
                    vertice.setText(String.valueOf(contadorVertices));
                    vertice.setId(contadorVertices);
                    vertice.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                                case MotionEvent.ACTION_DOWN:
                                    vertice.setSelecionado(true);
                                    break;

                                case MotionEvent.ACTION_MOVE:
                                    verticeParams = new FrameLayout.LayoutParams(v.getWidth(), v.getHeight());
                                    verticeParams.setMargins((int) event.getRawX() - metadeTamanhoVertice, (int) event.getRawY() - dobroTamanhoVertice, 0, 0);
                                    vertice.setLayoutParams(verticeParams);
                                    vertice.setSelecionado(false);
                                    break;

                                case MotionEvent.ACTION_UP:
                                    if (vertice.isSelecionado()) {
                                        Vertice verticeSelecionado = vertice;
                                        pointA = new PointF(vertice.getX() + metadeTamanhoVertice, vertice.getY() + metadeTamanhoVertice);
                                        vertice.setBackgroundResource(R.drawable.fab_oval);

                                        for (Vertice vertice : mapaVertices.values()) {
                                            if (vertice.isSelecionado() && verticeSelecionado != vertice) {
                                                pointB = new PointF(vertice.getX() + metadeTamanhoVertice, vertice.getY() + metadeTamanhoVertice);

                                                if (mapaArestas.isEmpty()) {
                                                    final Aresta novaAresta = new Aresta(getActivity());
                                                    arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                    novaAresta.setLayoutParams(arestaParams);
                                                    novaAresta.setPointA(pointA);
                                                    novaAresta.setPointB(pointB);
                                                    novaAresta.setVerticeInicial(vertice);
                                                    novaAresta.setVerticeFinal(verticeSelecionado);
                                                    mapaArestas.put(String.valueOf(contadorArestas), novaAresta);
                                                    grafoLayout.addView(novaAresta);
                                                    contadorArestas++;
                                                } else {
                                                    for (Aresta aresta : mapaArestas.values()) {
                                                        if (!((aresta.getVerticeInicial() == vertice && aresta.getVerticeFinal() == verticeSelecionado)
                                                                || (aresta.getVerticeInicial() == verticeSelecionado && aresta.getVerticeFinal() == vertice))) {
                                                            final Aresta novaAresta = new Aresta(getActivity());
                                                            arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                                            novaAresta.setLayoutParams(arestaParams);
                                                            novaAresta.setPointA(pointA);
                                                            novaAresta.setPointB(pointB);
                                                            novaAresta.setVerticeInicial(vertice);
                                                            novaAresta.setVerticeFinal(verticeSelecionado);
                                                            mapaArestas.put(String.valueOf(contadorArestas), novaAresta);
                                                            grafoLayout.addView(novaAresta);
                                                            contadorArestas++;
                                                        }
                                                    }
                                                }
                                                vertice.setBackgroundResource(R.drawable.vertice_button);
                                                verticeSelecionado.setBackgroundResource(R.drawable.vertice_button);
                                                vertice.setSelecionado(false);
                                                verticeSelecionado.setSelecionado(false);
                                            }
                                        }
                                    }
                                    break;
                            }
                            return false;
                        }
                    });
                    mapaVertices.put(String.valueOf(contadorVertices), vertice);
                    grafoLayout.addView(vertice);
                    contadorVertices ++;
                }
                return false;
            }
        });
    }
}


    
/*

    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            grafoLayout.invalidate();
            return true;
        }
    }
}


        rootLayout = (ViewGroup) findViewById(R.id.rootLayout);
        vertice = (Button) rootLayout.findViewById(R.id.vetice1);
        vertice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Teste click", Toast.LENGTH_SHORT).show();
            }
        });

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        vertice.setLayoutParams(layoutParams);
        vertice.setOnTouchListener(new ChoiceTouchListener());

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Longo click", Toast.LENGTH_SHORT).show();
            }
        });
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GrafoFragment grafoFragment = new GrafoFragment();
        fragmentTransaction.add(R.id.rootLayout, grafoFragment, "Fragmento");
 */