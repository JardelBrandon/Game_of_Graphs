package com.example.jarde.heros_graphs;

import java.util.ArrayList;
import java.util.HashMap;

public class MatrizAdjacencias {
    private ArrayList<Vertice> listaVertices = new ArrayList<>();
    private ArrayList<Aresta> listaArestas = new ArrayList<>();
    private HashMap<Vertice, ArrayList> mapaVerticesAdjacentes = new HashMap<>();

    public ArrayList<Vertice> getListaVertices() {
        return listaVertices;
    }

    public ArrayList<Aresta> getListaArestas() {
        return listaArestas;
    }

    public HashMap<Vertice, ArrayList> getmapaVerticesAdjacentes() {
        return mapaVerticesAdjacentes;
    }

    public void adicionarVertice(Vertice vertice) {
        listaVertices.add(vertice);
        mapaVerticesAdjacentes.put(vertice, new ArrayList());

    }

    public void adicionarAresta(Aresta aresta, boolean direcionado) {
        listaArestas.add(aresta);
        ArrayList verticeInicialListaAdjacentes = mapaVerticesAdjacentes.get(aresta.getVerticeInicial());
        ArrayList verticeFinalListaAdjacentes = mapaVerticesAdjacentes.get(aresta.getVerticeFinal());
        if (direcionado) {
            verticeInicialListaAdjacentes.add(aresta.getVerticeFinal());
            mapaVerticesAdjacentes.put(aresta.getVerticeInicial(),verticeInicialListaAdjacentes);
        }
        else {
            verticeInicialListaAdjacentes.add(aresta.getVerticeInicial());
            mapaVerticesAdjacentes.put(aresta.getVerticeInicial(),verticeInicialListaAdjacentes);
            verticeFinalListaAdjacentes.add(aresta.getVerticeFinal());
            mapaVerticesAdjacentes.put(aresta.getVerticeFinal(),verticeFinalListaAdjacentes);
        }
    }

    public int getQuantidadeVertices() {
        return listaVertices.size();
    }

    public int getQuantidadeArestas() {
        return listaArestas.size();
    }
    /*
    @Override
    public String toString() {
        String matrizAdjacencias = null;
        for (Vertice vertice : mapaVerticesAdjacentes.keySet()) {
            matrizAdjacencias += vertice.getText();
            matrizAdjacencias += ' ';
        }
        matrizAdjacencias += '\n';

        for (Vertice vertice : mapaVerticesAdjacentes.keySet()) {
            matrizAdjacencias += vertice.getText();


        }
        return super.toString();
    }
    */
}
