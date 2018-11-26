package com.example.robotica.navigationdrawer;

import android.content.Context;
import android.graphics.PointF;
import android.view.ViewGroup;

import com.example.robotica.navigationdrawer.utils.Calculos;

public class DarkTheme extends ThemeFactory {
    ViewGroup.LayoutParams verticeParams;
    ViewGroup.LayoutParams arestaParams;
    private Calculos calculos;

    public DarkTheme() {
        calculos = new Calculos();
    }

    @Override
    public Vertice criarVertice(Context contexto, PointF ponto) {
        final VerticeDark verticeDark = new VerticeDark(contexto);
        int tamanhoVertice = verticeDark.getTamanhoVertice();
        verticeParams = new ViewGroup.LayoutParams(tamanhoVertice, tamanhoVertice);
        verticeDark.setLayoutParams(verticeParams);
        verticeDark.setX(ponto.x - verticeDark.getMetadeTamanhoVertice());
        verticeDark.setY(ponto.y - verticeDark.getMetadeTamanhoVertice());
        verticeDark.setText(String.valueOf(CompositeSubjectGrafoFragment.getListaVertices().size()));

        verticeDark.setOnTouchListener(new ClickVertice());

        //verticeParams.setMargins(posicaoX - metadeTamanhoVertice, posicaoY - metadeTamanhoVertice, 0, 0);
        return verticeDark;
    }

    @Override
    public Aresta criarAresta(Context contexto, Vertice verticeInicial, Vertice verticeFinal) {
        final ArestaDark arestaDark = new ArestaDark(contexto);
        arestaParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        arestaDark.setLayoutParams(arestaParams);

        arestaDark.setVerticeInicial(verticeInicial);
        arestaDark.setVerticeFinal(verticeFinal);
        arestaDark.setPointA(calculos.getPontoInterseccaoAresta(arestaDark).get(0));
        arestaDark.setPointB(calculos.getPontoInterseccaoAresta(arestaDark).get(1));

        return arestaDark;
    }
}
