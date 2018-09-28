package com.example.jardel.grafostudio;


import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.otaliastudios.zoom.ZoomLayout;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GrafoFragment extends Fragment {
    private com.example.jardel.grafostudio.ZoomLayout grafoLayout;
    private int tamanhoVertice;
    private int metadeTamanhoVertice;
    private int dobroTamanhoVertice;
    private FrameLayout.LayoutParams verticeParams;
    private FrameLayout.LayoutParams arestaParams;
    private PointF pointCentroA;
    private PointF pointCentroB;
    private MatrizAdjacencias matrizAdjacencias;

    public GrafoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
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
        //grafoLayout = view.findViewById(R.id.grafoLayout);
        grafoLayout = view.findViewById(R.id.grafoLayout);
        matrizAdjacencias = new MatrizAdjacencias();

        grafoLayout.setOnTouchListener(onClickTela());
    }


    private View.OnTouchListener onClickTela() {
        return new View.OnTouchListener() {
            private Boolean moverTela = false;
            private PointF pointCentroVertice;
            private PointF pointToqueNaTela;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                desmarcarVerticesSelecionados();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        pointCentroVertice = new PointF(event.getX(), event.getY());
                        moverTela = false;
                        break;


                    case MotionEvent.ACTION_UP:
                        pointToqueNaTela = new PointF(event.getX(), event.getY());
                        if (pointDentroDoVertice(pointToqueNaTela, pointCentroVertice, metadeTamanhoVertice, metadeTamanhoVertice)) {
                            event.transform(grafoLayout.getMatrixInverse());
                            final int x = (int) event.getX();
                            final int y = (int) event.getY();
                            criarVertice(x, y);
                        }
                        break;
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
        moverViewParaBaixo(vertice);
        //Log.d("MatrizAdjacencias", matrizAdjacencias.toString());
    }

    private View.OnTouchListener onClickVertice(final Vertice vertice) {
        return new View.OnTouchListener() {
            private PointF pointCentroVertice;
            private PointF pointToqueNaTela;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        vertice.setSelecionado(!vertice.isSelecionado());
                        pointCentroVertice = new PointF(v.getX() + metadeTamanhoVertice, v.getY() + metadeTamanhoVertice);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        pointToqueNaTela = new PointF(event.getX() + v.getX(), event.getY() + v.getY());
                        if(!pointDentroDoVertice(pointToqueNaTela, pointCentroVertice, v.getWidth(), v.getHeight())) {
                            desmarcarVerticesSelecionados();
                            moverVertice(vertice, v, event);
                            vertice.setSelecionado(false);
                            moverArestas(vertice);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        verificarVerticesSelecionados(vertice);
                        break;
                }
                return false;
            }
        };
    }

    public boolean pointDentroDoVertice(PointF test, PointF center, float width, float height) {
        float dx = test.x - center.x;
        float dy = test.y - center.y;
        return ((dx * dx) / (width * width) + (dy * dy) / (height * height)) * 4 <= 1;
    }

    private void moverVertice(Vertice vertice, View v, MotionEvent event) {
        verticeParams = new FrameLayout.LayoutParams(v.getWidth(), v.getHeight());
        verticeParams.setMargins((int) event.getRawX() - metadeTamanhoVertice, (int) event.getRawY() - dobroTamanhoVertice, 0, 0);
        vertice.setLayoutParams(verticeParams);
    }

    private void verificarVerticesSelecionados(Vertice verticeSelecionado) {
        if (verticeSelecionado.isSelecionado()) {
            verticeSelecionado.setBackgroundResource(R.drawable.vertice_button_pressed);

            for (Vertice vertice : matrizAdjacencias.getListaVertices()) {
                if (vertice.isSelecionado() && verticeSelecionado != vertice) {
                    if (matrizAdjacencias.getMapaVerticesAdjacentes().get(verticeSelecionado).contains(vertice)) {
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
            verticeSelecionado.setBackgroundResource(R.drawable.vertice_button);
        }
    }

    private void criarAresta(Vertice verticeA, Vertice verticeB) {
        pointCentroA = new PointF(verticeA.getX() + metadeTamanhoVertice, verticeA.getY() + metadeTamanhoVertice);
        pointCentroB = new PointF(verticeB.getX() + metadeTamanhoVertice, verticeB.getY() + metadeTamanhoVertice);
        List interseccaoA = getPontoInterseccao(pointCentroA, pointCentroB, pointCentroA, metadeTamanhoVertice);
        List interseccaoB = getPontoInterseccao(pointCentroA, pointCentroB, pointCentroB, metadeTamanhoVertice);
        final Aresta aresta = new Aresta(getActivity());
        arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        aresta.setLayoutParams(arestaParams);
        aresta.setPointA((PointF) interseccaoA.get(1));
        aresta.setPointB((PointF) interseccaoB.get(0));
        aresta.setVerticeInicial(verticeA);
        aresta.setVerticeFinal(verticeB);
        matrizAdjacencias.adicionarAresta(aresta, false);
        grafoLayout.addView(aresta);
        moverViewParaBaixo(verticeA);
        moverViewParaBaixo(verticeB);
        Log.d("MatrizAdjacencias", matrizAdjacencias.toString());
    }

    public static void moverViewParaBaixo(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

    private void moverArestas(Vertice vertice) { //Mover as arestas ligadas ao vertice passado como argumento
        for (Aresta aresta : matrizAdjacencias.getListaArestas()) {
            if (aresta.getVerticeInicial() == vertice) {
                pointCentroA = new PointF(vertice.getX() + metadeTamanhoVertice, vertice.getY() + metadeTamanhoVertice);
                pointCentroB = new PointF(aresta.getVerticeFinal().getX() + metadeTamanhoVertice, aresta.getVerticeFinal().getY() + metadeTamanhoVertice);
                List interseccaoA = getPontoInterseccao(pointCentroA, pointCentroB, pointCentroA, metadeTamanhoVertice);
                List interseccaoB = getPontoInterseccao(pointCentroA, pointCentroB, pointCentroB, metadeTamanhoVertice);
                arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                aresta.setLayoutParams(arestaParams);
                aresta.setPointA((PointF) interseccaoA.get(1));
                aresta.setPointB((PointF) interseccaoB.get(0));
            }
            else if(aresta.getVerticeFinal() == vertice) {
                pointCentroA = new PointF(aresta.getVerticeInicial().getX() + metadeTamanhoVertice, aresta.getVerticeInicial().getY() + metadeTamanhoVertice);
                pointCentroB = new PointF(vertice.getX() + metadeTamanhoVertice, vertice.getY() + metadeTamanhoVertice);
                List interseccaoA = getPontoInterseccao(pointCentroA, pointCentroB, pointCentroA, metadeTamanhoVertice);
                List interseccaoB = getPontoInterseccao(pointCentroA, pointCentroB, pointCentroB, metadeTamanhoVertice);
                arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                aresta.setLayoutParams(arestaParams);
                aresta.setPointA((PointF) interseccaoA.get(1));
                aresta.setPointB((PointF) interseccaoB.get(0));
            }
        }
    }

    private void desmarcarVerticesSelecionados() {
        for (Vertice vertice : matrizAdjacencias.getListaVertices()) {
            if (vertice.isSelecionado()) {
                vertice.setBackgroundResource(R.drawable.vertice_button);
                vertice.setSelecionado(false);
            }
        }
    }

    private List<PointF> getPontoInterseccao(PointF pointA, PointF pointB, PointF center, float radius) {
        float baX = pointB.x - pointA.x;
        float baY = pointB.y - pointA.y;
        float caX = center.x - pointA.x;
        float caY = center.y - pointA.y;

        float a = baX * baX + baY * baY;
        float bBy2 = baX * caX + baY * caY;
        float c = caX * caX + caY * caY - radius * radius;

        float pBy2 = bBy2 / a;
        float q = c / a;

        float disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }
        // if disc == 0 ... dealt with later
        float tmpSqrt = (float) Math.sqrt(disc);
        float abScalingFactor1 = -pBy2 + tmpSqrt;
        float abScalingFactor2 = -pBy2 - tmpSqrt;

        PointF p1 = new PointF(pointA.x - baX * abScalingFactor1, pointA.y - baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        PointF p2 = new PointF(pointA.x - baX * abScalingFactor2, pointA.y - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }
}
