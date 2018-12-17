package com.example.robotica.grafostudio;

import android.view.View;

public class ClickVerticeRomenia implements View.OnClickListener {
    private SingletonFacade facade;

    public ClickVerticeRomenia() {
        facade = SingletonFacade.getInstancia();
    }

    @Override
    public void onClick(View view) {
        Vertice verticeSelecionado = facade.getGrafoFragment().getVerticeSelecionado();
        Vertice vertice = (Vertice) view;
        String mensagem;
        if (verticeSelecionado == null) {
            facade.selecionarVertice(vertice);
            mensagem = "Selecione o vertice final";
            facade.snackBar(mensagem);
        } else {
            if (verticeSelecionado != vertice) {
                facade.deselecionarVertice();
                facade.rodarAlgoritmos(facade.getAlgoritmo(), verticeSelecionado, vertice);
                mensagem = "Rodar Algoritmo";
                facade.snackBar(mensagem);
            } else {
                facade.deselecionarVertice();
            }
        }
    }
}
