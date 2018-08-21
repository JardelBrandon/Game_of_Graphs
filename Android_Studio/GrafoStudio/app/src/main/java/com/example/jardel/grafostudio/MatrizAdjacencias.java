package com.example.jardel.grafostudio;

import java.util.ArrayList;
import java.util.HashMap;

public class MatrizAdjacencias {
    private ArrayList<Vertice> listaVertices = new ArrayList<>();
    private ArrayList<Aresta> listaArestas = new ArrayList<>();
    private HashMap<Vertice, ArrayList> mapaVerticesAdjacentes = new HashMap<>();


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
            verticeInicialListaAdjacentes.add(aresta.getVerticeFinal());
            mapaVerticesAdjacentes.put(aresta.getVerticeInicial(),verticeInicialListaAdjacentes);
            verticeFinalListaAdjacentes.add(aresta.getVerticeInicial());
            mapaVerticesAdjacentes.put(aresta.getVerticeFinal(),verticeFinalListaAdjacentes);
        }
    }

    public int getQuantidadeVertices() {
        return listaVertices.size();
    }

    public int getQuantidadeArestas() {
        return listaArestas.size();
    }

    public ArrayList<Vertice> getListaVertices() {
        return listaVertices;
    }

    public ArrayList<Aresta> getListaArestas() {
        return listaArestas;
    }

    public HashMap<Vertice, ArrayList> getMapaVerticesAdjacentes() {
        return mapaVerticesAdjacentes;
    }

    @Override
    public String toString() {
        String matrizAdjacencias = "Tabela matriz de adjacências entre os vertices \n";
        matrizAdjacencias += "  ";
        for (Vertice vertice : listaVertices) {
            matrizAdjacencias += vertice.getText();
            matrizAdjacencias += ' ';
        }
        matrizAdjacencias += '\n';

        for (Vertice vertice : listaVertices) {
            matrizAdjacencias += vertice.getText();
            for (Vertice verticeVerficarAdjacencia : listaVertices) {
                matrizAdjacencias += ' ';
                if (mapaVerticesAdjacentes.get(vertice).contains(verticeVerficarAdjacencia)) {
                    matrizAdjacencias += '1';
                }
                else {
                    matrizAdjacencias += '0';
                }
            }
            matrizAdjacencias += '\n';
        }
        return matrizAdjacencias;
    }
}
