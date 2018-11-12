package com.example.robotica.navigationdrawer;

import java.util.ArrayList;

public class ObserverOriginator implements Observer {
    private ArrayList<Grafo> grafo;

    public Memento salvar() {
        return new Memento(grafo);
    }

    public ArrayList<Grafo> restaurar(Memento memento) {
        grafo = memento.getGrafo();
        return grafo;
    }

    @Override
    public void atualizar(Subject sujeito) {
        CompositeSubjectGrafoFragment grafoSujeio = (CompositeSubjectGrafoFragment) sujeito;
        grafo = grafoSujeio.getGrafo();
        salvar();
    }
}
