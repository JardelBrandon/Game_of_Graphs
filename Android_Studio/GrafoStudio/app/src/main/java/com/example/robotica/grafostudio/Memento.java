package com.example.robotica.grafostudio;

import com.example.robotica.grafostudio.utils.Ponto;

import java.util.ArrayList;
import java.util.HashMap;

public class Memento {

    private HashMap<Ponto, ArrayList<Ponto>> mapaPontosGrafo;

    public Memento(HashMap<Ponto, ArrayList<Ponto>> grafo) {
        this.mapaPontosGrafo = grafo;
    }

    public HashMap<Ponto, ArrayList<Ponto>> getMapaPontosGrafo() {
        return mapaPontosGrafo;
    }

}
