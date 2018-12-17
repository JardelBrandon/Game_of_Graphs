package com.example.robotica.grafostudio;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.robotica.grafostudio.utils.Calculos;
import com.example.robotica.grafostudio.utils.Ponto;
import com.example.robotica.grafostudio.utils.ZoomLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompositeSubjectGrafoFragment extends Fragment implements Grafo, Subject, Serializable {
    transient private ArrayList<Grafo> folhasGrafo;
    transient private ArrayList<Vertice> listaVertices;
    transient private ArrayList<Aresta> listaArestas;
    transient private HashMap<Vertice, ArrayList<Vertice>> mapaVerticesAdjacentes;
    private HashMap<Ponto, ArrayList<Ponto>> mapaPontosArquivos;
    transient private ObserverOriginator originator;
    private static ZoomLayout grafoLayout;
    private static Vertice verticeSelecionado;
    private static ThemeFactory themeFactory;
    private boolean direcionado;
    private boolean carregandoGrafo;
    transient private Handler handler;
    private static final long serialVersionUID = -6298516694275121291L;

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
        mapaPontosArquivos = new HashMap<>();

        direcionado = false;
        carregandoGrafo = false;
        handler = new Handler();

        SingletonFacade.setGrafoFragment(this);
        cadastrarObservador(new ObserverMatrizAdjacencias());
        originator = new ObserverOriginator();
        cadastrarObservador(originator);
    }

    @Override
    public void onPause() {
        super.onPause();
        removerVisualizacoes();
    }

    public void removerVisualizacoes() {
        grafoLayout.removeAllViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        restaurarVisualizacoes();
        SingletonFacade.setGrafoFragment(this);
    }

    public void restaurarVisualizacoes() {
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
        notificar();
    }

    public void carregarGrafo(final HashMap<Ponto, ArrayList<Ponto>> mapaDePontos) {
        reiniciarGrafo();
        carregandoGrafo = true;
        for (Ponto pontoVertice : mapaDePontos.keySet()) {
            criarVertice(pontoVertice);
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                for (Vertice verticeInicial : listaVertices) {
                    int metadeTamanhoVertice = verticeInicial.getMetadeTamanhoVertice();
                    for (Ponto pontoInicial : mapaDePontos.keySet()) {

                        if (verticeInicial.getX() == pontoInicial.x - metadeTamanhoVertice && verticeInicial.getY() == pontoInicial.y - metadeTamanhoVertice) {
                            for (Vertice verticeFinal : listaVertices) {
                                for (Ponto pontoFinal : mapaDePontos.get(pontoInicial)) {
                                    if (verticeFinal.getX() == pontoFinal.x - metadeTamanhoVertice && verticeFinal.getY() == pontoFinal.y - metadeTamanhoVertice) {
                                        criarAresta(verticeInicial, verticeFinal);
                                    }
                                }
                            }
                        }
                    }
                }
                carregandoGrafo = false;
            }
        });
    }

    private void reiniciarGrafo() {
        folhasGrafo = new ArrayList<>();
        listaVertices = new ArrayList<>();
        listaArestas = new ArrayList<>();
        mapaVerticesAdjacentes = new HashMap<>();
        mapaPontosArquivos = new HashMap<>();
        grafoLayout.removeAllViews();
    }

    public void salvarPontos() {
        mapaPontosArquivos = new HashMap<>();
        for (Vertice vertice : mapaVerticesAdjacentes.keySet()) {
            int metadeTamanhoVertice = vertice.getMetadeTamanhoVertice();
            Ponto ponto = new Ponto(vertice.getX() + metadeTamanhoVertice, vertice.getY() + metadeTamanhoVertice);
            mapaPontosArquivos.put(ponto, new ArrayList());
            for (Vertice verticeAdjacente : mapaVerticesAdjacentes.get(vertice)) {
                ArrayList pontoAdajacente = mapaPontosArquivos.get(ponto);
                pontoAdajacente.add(new Ponto(verticeAdjacente.getX() + metadeTamanhoVertice, verticeAdjacente.getY() + metadeTamanhoVertice));
                mapaPontosArquivos.put(ponto, pontoAdajacente);
            }
        }
    }

    public void criarVertice(final Ponto ponto) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Vertice vertice = themeFactory.criarVertice(SingletonFacade.getMainActivity(), ponto);
                addElemento((Grafo) vertice);
            }
        });
    }

    public void criarAresta(final Vertice verticeInicial, final Vertice verticeFinal) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Aresta aresta = themeFactory.criarAresta(SingletonFacade.getMainActivity(), verticeInicial, verticeFinal);
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
            mapaVerticesAdjacentes.put(aresta.getVerticeInicial(), verticeInicialListaAdjacentes);
        }
        else {
            verticeInicialListaAdjacentes.add(aresta.getVerticeFinal());
            mapaVerticesAdjacentes.put(aresta.getVerticeInicial(), verticeInicialListaAdjacentes);

            verticeFinalListaAdjacentes.add(aresta.getVerticeInicial());
            mapaVerticesAdjacentes.put(aresta.getVerticeFinal(), verticeFinalListaAdjacentes);
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

    public ArrayList<Vertice> getListaVertices() {
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

    public ObserverOriginator getOriginator() {
        return originator;
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

    public HashMap<Ponto, ArrayList<Ponto>> getMapaPontosArquivos() {
        return mapaPontosArquivos;
    }

    public void setMapaPontosArquivos(HashMap<Ponto, ArrayList<Ponto>> mapaPontosArquivos) {
        this.mapaPontosArquivos = mapaPontosArquivos;
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
            if (observer instanceof ObserverOriginator) {
                if (carregandoGrafo) {
                    continue;
                }
            }
            observer.atualizar(this);
        }
    }

    public void rodarAlgoritmos(int algoritmo, Vertice verticeInicial, Vertice verticeFinal) {
        for (Vertice  vertice : mapaVerticesAdjacentes.keySet()) {
            vertice.setVisitado(false);
            vertice.deselecionar();
        }

        ArrayList<Vertice> verticesCaminho = null;

        if (algoritmo == 0) {
            verticesCaminho = buscaEmProfundidade(verticeInicial, verticeFinal, new ArrayList<Vertice>());
        }
        else if (algoritmo == 1) {
            verticesCaminho = buscaEmLargura(verticeInicial, verticeFinal);
        }
        else if (algoritmo == 2) {
            verticesCaminho = buscaAEstrela(verticeInicial, verticeFinal, new ArrayList<Vertice>());
        }
        colorirCaminho(verticesCaminho, verticeInicial, verticeFinal);
    }

    private void colorirCaminho(ArrayList<Vertice> caminho, Vertice verticeInicial, Vertice verticeFinal) {
        if (caminho != null) {
            for (Vertice vertice : caminho) {
                vertice.selecionar();
            }
            verticeInicial.setBackgroundResource(R.drawable.vertice_inicial);
            if (verticeFinal != null) {
                verticeFinal.setBackgroundResource(R.drawable.vertice_final);
            }
        }
    }

    private ArrayList<Vertice> buscaEmProfundidade(Vertice verticeInicial, Vertice verticeFinal, ArrayList<Vertice> caminho) {
        caminho.add(verticeInicial);
        if (verticeInicial == verticeFinal) {
            return caminho;
        }
        for (Vertice  vertice : mapaVerticesAdjacentes.get(verticeInicial)) {
            if (!caminho.contains(vertice)) {
                ArrayList<Vertice> verticesCaminho = buscaEmProfundidade(vertice, verticeFinal, caminho);
                if (verticesCaminho != null) {
                    return verticesCaminho;
                }
            }
        }
        return null;
    }

    private ArrayList<Vertice> buscaEmLargura(Vertice verticeInicial, Vertice verticeFinal) {
        ArrayList<Vertice> filaVertices = new ArrayList();
        ArrayList<Vertice> caminho = new ArrayList<>();
        verticeInicial.setVisitado(true);
        filaVertices.add(verticeInicial);
        while(!filaVertices.isEmpty()){
            Vertice verticeInicialDaFila = filaVertices.get(0);
            caminho.add(verticeInicialDaFila);
            if(verticeInicialDaFila == verticeFinal){
                break;
            }else{
                for(Vertice v: mapaVerticesAdjacentes.get(verticeInicialDaFila)){
                    if(!v.isVisitado()){
                        v.setVisitado(true);
                        filaVertices.add(v);
                    }
                }
            }
            filaVertices.remove(verticeInicialDaFila);
        }
        return caminho;
    }

    private ArrayList<Vertice> buscaAEstrela (Vertice verticeInicial, Vertice verticeFinal, ArrayList<Vertice> caminho){
        if(caminho.isEmpty()){
            caminho.add(verticeInicial);
        }
        if(verticeInicial == verticeFinal){
            return caminho;
        }

        Vertice proximo = heuristica(verticeInicial, verticeFinal);
        caminho.add(proximo);
        return buscaAEstrela(proximo, verticeFinal, caminho);
    }

    private Vertice heuristica(Vertice verticeAtual, Vertice verticeFinal) {
        verticeAtual.setVisitado(true);
        Vertice proximoVertice = verticeAtual;

        float menorDistancia = Float.POSITIVE_INFINITY;

        for(Aresta aresta : listaArestas) {
            if (aresta.getVerticeInicial() == verticeAtual) {
                Vertice verticeAdjacente = aresta.getVerticeFinal();
                if (!verticeAdjacente.isVisitado()) {
                    float distancia = Calculos.getDistanciaVertices(verticeAdjacente, verticeFinal);
                    if (aresta.getPeso() + distancia < menorDistancia) {
                        menorDistancia = aresta.getPeso() + distancia;
                        proximoVertice = verticeAdjacente;
                    }
                }
            }
            else if (aresta.getVerticeFinal() == verticeAtual) {
                Vertice verticeAdjacente = aresta.getVerticeInicial();
                if (!verticeAdjacente.isVisitado()) {
                    float distancia = Calculos.getDistanciaVertices(verticeAdjacente, verticeFinal);
                    if (aresta.getPeso() + distancia < menorDistancia) {
                        menorDistancia = aresta.getPeso() + distancia;
                        proximoVertice = verticeAdjacente;
                    }
                }
            }
        }
        return proximoVertice;
    }
}
