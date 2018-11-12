package com.example.robotica.navigationdrawer;

import java.util.ArrayList;

public interface Subject {
    ArrayList<Observer> OBSERVERS = null;
    public void cadastrar(Observer observer);
    public void remover(Observer observer);
    public void notificar();
}
