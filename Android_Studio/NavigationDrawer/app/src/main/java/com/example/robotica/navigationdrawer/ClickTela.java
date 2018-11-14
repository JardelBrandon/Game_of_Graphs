package com.example.robotica.navigationdrawer;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.example.robotica.navigationdrawer.utils.Calculos;

public class ClickTela implements View.OnTouchListener {
    private PointF pontoCentral;
    private PointF pontoCentralFinal;
    private SingletonFacade facade;
    private Calculos calculos;

    public ClickTela() {
        facade = SingletonFacade.getInstancia();
        calculos = new Calculos();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (facade.getEstadoFerramentas() == 2) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    pontoCentral = new PointF(event.getRawX(), event.getRawY());
                    facade.deselecionarVertice();
                    break;


                case MotionEvent.ACTION_UP:
                    pontoCentralFinal = new PointF(event.getRawX(), event.getRawY());
                    if (calculos.pontoDentroDoCirculo(pontoCentralFinal, pontoCentral, R.dimen.tamanho_vertice, R.dimen.tamanho_vertice)) {
                        PointF pontoCriarVertice = new PointF(event.getX(), event.getY());
                        facade.criarVertice(pontoCriarVertice);
                    }
                    break;
            }
        } else if (facade.getEstadoFerramentas() == 4) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    // Excluir aresta
                    pontoCentral = new PointF(event.getX(), event.getY());

                    for (Vertice vertice : facade.getGrafoFragment().getMapaVerticesAdjacentes().keySet()) {
                        for (Vertice verticeAdjacente : facade.getGrafoFragment().getMapaVerticesAdjacentes().get(vertice)) {
                            int[] lados = calculos.calcularRetanguloDaAresta(vertice, verticeAdjacente, vertice.getTamanhoVertice());
                            RectF rect = new RectF(lados[0], lados[1], lados[2], lados[3]);

                            if (rect.contains(pontoCentral.x, pontoCentral.y)) {
                                for (Aresta aresta : facade.getGrafoFragment().getListaArestas()) {
                                    if (aresta.getVerticeInicial() == vertice && aresta.getVerticeFinal() == verticeAdjacente) {
                                        if (calculos.pontoToqueSobreAresta(aresta.getPointA(), aresta.getPointB(), pontoCentral)) {
                                            facade.removerAresta(aresta);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
        }
        return false;
    }
}
