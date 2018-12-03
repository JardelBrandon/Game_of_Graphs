package com.example.robotica.grafostudio;

import android.content.Context;
import android.graphics.PointF;
import android.view.ViewGroup;

import com.example.robotica.grafostudio.utils.Calculos;
import com.example.robotica.grafostudio.utils.Ponto;

public class LightTheme extends ThemeFactory {
    ViewGroup.LayoutParams verticeParams;
    ViewGroup.LayoutParams arestaParams;
    private Calculos calculos;
    private SingletonFacade facade;

    public LightTheme() {
        this.calculos = new Calculos();
        facade = SingletonFacade.getInstancia();
    }

    @Override
    public Vertice criarVertice(Context contexto, Ponto ponto) {
        final VerticeLight verticeLight = new VerticeLight(contexto);
        int tamanhoVertice = verticeLight.getTamanhoVertice();
        verticeParams = new ViewGroup.LayoutParams(tamanhoVertice, tamanhoVertice);
        verticeLight.setLayoutParams(verticeParams);
        verticeLight.setX(ponto.x - verticeLight.getMetadeTamanhoVertice());
        verticeLight.setY(ponto.y - verticeLight.getMetadeTamanhoVertice());
        verticeLight.setText(String.valueOf(facade.getQuantidadeDeVertices()));

        verticeLight.setOnTouchListener(new ClickVertice());

        return verticeLight;
    }

    @Override
    public Aresta criarAresta(Context contexto, Vertice verticeInicial, Vertice verticeFinal) {
        final ArestaLight arestaLight = new ArestaLight(contexto);
        arestaParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        arestaLight.setLayoutParams(arestaParams);

        arestaLight.setVerticeInicial(verticeInicial);
        arestaLight.setVerticeFinal(verticeFinal);
        arestaLight.setPontoInicial(calculos.getPontoInterseccaoAresta(arestaLight).get(0));
        arestaLight.setPontoFinal(calculos.getPontoInterseccaoAresta(arestaLight).get(1));

        return arestaLight;
    }
}
