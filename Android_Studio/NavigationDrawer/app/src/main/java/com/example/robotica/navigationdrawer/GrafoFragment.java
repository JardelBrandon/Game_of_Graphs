package com.example.robotica.navigationdrawer;


import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.robotica.navigationdrawer.utils.ZoomLayout;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class GrafoFragment extends Fragment {
    private static ZoomLayout grafoLayout;
    private int tamanhoVertice;
    private int metadeTamanhoVertice;
    private static int selecaoFerramentas;
    private FrameLayout.LayoutParams verticeParams;
    private FrameLayout.LayoutParams arestaParams;
    private Vertice verticeSelecionado;
    private PointF pointCentroA;
    private PointF pointCentroB;
    private MatrizAdjacencias matrizAdjacencias;
    private Handler handler;

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
        init(view);
    }

    private void init(View view) {
        tamanhoVertice = getResources().getDimensionPixelSize(R.dimen.tamanho_vertice);
        metadeTamanhoVertice = tamanhoVertice / 2;
        grafoLayout = view.findViewById(R.id.grafoLayout);
        matrizAdjacencias = new MatrizAdjacencias();
        handler = new Handler();

        grafoLayout.setOnTouchListener(onClickTela());
    }

    public void ferramentasEstado(int estado) {
        selecaoFerramentas = estado;
        switch (estado) {
            case 1: //Selecionar
                break;
            case 2: //Criar Vertice
                final Snackbar snackBarVertice = Snackbar.make(grafoLayout, "Toque na tela para adicionar vertices", Snackbar.LENGTH_INDEFINITE);

                snackBarVertice.setAction("Esconder", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBarVertice.dismiss();
                    }
                });
                snackBarVertice.show();

                break;
            case 3: //Criar Aresta
                final Snackbar snackBarAresta = Snackbar.make(grafoLayout, "Selecione o vertice inicial", Snackbar.LENGTH_INDEFINITE);

                snackBarAresta.setAction("Esconder", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBarAresta.dismiss();
                    }
                });
                snackBarAresta.show();

                break;
            case 4: //Excluir
                final Snackbar snackBarExcluir = Snackbar.make(grafoLayout, "Selecione algum elemento para exluí-lo", Snackbar.LENGTH_INDEFINITE);

                snackBarExcluir.setAction("Esconder", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBarExcluir.dismiss();
                    }
                });
                snackBarExcluir.show();

                break;
            default:
                break;
        }
    }

    private View.OnTouchListener onClickTela() {
        return new View.OnTouchListener() {
            private PointF pontoCentral;
            private PointF pontoCentralFinal;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (selecaoFerramentas == 2) {
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            pontoCentral = new PointF(event.getRawX(), event.getRawY());
                            if (verticeSelecionado != null) {
                                verticeSelecionado.setBackgroundResource(R.drawable.vertice_button);
                                verticeSelecionado = null;
                            }
                            break;


                        case MotionEvent.ACTION_UP:
                            pontoCentralFinal = new PointF(event.getRawX(), event.getRawY());
                            if (pontoDentroDoCirculo(pontoCentralFinal, pontoCentral, metadeTamanhoVertice, metadeTamanhoVertice)) {
                                final int x = (int) event.getX();
                                final int y = (int) event.getY();
                                criarVertice(x, y);
                            }
                            break;
                    }
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

        vertice.setOnTouchListener(onClickVertice());

        matrizAdjacencias.adicionarVertice(vertice);
        grafoLayout.addView(vertice);
        moverViewParaBaixo(vertice);
        //Log.d("MatrizAdjacencias", matrizAdjacencias.toString());
    }

    private View.OnTouchListener onClickVertice() {
        return new View.OnTouchListener() {
            float dX, dY;
            private PointF pontoCentralVertice;
            private PointF pontoToqueNaTela;
            private boolean mover;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (selecaoFerramentas) {
                    case 1:
                        //rotina para seleção
                        break;
                    case 2:
                        //rotina para criação do vertice
                        break;
                    case 3:
                        //rotina para criação da aresta
                        break;
                    case 4:
                        //rotina para remoção do vertice
                        break;
                }
                Vertice vertice = (Vertice) v;
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        pontoCentralVertice = new PointF(v.getX() + metadeTamanhoVertice, v.getY() + metadeTamanhoVertice);
                        dX = v.getX() - (event.getRawX()) / grafoLayout.getScaleX();
                        dY = v.getY() - (event.getRawY()) / grafoLayout.getScaleY();
                        mover = false;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        pontoToqueNaTela = new PointF(event.getX() + v.getX(), event.getY() + v.getY());
                        if(!pontoDentroDoCirculo(pontoToqueNaTela, pontoCentralVertice, v.getWidth(), v.getHeight())) {

                            v.animate() // Mover Vertice
                                    .x(dX + (event.getRawX() / grafoLayout.getScaleX()))
                                    .y(dY + (event.getRawY() / grafoLayout.getScaleY()))
                                    .setDuration(0)
                                    .start();

                            mover = true;
                            moverArestas(vertice);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if (verticeSelecionado == null) {
                            if (!mover) {
                                verticeSelecionado = vertice;
                                verticeSelecionado.setBackgroundResource(R.drawable.vertice_button_pressed);
                            }
                        }
                        else {
                            if (!mover && verticeSelecionado != vertice) {
                                verticeSelecionado.setBackgroundResource(R.drawable.vertice_button);
                                criarAresta(verticeSelecionado, vertice);
                                verticeSelecionado = null;
                            }
                            else {
                                verticeSelecionado.setBackgroundResource(R.drawable.vertice_button);
                                verticeSelecionado = null;
                            }
                        }
                        break;
                }
                return false;
            }
        };
    }



    public boolean pontoDentroDoCirculo(PointF test, PointF center, float width, float height) {
        float dx = test.x - center.x;
        float dy = test.y - center.y;
        return ((dx * dx) / (width * width) + (dy * dy) / (height * height)) * 4 <= 1;
    }

    private void criarAresta(final Vertice verticeA, final Vertice verticeB) {
        handler.post(new Runnable() {
            @Override
            public void run() {
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

                aresta.setOnTouchListener(onClickAresta());

                matrizAdjacencias.adicionarAresta(aresta, false);
                grafoLayout.addView(aresta);
                moverViewParaBaixo(verticeA);
                moverViewParaBaixo(verticeB);
                //Log.d("MatrizAdjacencias", matrizAdjacencias.toString());
            }
        });
    }

    private View.OnTouchListener onClickAresta() {
        return new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (selecaoFerramentas) {
                    case 1:
                        //rotina para seleção
                        break;
                    case 2:
                        //rotina para criação do vertice
                        break;
                    case 3:
                        //rotina para criação da aresta
                        break;
                    case 4:
                        //rotina para remoção do vertice
                        break;
                }

                return false;
            }
        };
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
