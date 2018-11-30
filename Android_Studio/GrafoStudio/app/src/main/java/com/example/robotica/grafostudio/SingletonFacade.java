package com.example.robotica.grafostudio;

import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import com.example.robotica.grafostudio.utils.ZoomLayout;

public class SingletonFacade {
    private static SingletonFacade instancia = null;
    private static CompositeSubjectGrafoFragment grafoFragment;
    private SingletonFerramentas ferramentas;

    private SingletonFacade() {
        init();
    }

    private void init() {
        ferramentas = SingletonFerramentas.getInstancia();
    }

    public static SingletonFacade getInstancia() {
        if (instancia == null) {
            instancia = new SingletonFacade();
        }
        return instancia;
    }

    public void criarVertice(PointF ponto) {
        grafoFragment.criarVertice(ponto);
    }

    public void criarAresta(Vertice verticeInicial, Vertice verticeFinal) {
        grafoFragment.criarAresta(verticeInicial, verticeFinal);
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
        grafoFragment.removerElemento((Grafo) vertice);
    }

    public void removerAresta(Aresta aresta) {
        grafoFragment.removerElemento((Grafo) aresta);
    }

    public void selecionarVertice(Vertice vertice) {
        if (grafoFragment.getVerticeSelecionado() != null) {
            deselecionarVertice();
        }
        vertice.selecionar();
        grafoFragment.setVerticeSelecionado(vertice);
    }

    public void deselecionarVertice() {
        if (grafoFragment.getVerticeSelecionado() != null) {
            grafoFragment.getVerticeSelecionado().deselecionar();
            grafoFragment.setVerticeSelecionado(null);
        }
    }

    public static CompositeSubjectGrafoFragment getGrafoFragment() {
        return grafoFragment;
    }

    public static void setGrafoFragment(CompositeSubjectGrafoFragment grafoFragment) {
        SingletonFacade.grafoFragment = grafoFragment;
    }

    public static ZoomLayout getGrafoLayout() {
        return grafoFragment.getGrafoLayout();
    }

}
