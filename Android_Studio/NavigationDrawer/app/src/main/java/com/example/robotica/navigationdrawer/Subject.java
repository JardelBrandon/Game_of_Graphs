package com.example.robotica.navigationdrawer;

import java.util.ArrayList;

public interface Subject {
    ArrayList<Observer> OBSERVERS = null;
    public void cadastrarObservador(Observer observer);
    public void removerObservador(Observer observer);
    public void notificar();
}
