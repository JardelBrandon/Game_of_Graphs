package com.example.robotica.navigationdrawer;

import android.graphics.PointF;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.robotica.navigationdrawer.utils.Calculos;
import com.example.robotica.navigationdrawer.utils.ZoomLayout;

import java.util.List;

public class SingletonFacade {
    private static SingletonFacade instancia = null;
    private PrototypeFactory prototypeFactory;
    private CompositeSubjectGrafoFragment grafoFragment;
    private Calculos calculos;
    private static int selecaoFerramentas;
    private FrameLayout.LayoutParams verticeParams;
    private FrameLayout.LayoutParams arestaParams;
    private PointF pointCentroA;
    private PointF pointCentroB;
    private ObserverMatrizAdjacencias observerMatrizAdjacencias;
    private Handler handler;

    private SingletonFacade() {
        init();
    }

    private void init() {
        observerMatrizAdjacencias = new ObserverMatrizAdjacencias();
        handler = new Handler();
        calculos = new Calculos();
        grafoFragment = new CompositeSubjectGrafoFragment();
        prototypeFactory = new PrototypeFactory(grafoFragment.getActivity());
    }

    public static SingletonFacade getInstancia() {
        if (instancia == null) {
            instancia = new SingletonFacade();
        }
        return instancia;
    }

    public void criarVertice(PointF ponto) {
        final Vertice vertice = prototypeFactory.getVerticePrototipo();
        vertice.setX(ponto.x - vertice.getMetadeTamanhoVertice());
        vertice.setY(ponto.y - vertice.getMetadeTamanhoVertice());

        grafoFragment.addElemento(vertice);
        moverViewParaBaixo(vertice);
        //verticeParams.setMargins(posicaoX - metadeTamanhoVertice, posicaoY - metadeTamanhoVertice, 0, 0);
    }

    public void criarAresta(Vertice verticeInicial, Vertice verticeFinal) {
        final Aresta aresta = prototypeFactory.getArestaPrototipo();
        aresta.setPointA(calculos.getPontoInterseccaoAresta(aresta).get(0));
        aresta.setPointB(calculos.getPontoInterseccaoAresta(aresta).get(1));
        aresta.setVerticeInicial(verticeInicial);
        aresta.setVerticeFinal(verticeFinal);

        grafoFragment.addElemento(aresta);
        moverViewParaBaixo(verticeInicial);
        moverViewParaBaixo(verticeFinal);

    }

    public void removerVertice(Vertice vertice) {
        grafoFragment.removerElemento(vertice);
    }

    public void removerAresta(Aresta aresta) {
        grafoFragment.removerElemento(aresta);
    }

    public void selecionarVertice(Vertice vertice) {
        grafoFragment.setVerticeSelecionado(vertice);
    }

    public void deselecionarVertice() {
        if (grafoFragment.getVerticeSelecionado() != null) {
            grafoFragment.getVerticeSelecionado().setBackgroundResource(R.drawable.vertice_button);
            grafoFragment.setVerticeSelecionado(null);
        }
    }

    public CompositeSubjectGrafoFragment getGrafoFragment() {
        return grafoFragment;
    }

    public ZoomLayout getGrafoLayout() {
        return grafoFragment.getGrafoLayout();
    }

    private static void moverViewParaBaixo(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }

    protected void moverArestas(Vertice vertice) { //Mover as arestas ligadas ao vertice passado como argumento
        for (Aresta aresta : observerMatrizAdjacencias.getListaArestas()) {
            if (aresta.getVerticeInicial() == vertice) {
                pointCentroA = new PointF(vertice.getX() + metadeTamanhoVertice, vertice.getY() + metadeTamanhoVertice);
                pointCentroB = new PointF(aresta.getVerticeFinal().getX() + metadeTamanhoVertice, aresta.getVerticeFinal().getY() + metadeTamanhoVertice);
                List interseccaoA = calculos.getPontoInterseccaoVerticeAresta(pointCentroA, pointCentroB, pointCentroA, metadeTamanhoVertice);
                List interseccaoB = calculos.getPontoInterseccaoVerticeAresta(pointCentroA, pointCentroB, pointCentroB, metadeTamanhoVertice);
                arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                aresta.setLayoutParams(arestaParams);
                aresta.setPointA((PointF) interseccaoA.get(1));
                aresta.setPointB((PointF) interseccaoB.get(0));
            }
            else if(aresta.getVerticeFinal() == vertice) {
                pointCentroA = new PointF(aresta.getVerticeInicial().getX() + metadeTamanhoVertice, aresta.getVerticeInicial().getY() + metadeTamanhoVertice);
                pointCentroB = new PointF(vertice.getX() + metadeTamanhoVertice, vertice.getY() + metadeTamanhoVertice);
                List interseccaoA = calculos.getPontoInterseccaoVerticeAresta(pointCentroA, pointCentroB, pointCentroA, metadeTamanhoVertice);
                List interseccaoB = calculos.getPontoInterseccaoVerticeAresta(pointCentroA, pointCentroB, pointCentroB, metadeTamanhoVertice);
                arestaParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                aresta.setLayoutParams(arestaParams);
                aresta.setPointA((PointF) interseccaoA.get(1));
                aresta.setPointB((PointF) interseccaoB.get(0));
            }
        }
    }
}
