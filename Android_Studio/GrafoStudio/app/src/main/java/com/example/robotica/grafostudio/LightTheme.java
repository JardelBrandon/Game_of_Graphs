package com.example.robotica.grafostudio;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.robotica.grafostudio.utils.Calculos;
import com.example.robotica.grafostudio.utils.Ponto;

public class LightTheme extends ThemeFactory {
    FrameLayout.LayoutParams verticeParams;
    FrameLayout.LayoutParams arestaParams;
    private Calculos calculos;
    private SingletonFacade facade;

    public LightTheme() {
        this.calculos = new Calculos();
        facade = SingletonFacade.getInstancia();
    }

    @Override
    public Vertice criarVertice(Context contexto, Ponto ponto) {
        final VerticeLight verticeLight = new VerticeLight(contexto);
        if (facade.getGrafoLayout().getId() == R.id.grafo_layout_romenia) {
            verticeLight.setTamanhoVertice(verticeLight.getTamanhoVertice() / 2);
            verticeLight.setOnClickListener(new ClickVerticeRomenia());
        }
        else {
            verticeLight.setOnTouchListener(new ClickVertice());
        }
        int tamanhoVertice = verticeLight.getTamanhoVertice();

        verticeParams = new FrameLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
        verticeParams.gravity = Gravity.LEFT;
        verticeLight.setLayoutParams(verticeParams);
        verticeLight.setX(ponto.x - verticeLight.getMetadeTamanhoVertice());
        verticeLight.setY(ponto.y - verticeLight.getMetadeTamanhoVertice());
        verticeLight.setText(String.valueOf(facade.getQuantidadeDeVertices()));

        return verticeLight;
    }

    @Override
    public Aresta criarAresta(Context contexto, Vertice verticeInicial, Vertice verticeFinal) {
        final ArestaLight arestaLight = new ArestaLight(contexto);
        arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        arestaParams.gravity = Gravity.LEFT;
        arestaLight.setLayoutParams(arestaParams);

        arestaLight.setVerticeInicial(verticeInicial);
        arestaLight.setVerticeFinal(verticeFinal);
        arestaLight.setPontoInicial(calculos.getPontoInterseccaoAresta(arestaLight).get(0));
        arestaLight.setPontoFinal(calculos.getPontoInterseccaoAresta(arestaLight).get(1));

        return arestaLight;
    }
}
