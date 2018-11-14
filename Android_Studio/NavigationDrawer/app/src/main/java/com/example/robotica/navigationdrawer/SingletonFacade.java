package com.example.robotica.navigationdrawer;

import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import com.example.robotica.navigationdrawer.utils.Calculos;
import com.example.robotica.navigationdrawer.utils.ZoomLayout;

public class SingletonFacade {
    private static SingletonFacade instancia = null;
    private static CompositeSubjectGrafoFragment grafoFragment;
    private PrototypeFactory prototypeFactory;
    private Calculos calculos;
    private SingletonFerramentas ferramentas;

    private SingletonFacade() {
        init();
    }

    private void init() {
        calculos = new Calculos();
        grafoFragment = new CompositeSubjectGrafoFragment();
        prototypeFactory = new PrototypeFactory(CompositeSubjectGrafoFragment.getGrafoLayout().getContext());
        ferramentas = SingletonFerramentas.getInstancia();
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
        vertice.setText(String.valueOf(grafoFragment.getListaVertices().size()));
        vertice.setId(grafoFragment.getListaVertices().size());

        vertice.setOnTouchListener(new ClickVertice());

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
        aresta.setId(grafoFragment.getListaArestas().size());

        grafoFragment.addElemento(aresta);
        moverViewParaBaixo(verticeInicial);
        moverViewParaBaixo(verticeFinal);

    }

    public void setEstadoFerramentas(int estadoFerramentas) {
        deselecionarVertice();
        ferramentas.setEstado(estadoFerramentas);
    }

    public int getEstadoFerramentas() {
        return ferramentas.getEstado();
    }

    public void snackBar(String mensagem) {
        ferramentas.setupSnackBar(mensagem);
    }

    public void removerVertice(Vertice vertice) {
        grafoFragment.removerElemento(vertice);
    }

    public void removerAresta(Aresta aresta) {
        grafoFragment.removerElemento(aresta);
    }

    public void selecionarVertice(Vertice vertice) {
        grafoFragment.getVerticeSelecionado().setBackgroundResource(R.drawable.vertice_button_pressed);
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

    public static ZoomLayout getGrafoLayout() {
        return grafoFragment.getGrafoLayout();
    }

    private static void moverViewParaBaixo(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }


}
