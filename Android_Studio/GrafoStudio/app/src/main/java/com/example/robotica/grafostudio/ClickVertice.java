package com.example.robotica.grafostudio;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.robotica.grafostudio.utils.Calculos;
import com.example.robotica.grafostudio.utils.Ponto;

public class ClickVertice implements View.OnTouchListener {
    float dX, dY;
    private Ponto pontoCentralVertice;
    private Ponto pontoToqueNaTela;
    private boolean mover;
    private SingletonFacade facade;
    private Calculos calculos;
    private FrameLayout.LayoutParams arestaParams;

    public ClickVertice() {
        facade = SingletonFacade.getInstancia();
        calculos = new Calculos();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Vertice vertice = (Vertice) v;
        if (facade.getEstadoFerramentas() == 4) {
            facade.removerVertice(vertice);
        }
        else {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    pontoCentralVertice = new Ponto(v.getX() + vertice.getMetadeTamanhoVertice(), v.getY() + vertice.getMetadeTamanhoVertice());
                    dX = v.getX() - (event.getRawX()) / CompositeSubjectGrafoFragment.getGrafoLayout().getScaleX();
                    dY = v.getY() - (event.getRawY()) / CompositeSubjectGrafoFragment.getGrafoLayout().getScaleY();
                    mover = false;
                    break;

                case MotionEvent.ACTION_MOVE:
                    pontoToqueNaTela = new Ponto(event.getX() + v.getX(), event.getY() + v.getY());
                    if (!calculos.pontoDentroDoCirculo(pontoToqueNaTela, pontoCentralVertice, v.getWidth(), v.getHeight())) {
                        float x = dX + (event.getRawX() / CompositeSubjectGrafoFragment.getGrafoLayout().getScaleX());
                        float y = dY + (event.getRawY() / CompositeSubjectGrafoFragment.getGrafoLayout().getScaleY());
                        PointF ponto = new PointF(x, y);
                        moverVertice(v, ponto);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    String mensagem;
                    if (facade.getEstadoFerramentas() == 2) {
                        break;
                    }
                    Vertice verticeSelecionado = facade.getGrafoFragment().getVerticeSelecionado();
                    if (verticeSelecionado == null) {
                        if (!mover) {
                            facade.selecionarVertice(vertice);
                            mensagem = "Selecione o vertice final";
                            facade.snackBar(mensagem);
                        }
                    } else {
                        if (!mover && verticeSelecionado != vertice) {
                            if (facade.getEstadoFerramentas() == 3) {
                                facade.criarAresta(verticeSelecionado, vertice);
                                facade.deselecionarVertice();
                                mensagem = "Selecione o vertice inicial";
                                facade.snackBar(mensagem);
                            } else {
                                facade.selecionarVertice(vertice);
                            }
                        } else {
                            facade.deselecionarVertice();
                        }
                    }
                    break;
            }
        }
        return false;
    }

    private void moverVertice(View v, PointF ponto) {
        v.animate() // Mover Vertice
                .x(ponto.x)
                .y(ponto.y)
                .setDuration(0)
                .start();

        mover = true;
        moverArestasConectadas((Vertice) v);
    }

    private void moverArestasConectadas(Vertice vertice) { //Mover as arestas ligadas ao vertice passado como argumento
        for (Aresta aresta : facade.getGrafoFragment().getListaArestas()) {
            if (aresta.getVerticeInicial() == vertice) {
                arestaParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                aresta.setLayoutParams(arestaParams);
                aresta.setPontoInicial(calculos.getPontoInterseccaoAresta(aresta).get(0));
                aresta.setPontoFinal(calculos.getPontoInterseccaoAresta(aresta).get(1));
            }
            else if(aresta.getVerticeFinal() == vertice) {
                arestaParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                aresta.setLayoutParams(arestaParams);
                aresta.setPontoInicial(calculos.getPontoInterseccaoAresta(aresta).get(1));
                aresta.setPontoFinal(calculos.getPontoInterseccaoAresta(aresta).get(0));
            }
        }
    }
}
