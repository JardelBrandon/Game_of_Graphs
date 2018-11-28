package com.example.robotica.grafostudio;

import java.util.ArrayList;

public class Caretaker {
    ArrayList<Memento> grafosAmazenado = new ArrayList<>();

    public void addMemento(Memento memento) {
        grafosAmazenado.add(memento);
    }

    public Memento getMemento(int i) {
        return grafosAmazenado.get(i);
    }
}
