package com.example.robotica.grafostudio;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.robotica.grafostudio.utils.ZoomLayout;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompositeSubjectGrafoFragment extends Fragment implements Grafo, Subject {
    private static ArrayList<Grafo> folhasGrafo;
    private static ArrayList<Vertice> listaVertices;
    private static ArrayList<Aresta> listaArestas;
    private static HashMap<Vertice, ArrayList<Vertice>> mapaVerticesAdjacentes;
    private static ZoomLayout grafoLayout;
    private static Vertice verticeSelecionado;
    private static ThemeFactory themeFactory;
    private static boolean direcionado;
    private Handler handler;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    public CompositeSubjectGrafoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        init();
    }

    private void init() {
        folhasGrafo = new ArrayList<>();
        listaVertices = new ArrayList<>();
        listaArestas = new ArrayList<>();
        mapaVerticesAdjacentes = new HashMap<>();

        direcionado = false;
        handler = new Handler();
        SingletonFacade.setGrafoFragment(this);
        cadastrarObservador(new ObserverMatrizAdjacencias());
    }

    @Override
    public void onPause() {
        super.onPause();
        grafoLayout.removeAllViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Vertice vertice : listaVertices) {
            grafoLayout.addView((View) vertice);
        }
        for (Aresta aresta : listaArestas) {
            grafoLayout.addView((View) aresta);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grafo, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        grafoLayout = view.findViewById(R.id.grafoLayout);
        grafoLayout.setOnTouchListener(new ClickTela());
    }

    public void criarVertice(final PointF ponto) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Vertice vertice = themeFactory.criarVertice(getActivity(), ponto);
                addElemento((Grafo) vertice);
            }
        });
    }

    public void criarAresta(final Vertice verticeInicial, final Vertice verticeFinal) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Aresta aresta = themeFactory.criarAresta(getActivity(), verticeInicial, verticeFinal);
                addElemento((Grafo) aresta);
            }
        });
    }

    public void addElemento(Grafo elemento) {
        folhasGrafo.add(elemento);
        grafoLayout.addView((View) elemento);
        if (elemento instanceof Vertice) {
            addVertice((Vertice) elemento);
            moverViewParaBaixo((View) elemento);
        }
        else if (elemento instanceof Aresta) {
            addAresta((Aresta) elemento);
            moverViewParaBaixo((View) ((Aresta) elemento).getVerticeInicial());
            moverViewParaBaixo((View) ((Aresta) elemento).getVerticeFinal());
        }
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
            removerElemento((Grafo) aresta);
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

    public void moverViewParaBaixo(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
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

    public static ArrayList<Vertice> getListaVertices() {
        return listaVertices;
    }

    public void setListaVertices(ArrayList<Vertice> listaVertices) {
        this.listaVertices = listaVertices;
    }

    public ArrayList<Aresta> getListaArestas() {
        return listaArestas;
    }

    public void setListaArestas(ArrayList<Aresta> listaAresta) {
        this.listaArestas = listaAresta;
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

    public static ThemeFactory getThemeFactory() {
        return themeFactory;
    }

    public static void setThemeFactory(ThemeFactory themeFactory) {
        CompositeSubjectGrafoFragment.themeFactory = themeFactory;
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
}
