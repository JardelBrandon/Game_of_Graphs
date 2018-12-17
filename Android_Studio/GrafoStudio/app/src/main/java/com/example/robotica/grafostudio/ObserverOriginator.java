package com.example.robotica.grafostudio;

import com.example.robotica.grafostudio.utils.Ponto;

import java.util.ArrayList;
import java.util.HashMap;

public class ObserverOriginator implements Observer {
    private HashMap<Ponto, ArrayList<Ponto>> mapaPontosGrafo;
    private Caretaker caretaker = new Caretaker();
    private int mementosSalvos = 0, mementoAtual = 0;

    public Memento salvar() {
        return new Memento(mapaPontosGrafo);
    }

    public HashMap<Ponto, ArrayList<Ponto>> restaurar(int posicaoMemento) {
        mementoAtual = posicaoMemento;
        Memento memento = caretaker.getMemento(posicaoMemento);
        mapaPontosGrafo = memento.getMapaPontosGrafo();
        return mapaPontosGrafo;
    }

    @Override
    public void atualizar(Subject sujeito) {
        CompositeSubjectGrafoFragment grafoSujeito = (CompositeSubjectGrafoFragment) sujeito;
        grafoSujeito.salvarPontos();
        mapaPontosGrafo = grafoSujeito.getMapaPontosArquivos();
        caretaker.addMemento(salvar());
        mementosSalvos++;
        mementoAtual++;
    }

    public Caretaker getCaretaker() {
        return caretaker;
    }

    public int getMementosSalvos() {
        return mementosSalvos;
    }

    public int getMementoAtual() {
        return mementoAtual;
    }
}
