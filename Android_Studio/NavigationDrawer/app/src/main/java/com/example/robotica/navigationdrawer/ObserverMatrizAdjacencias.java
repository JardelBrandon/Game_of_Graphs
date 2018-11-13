package com.example.robotica.navigationdrawer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ObserverMatrizAdjacencias implements Observer {
    private HashMap<Vertice, ArrayList<Vertice>> mapaVerticesAdjacentes = new HashMap<>();

    @Override
    public String toString() {
        String matrizAdjacencias = "Tabela matriz de adjacências entre os vertices \n";
        matrizAdjacencias += "  ";
        for (Vertice vertice : mapaVerticesAdjacentes.keySet()) {
            matrizAdjacencias += vertice.getText();
            matrizAdjacencias += ' ';
        }
        matrizAdjacencias += '\n';

        for (Vertice vertice : mapaVerticesAdjacentes.keySet()) {
            matrizAdjacencias += vertice.getText();
            for (Vertice verticeVerficarAdjacencia : mapaVerticesAdjacentes.keySet()) {
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

    @Override
    public void atualizar(Subject sujeito) {
        CompositeSubjectGrafoFragment grafoSujeio = (CompositeSubjectGrafoFragment) sujeito;
        mapaVerticesAdjacentes = grafoSujeio.getMapaVerticesAdjacentes();
    }

}
