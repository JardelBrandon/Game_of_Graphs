package com.example.jarde.heros_graphs;

import java.util.ArrayList;
import java.util.HashMap;

public class MatrizAdjacencias {
    private ArrayList<Vertice> listaVertices = new ArrayList<>();
    private ArrayList<Aresta> listaArestas = new ArrayList<>();
    private HashMap<Vertice, ArrayList> verticesAdjacentes = new HashMap<>();

    public ArrayList<Vertice> getListaVertices() {
        return listaVertices;
    }

    public ArrayList<Aresta> getListaArestas() {
        return listaArestas;
    }

    public HashMap<Vertice, ArrayList> getVerticesAdjacentes() {
        return verticesAdjacentes;
    }

    public void adicionarVertice(Vertice vertice) {
        listaVertices.add(vertice);
        verticesAdjacentes.put(vertice, null);

    }

    public void adicionarAresta(Aresta aresta, boolean direcionado) {
        listaArestas.add(aresta);
        ArrayList verticeInicialListaAdjacentes = verticesAdjacentes.get(aresta.getVerticeInicial());
        ArrayList verticeFinalListaAdjacentes = verticesAdjacentes.get(aresta.getVerticeFinal());
        if (direcionado) {
            verticeInicialListaAdjacentes.add(aresta.getVerticeFinal());
            verticesAdjacentes.put(aresta.getVerticeInicial(),verticeInicialListaAdjacentes);
        }
        else {
            verticeInicialListaAdjacentes.add(aresta.getVerticeFinal());
            verticesAdjacentes.put(aresta.getVerticeInicial(),verticeInicialListaAdjacentes);
            verticeFinalListaAdjacentes.add(aresta.getVerticeFinal());
            verticesAdjacentes.put(aresta.getVerticeFinal(),verticeFinalListaAdjacentes);
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
        for (Vertice vertice : verticesAdjacentes.keySet()) {
            matrizAdjacencias += vertice.getText();
            matrizAdjacencias += ' ';
        }
        matrizAdjacencias += '\n';

        for (Vertice vertice : verticesAdjacentes.keySet()) {
            matrizAdjacencias += vertice.getText();


        }
        return super.toString();
    }
    */
}
