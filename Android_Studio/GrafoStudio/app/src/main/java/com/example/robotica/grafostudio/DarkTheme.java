package com.example.robotica.grafostudio;

import android.content.Context;
import android.graphics.PointF;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.robotica.grafostudio.utils.Calculos;
import com.example.robotica.grafostudio.utils.Ponto;

public class DarkTheme extends ThemeFactory {
    FrameLayout.LayoutParams verticeParams;
    FrameLayout.LayoutParams arestaParams;
    private Calculos calculos;
    private SingletonFacade facade;

    public DarkTheme() {
        calculos = new Calculos();
        facade = SingletonFacade.getInstancia();
    }

    @Override
    public Vertice criarVertice(Context contexto, Ponto ponto) {
        final VerticeDark verticeDark = new VerticeDark(contexto);
        if (facade.getGrafoLayout().getId() == R.id.grafo_layout_romenia) {
            verticeDark.setTamanhoVertice(verticeDark.getTamanhoVertice() / 2);
            verticeDark.setOnClickListener(new View.OnClickListener() {
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
            });
        }
        else {
            verticeDark.setOnTouchListener(new ClickVertice());
        }
        int tamanhoVertice = verticeDark.getTamanhoVertice();
        verticeParams = new FrameLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
        verticeParams.gravity = Gravity.LEFT;
        verticeDark.setLayoutParams(verticeParams);
        verticeDark.setX(ponto.x - verticeDark.getMetadeTamanhoVertice());
        verticeDark.setY(ponto.y - verticeDark.getMetadeTamanhoVertice());
        verticeDark.setText(String.valueOf(facade.getQuantidadeDeVertices()));

        return verticeDark;
    }

    @Override
    public Aresta criarAresta(Context contexto, Vertice verticeInicial, Vertice verticeFinal) {
        final ArestaDark arestaDark = new ArestaDark(contexto);
        arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        arestaParams.gravity = Gravity.LEFT;
        arestaDark.setLayoutParams(arestaParams);

        arestaDark.setVerticeInicial(verticeInicial);
        arestaDark.setVerticeFinal(verticeFinal);
        arestaDark.setPontoInicial(calculos.getPontoInterseccaoAresta(arestaDark).get(0));
        arestaDark.setPontoFinal(calculos.getPontoInterseccaoAresta(arestaDark).get(1));

        return arestaDark;
    }
}
