package com.example.robotica.grafostudio;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.example.robotica.grafostudio.utils.Calculos;
import com.example.robotica.grafostudio.utils.Ponto;

import java.util.ArrayList;

public class ClickTela implements View.OnTouchListener {
    private Ponto pontoCentral;
    private Ponto pontoCentralFinal;
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
                    pontoCentral = new Ponto(event.getRawX(), event.getRawY());
                    facade.deselecionarVertice();
                    break;


                case MotionEvent.ACTION_UP:
                    pontoCentralFinal = new Ponto(event.getRawX(), event.getRawY());
                    if (calculos.pontoDentroDoCirculo(pontoCentralFinal, pontoCentral, R.dimen.tamanho_vertice, R.dimen.tamanho_vertice)) {
                        Ponto pontoCriarVertice = new Ponto(event.getX(), event.getY());
                        facade.criarVertice(pontoCriarVertice);
                    }
                    break;
            }
        } else if (facade.getEstadoFerramentas() == 4) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    pontoCentral = new Ponto(event.getX(), event.getY());
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            excluirArestas(pontoCentral);
                        }
                    });
                    break;
            }
        }
        return false;
    }

    private void excluirArestas(Ponto pontoToqueNaTela) {
        ArrayList<Aresta> excluirArestas = new ArrayList<>();

        for (Vertice vertice : facade.getGrafoFragment().getMapaVerticesAdjacentes().keySet()) {
            for (Vertice verticeAdjacente : facade.getGrafoFragment().getMapaVerticesAdjacentes().get(vertice)) {
                int[] lados = calculos.calcularRetanguloDaAresta(vertice, verticeAdjacente, vertice.getTamanhoVertice());
                RectF rect = new RectF(lados[0], lados[1], lados[2], lados[3]);

                if (rect.contains(pontoToqueNaTela.x, pontoToqueNaTela.y)) {
                    for (Aresta aresta : facade.getGrafoFragment().getListaArestas()) {
                        if (aresta.getVerticeInicial() == vertice && aresta.getVerticeFinal() == verticeAdjacente) {
                            if (calculos.pontoToqueSobreAresta(aresta.getPontoInicial(), aresta.getPontoFinal(), pontoCentral)) {
                                excluirArestas.add(aresta);
                            }
                        }
                    }
                }
            }
        }
        for (Aresta aresta : excluirArestas) {
            facade.removerAresta(aresta);
        }
    }
}
