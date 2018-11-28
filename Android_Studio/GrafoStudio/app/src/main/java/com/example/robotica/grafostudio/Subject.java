package com.example.robotica.grafostudio;

import java.util.ArrayList;

public interface Subject {
    ArrayList<Observer> OBSERVERS = new ArrayList<>();
    public void cadastrarObservador(Observer observer);
    public void removerObservador(Observer observer);
    public void notificar();
}
