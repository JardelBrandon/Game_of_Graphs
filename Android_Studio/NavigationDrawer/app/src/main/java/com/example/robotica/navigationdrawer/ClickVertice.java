package com.example.robotica.navigationdrawer;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.robotica.navigationdrawer.utils.Calculos;

import java.util.List;

public class ClickVertice implements View.OnTouchListener {
    float dX, dY;
    private PointF pontoCentralVertice;
    private PointF pontoToqueNaTela;
    private boolean mover;
    private SingletonFacade facade;
    private Calculos calculos;

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
                    pontoCentralVertice = new PointF(v.getX() + vertice.getMetadeTamanhoVertice(), v.getY() + vertice.getMetadeTamanhoVertice());
                    dX = v.getX() - (event.getRawX()) / CompositeSubjectGrafoFragment.getGrafoLayout().getScaleX();
                    dY = v.getY() - (event.getRawY()) / CompositeSubjectGrafoFragment.getGrafoLayout().getScaleY();
                    mover = false;
                    break;

                case MotionEvent.ACTION_MOVE:
                    pontoToqueNaTela = new PointF(event.getX() + v.getX(), event.getY() + v.getY());
                    if (!calculos.pontoDentroDoCirculo(pontoToqueNaTela, pontoCentralVertice, v.getWidth(), v.getHeight())) {

                        v.animate() // Mover Vertice
                                .x(dX + (event.getRawX() / CompositeSubjectGrafoFragment.getGrafoLayout().getScaleX()))
                                .y(dY + (event.getRawY() / CompositeSubjectGrafoFragment.getGrafoLayout().getScaleY()))
                                .setDuration(0)
                                .start();

                        mover = true;
                        moverArestasConectadas(vertice);
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
                            verticeSelecionado = vertice;
                            verticeSelecionado.setBackgroundResource(R.drawable.vertice_button_pressed);
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
                                facade.selecionarVertice(verticeSelecionado);
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

    private void moverArestasConectadas(Vertice vertice) { //Mover as arestas ligadas ao vertice passado como argumento
        for (Aresta aresta : facade.getGrafoFragment().getListaArestas()) {
            if (aresta.getVerticeInicial() == vertice) {
//                pointCentroA = new PointF(vertice.getX() + vertice.getMetadeTamanhoVertice(), vertice.getY() + vertice.getMetadeTamanhoVertice());
//                pointCentroB = new PointF(aresta.getVerticeFinal().getX() + vertice.getMetadeTamanhoVertice(), aresta.getVerticeFinal().getY() + vertice.getMetadeTamanhoVertice());
//                List interseccaoA = calculos.getPontoInterseccaoVerticeAresta(pointCentroA, pointCentroB, pointCentroA, vertice.getMetadeTamanhoVertice());
//                List interseccaoB = calculos.getPontoInterseccaoVerticeAresta(pointCentroA, pointCentroB, pointCentroB, vertice.getMetadeTamanhoVertice());
//                arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                aresta.setLayoutParams(arestaParams);
//                aresta.setPointA((PointF) interseccaoA.get(1));
//                aresta.setPointB((PointF) interseccaoB.get(0));
                aresta.setPointA(calculos.getPontoInterseccaoAresta(aresta).get(0));
                aresta.setPointB(calculos.getPontoInterseccaoAresta(aresta).get(1));
            }
            else if(aresta.getVerticeFinal() == vertice) {
//                pointCentroA = new PointF(aresta.getVerticeInicial().getX() + metadeTamanhoVertice, aresta.getVerticeInicial().getY() + metadeTamanhoVertice);
//                pointCentroB = new PointF(vertice.getX() + metadeTamanhoVertice, vertice.getY() + metadeTamanhoVertice);
//                List interseccaoA = calculos.getPontoInterseccaoVerticeAresta(pointCentroA, pointCentroB, pointCentroA, metadeTamanhoVertice);
//                List interseccaoB = calculos.getPontoInterseccaoVerticeAresta(pointCentroA, pointCentroB, pointCentroB, metadeTamanhoVertice);
//                arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                aresta.setLayoutParams(arestaParams);
//                aresta.setPointA((PointF) interseccaoA.get(1));
//                aresta.setPointB((PointF) interseccaoB.get(0));
                aresta.setPointA(calculos.getPontoInterseccaoAresta(aresta).get(1));
                aresta.setPointB(calculos.getPontoInterseccaoAresta(aresta).get(0));
            }
        }
    }
}
