package com.example.robotica.navigationdrawer;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.robotica.navigationdrawer.utils.Calculos;
import com.example.robotica.navigationdrawer.utils.ZoomLayout;

import java.util.ArrayList;
import java.util.List;

public class SingletonFacade {
    private static SingletonFacade instancia = null;
    private final AbstractFactory fabricaVertice, fabricaAresta;
    private CompositeSubjectGrafoFragment grafoFragment;
    private Calculos calculos;
    private static ZoomLayout grafoLayout;
    private int tamanhoVertice;
    private int metadeTamanhoVertice;
    private static int selecaoFerramentas;
    private FrameLayout.LayoutParams verticeParams;
    private FrameLayout.LayoutParams arestaParams;
    private static Vertice verticeSelecionado;
    private PointF pointCentroA;
    private PointF pointCentroB;
    private ObserverMatrizAdjacencias observerMatrizAdjacencias;
    private Handler handler;
    private Snackbar snackbar;

    private SingletonFacade() {
        fabricaVertice = new FactoryVertice();
        fabricaAresta = new FactoryAresta();
    }

    public static SingletonFacade getInstancia() {
        if (instancia == null) {
            instancia = new SingletonFacade();
        }
        return instancia;
    }

    public void criarVertice() {
        fabricaVertice.criarElemento();
    }

    public void criarAresta() {
        fabricaAresta.criarElemento();
    }

    private void init(View view) {
        tamanhoVertice = grafoLayout.getResources().getDimensionPixelSize(R.dimen.tamanho_vertice);
        metadeTamanhoVertice = tamanhoVertice / 2;
        grafoLayout = view.findViewById(R.id.grafoLayout);
        observerMatrizAdjacencias = new ObserverMatrizAdjacencias();
        handler = new Handler();
        calculos = new Calculos();
        grafoFragment = new CompositeSubjectGrafoFragment();

        grafoLayout.setOnTouchListener(onClickTela());
    }


    public void ferramentasEstado(int estado) {
        selecaoFerramentas = estado;
        if (verticeSelecionado != null) {
            verticeSelecionado.setBackgroundResource(R.drawable.vertice_button);
            verticeSelecionado = null;
        }
        switch (estado) {
            case 1: //Selecionar
                break;
            case 2: //Criar Vertice
                snackbar = Snackbar.make(grafoLayout, "Toque na tela para adicionar vertices", Snackbar.LENGTH_INDEFINITE);

                snackbar.setAction("Esconder", onClickSnackbarEsconder());
                snackbar.show();

                break;
            case 3: //Criar Aresta
                snackbar = Snackbar.make(grafoLayout, "Selecione o vertice inicial", Snackbar.LENGTH_INDEFINITE);

                snackbar.setAction("Esconder", onClickSnackbarEsconder());
                snackbar.show();

                break;
            case 4: //Excluir
                snackbar = Snackbar.make(grafoLayout, "Selecione algum elemento para exluí-lo", Snackbar.LENGTH_INDEFINITE);

                snackbar.setAction("Esconder", onClickSnackbarEsconder());
                snackbar.show();

                break;
            default:
                break;
        }
    }

