package com.example.robotica.navigationdrawer;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

import com.example.robotica.navigationdrawer.utils.Calculos;

import java.util.ArrayList;

public class ClickVertice implements View.OnTouchListener {
    float dX, dY;
    private PointF pontoCentralVertice;
    private PointF pontoToqueNaTela;
    private boolean mover;
    private SingletonFerramentas ferramentas;
    private SingletonFacade facade;
    private Calculos calculos;

    public ClickVertice() {
        ferramentas = SingletonFerramentas.getInstancia();
        facade = SingletonFacade.getInstancia();
        calculos = new Calculos();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Vertice vertice = (Vertice) v;
        if (ferramentas.getEstado() == 4) {
            facade.removerVertice(vertice);
        }
        else {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    pontoCentralVertice = new PointF(v.getX() + vertice.getMetadeTamanhoVertice(), v.getY() + vertice.getMetadeTamanhoVertice());
                    dX = v.getX() - (event.getRawX()) / facade.getGrafoLayout().getScaleX();
                    dY = v.getY() - (event.getRawY()) / facade.getGrafoLayout().getScaleY();
                    mover = false;
                    break;

                case MotionEvent.ACTION_MOVE:
                    pontoToqueNaTela = new PointF(event.getX() + v.getX(), event.getY() + v.getY());
                    if (!calculos.pontoDentroDoCirculo(pontoToqueNaTela, pontoCentralVertice, v.getWidth(), v.getHeight())) {

                        v.animate() // Mover Vertice
                                .x(dX + (event.getRawX() / facade.getGrafoLayout().getScaleX()))
                                .y(dY + (event.getRawY() / facade.getGrafoLayout().getScaleY()))
                                .setDuration(0)
                                .start();

                        mover = true;
                        facade.moverArestas(vertice);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    String mensagem;
                    if (ferramentas.getEstado() == 2) {
                        break;
                    }
                    Vertice verticeSelecionado = facade.getVerticeSelecionado();
                    if (verticeSelecionado == null) {
                        if (!mover) {
                            verticeSelecionado = vertice;
                            verticeSelecionado.setBackgroundResource(R.drawable.vertice_button_pressed);
                            mensagem = "Selecione o vertice final";
                            ferramentas.setupSnackBar(mensagem);
                        }
                    } else {
                        if (!mover && verticeSelecionado != vertice) {
                            if (ferramentas.getEstado() == 3) {
                                verticeSelecionado.setBackgroundResource(R.drawable.vertice_button);
                                facade.criarAresta(verticeSelecionado, vertice);
                                facade.deselecionarVertice();
                                mensagem = "Selecione o vertice inicial";
                                ferramentas.setupSnackBar(mensagem);
                            } else {
                                verticeSelecionado.setBackgroundResource(R.drawable.vertice_button);
                                verticeSelecionado = vertice;
                                verticeSelecionado.setBackgroundResource(R.drawable.vertice_button_pressed);
                            }
                        } else {
                            verticeSelecionado.setBackgroundResource(R.drawable.vertice_button);
                            facade.deselecionarVertice();
                        }
                    }
                    break;
            }
        }
        return false;
    }
}
