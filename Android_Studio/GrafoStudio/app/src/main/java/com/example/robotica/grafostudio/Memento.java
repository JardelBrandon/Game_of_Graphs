package com.example.robotica.grafostudio;

import java.util.ArrayList;

public class Memento {
    private ArrayList<Grafo> grafo;

    public Memento(ArrayList<Grafo> grafo) {
        this.grafo = grafo;
    }

    public ArrayList<Grafo> getGrafo() {
        return grafo;
    }

}