    private View.OnClickListener onClickSnackbarEsconder() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        };
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
                            if (calculos.pontoDentroDoCirculo(pontoCentralFinal, pontoCentral, metadeTamanhoVertice, metadeTamanhoVertice)) {
                                final int x = (int) event.getX();
                                final int y = (int) event.getY();
                                criarVertice(x, y);
                            }
                            break;
                    }
                }
                else if (selecaoFerramentas == 4) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            // Excluir aresta
                            pontoCentral = new PointF(event.getX(), event.getY());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    for (Vertice vertice: observerMatrizAdjacencias.getMapaVerticesAdjacentes().keySet()) {
                                        for (Vertice verticeAdjacente : observerMatrizAdjacencias.getMapaVerticesAdjacentes().get(vertice)) {
                                            int[] lados = calculos.calcularRetanguloDaAresta(vertice, verticeAdjacente, tamanhoVertice);
                                            RectF rect = new RectF(lados[0], lados[1], lados[2], lados[3]);

                                            if (rect.contains(pontoCentral.x, pontoCentral.y)) {
                                                for (Aresta aresta : observerMatrizAdjacencias.getListaArestas()) {
                                                    if (aresta.getVerticeInicial() == vertice && aresta.getVerticeFinal() == verticeAdjacente) {
                                                        if (calculos.pontoToqueSobreAresta(aresta.getPointA(), aresta.getPointB(), pontoCentral)) {
                                                            observerMatrizAdjacencias.removerAresta(aresta);
                                                            grafoLayout.removeView(aresta);
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            });
                            break;
                    }
                }

                return false;
            }
        };
    }

    private void criarVertice(int posicaoX, int posicaoY) {
        final Vertice vertice = new Vertice(grafoFragment.getActivity());
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
    }

    private View.OnTouchListener onClickVertice() {
        return new View.OnTouchListener() {
            float dX, dY;
            private PointF pontoCentralVertice;
            private PointF pontoToqueNaTela;
            private boolean mover;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Vertice vertice = (Vertice) v;
                ArrayList<Aresta> removerArestas = new ArrayList<>();
                if (selecaoFerramentas == 4) {
                    //remover o vertice selecionado
                    for (Aresta aresta : observerMatrizAdjacencias.getListaArestas()) {
                        if (aresta.getVerticeInicial() == vertice) {
                            removerArestas.add(aresta);
                            grafoLayout.removeView(aresta);
                        }
                        else if(aresta.getVerticeFinal() == vertice) {
                            removerArestas.add(aresta);
                            grafoLayout.removeView(aresta);
                        }
                    }
                    for (Aresta aresta : removerArestas) {
                        observerMatrizAdjacencias.removerAresta(aresta);
                    }
                    observerMatrizAdjacencias.removerVertice(vertice);
                    grafoLayout.removeView(vertice);
                }
                else {
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            pontoCentralVertice = new PointF(v.getX() + metadeTamanhoVertice, v.getY() + metadeTamanhoVertice);
                            dX = v.getX() - (event.getRawX()) / grafoLayout.getScaleX();
                            dY = v.getY() - (event.getRawY()) / grafoLayout.getScaleY();
                            mover = false;
                            break;

                        case MotionEvent.ACTION_MOVE:
                            pontoToqueNaTela = new PointF(event.getX() + v.getX(), event.getY() + v.getY());
                            if (!calculos.pontoDentroDoCirculo(pontoToqueNaTela, pontoCentralVertice, v.getWidth(), v.getHeight())) {

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
                            if (selecaoFerramentas == 2) {
                                break;
                            }
                            if (verticeSelecionado == null) {
                                if (!mover) {
                                    verticeSelecionado = vertice;
                                    verticeSelecionado.setBackgroundResource(R.drawable.vertice_button_pressed);
                                    snackbar = Snackbar.make(grafoLayout, "Selecione o vertice final", Snackbar.LENGTH_INDEFINITE);
                                    snackbar.setAction("Esconder", onClickSnackbarEsconder());
                                    snackbar.show();
                                }
                            } else {
                                if (!mover && verticeSelecionado != vertice) {
                                    if (selecaoFerramentas == 3) {
                                        verticeSelecionado.setBackgroundResource(R.drawable.vertice_button);
                                        criarAresta(verticeSelecionado, vertice);
                                        verticeSelecionado = null;
                                        snackbar = Snackbar.make(grafoLayout, "Selecione o vertice inicial", Snackbar.LENGTH_INDEFINITE);
                                        snackbar.setAction("Esconder", onClickSnackbarEsconder());
                                        snackbar.show();
                                    } else {
                                        verticeSelecionado.setBackgroundResource(R.drawable.vertice_button);
                                        verticeSelecionado = vertice;
                                        verticeSelecionado.setBackgroundResource(R.drawable.vertice_button_pressed);
                                    }
                                } else {
                                    verticeSelecionado.setBackgroundResource(R.drawable.vertice_button);
                                    verticeSelecionado = null;
                                }
                            }
                            break;
                    }
                }
                return false;
            }
        };
    }

    private void criarAresta(final Vertice verticeA, final Vertice verticeB) {
        handler.post(new Runnable() {
            @Override
            public void run() {
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
            }
        });
    }

    public static void moverViewParaBaixo(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

    private void moverArestas(Vertice vertice) { //Mover as arestas ligadas ao vertice passado como argumento
        for (Aresta aresta : observerMatrizAdjacencias.getListaArestas()) {
            if (aresta.getVerticeInicial() == vertice) {
                pointCentroA = new PointF(vertice.getX() + metadeTamanhoVertice, vertice.getY() + metadeTamanhoVertice);
                pointCentroB = new PointF(aresta.getVerticeFinal().getX() + metadeTamanhoVertice, aresta.getVerticeFinal().getY() + metadeTamanhoVertice);
                List interseccaoA = calculos.getPontoInterseccao(pointCentroA, pointCentroB, pointCentroA, metadeTamanhoVertice);
                List interseccaoB = calculos.getPontoInterseccao(pointCentroA, pointCentroB, pointCentroB, metadeTamanhoVertice);
                arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                aresta.setLayoutParams(arestaParams);
                aresta.setPointA((PointF) interseccaoA.get(1));
                aresta.setPointB((PointF) interseccaoB.get(0));
            }
            else if(aresta.getVerticeFinal() == vertice) {
                pointCentroA = new PointF(aresta.getVerticeInicial().getX() + metadeTamanhoVertice, aresta.getVerticeInicial().getY() + metadeTamanhoVertice);
                pointCentroB = new PointF(vertice.getX() + metadeTamanhoVertice, vertice.getY() + metadeTamanhoVertice);
                List interseccaoA = calculos.getPontoInterseccao(pointCentroA, pointCentroB, pointCentroA, metadeTamanhoVertice);
                List interseccaoB = calculos.getPontoInterseccao(pointCentroA, pointCentroB, pointCentroB, metadeTamanhoVertice);
                arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                aresta.setLayoutParams(arestaParams);
                aresta.setPointA((PointF) interseccaoA.get(1));
                aresta.setPointB((PointF) interseccaoB.get(0));
            }
        }
    }
}
