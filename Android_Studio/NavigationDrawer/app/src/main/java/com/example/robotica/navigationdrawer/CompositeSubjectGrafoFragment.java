package com.example.robotica.navigationdrawer;


import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.robotica.navigationdrawer.utils.ZoomLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class CompositeSubjectGrafoFragment extends Fragment implements Grafo, Subject {
    private ArrayList<Grafo> folhasGrafo;
    private ArrayList<Vertice> listaVertices = new ArrayList<>();
    private ArrayList<Aresta> listaArestas = new ArrayList<>();
    private HashMap<Vertice, ArrayList<Vertice>> mapaVerticesAdjacentes = new HashMap<>();
    private static ZoomLayout grafoLayout;
    private Vertice verticeSelecionado;
    private boolean direcionado;

    public CompositeSubjectGrafoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        grafoLayout = grafoLayout.findViewById(R.id.grafoLayout);
        grafoLayout.setOnTouchListener(new ClickTela());
        direcionado = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafo, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void addElemento(Grafo elemento) {
        folhasGrafo.add(elemento);
        if (elemento instanceof Vertice) {
            addVertice((Vertice) elemento);
        }
        else if (elemento instanceof Aresta) {
            addAresta((Aresta) elemento);
        }
        grafoLayout.addView((View) elemento);
        notificar();
    }

    private void addVertice(Vertice vertice) {
        listaVertices.add(vertice);
        mapaVerticesAdjacentes.put(vertice, new ArrayList());
    }

    private void addAresta(Aresta aresta) {
        listaArestas.add(aresta);
        ArrayList verticeInicialListaAdjacentes = mapaVerticesAdjacentes.get(aresta.getVerticeInicial());
        ArrayList verticeFinalListaAdjacentes = mapaVerticesAdjacentes.get(aresta.getVerticeFinal());
        if (direcionado) {
            verticeInicialListaAdjacentes.add(aresta.getVerticeFinal());
            mapaVerticesAdjacentes.put(aresta.getVerticeInicial(),verticeInicialListaAdjacentes);
        }
        else {
            verticeInicialListaAdjacentes.add(aresta.getVerticeFinal());
            mapaVerticesAdjacentes.put(aresta.getVerticeInicial(),verticeInicialListaAdjacentes);
            verticeFinalListaAdjacentes.add(aresta.getVerticeInicial());
            mapaVerticesAdjacentes.put(aresta.getVerticeFinal(),verticeFinalListaAdjacentes);
        }
    }

    public void removerElemento(Grafo elemento) {
        if (elemento instanceof Vertice) {
            removerVertice((Vertice) elemento);
        }
        else if (elemento instanceof Aresta) {
            removerAresta((Aresta) elemento);
        }

        folhasGrafo.remove(elemento);
        grafoLayout.removeView((View) elemento);
        notificar();
    }

    private void removerVertice(Vertice vertice) {
        ArrayList<Aresta> arestasConectadas = new ArrayList<>();
        for (Aresta aresta : listaArestas) {
            if (aresta.getVerticeInicial() == vertice) {
                arestasConectadas.add(aresta);
            }
            else if(aresta.getVerticeFinal() == vertice) {
                arestasConectadas.add(aresta);
            }
        }
        for (Aresta aresta : arestasConectadas) {
            removerElemento(aresta);
        }

        listaVertices.remove(vertice);
        mapaVerticesAdjacentes.remove(vertice);
    }

    private void removerAresta(Aresta aresta) {
        if (mapaVerticesAdjacentes.get(aresta.getVerticeInicial()).contains(aresta.getVerticeFinal())) {
            mapaVerticesAdjacentes.get(aresta.getVerticeInicial()).remove(aresta.getVerticeFinal());
        }
        if (mapaVerticesAdjacentes.get(aresta.getVerticeFinal()).contains(aresta.getVerticeInicial())) {
            mapaVerticesAdjacentes.get(aresta.getVerticeFinal()).remove(aresta.getVerticeInicial());
        }
        listaArestas.remove(aresta);
    }

    public boolean isDirecionado() {
        return direcionado;
    }

    public void setDirecionado(boolean direcionado) {
        this.direcionado = direcionado;
    }

    public ArrayList<Grafo> getFolhasGrafo() {
        return folhasGrafo;
    }

    public void setFolhasGrafo(ArrayList<Grafo> folhasGrafo) {
        this.folhasGrafo = folhasGrafo;
    }

    public ArrayList<Vertice> getListaVertices() {
        return listaVertices;
    }

    public void setListaVertices(ArrayList<Vertice> listaVertices) {
        this.listaVertices = listaVertices;
    }

    public ArrayList<Aresta> getListaArestas() {
        return listaArestas;
    }

    public void setListaArestas(ArrayList<Aresta> listaArestas) {
        this.listaArestas = listaArestas;
    }

    public Vertice getVerticeSelecionado() {
        return verticeSelecionado;
    }

    public void setVerticeSelecionado(Vertice verticeSelecionado) {
        this.verticeSelecionado = verticeSelecionado;
    }

    public void getElemento(int i) {
        folhasGrafo.get(i);
    }

    public static ZoomLayout getGrafoLayout() {
        return grafoLayout;
    }

    public HashMap<Vertice, ArrayList<Vertice>> getMapaVerticesAdjacentes() {
        return mapaVerticesAdjacentes;
    }

    public void setMapaVerticesAdjacentes(HashMap<Vertice, ArrayList<Vertice>> mapaVerticesAdjacentes) {
        this.mapaVerticesAdjacentes = mapaVerticesAdjacentes;
    }

    public ArrayList<Grafo> getGrafo() {
        return folhasGrafo;
    }

    @Override
    public void cadastrarObservador(Observer observer) {
        OBSERVERS.add(observer);
    }

    @Override
    public void removerObservador(Observer observer) {
        OBSERVERS.remove(observer);
    }

    @Override
    public void notificar() {
        for (Observer observer : OBSERVERS) {
            observer.atualizar(this);
        }
    }

    @Override
    public Object clone() {
        return null;
    }
}
