package com.example.robotica.grafostudio;

public interface Vertice {
    public void selecionar();
    public void deselecionar();
    public int getMetadeTamanhoVertice();
    public int getTamanhoVertice();
    public float getX();
    public float getY();
    public CharSequence getText();
    public boolean isVisitado();
    public void setVisitado(boolean visitado);
    public void setBackgroundColor(int color);
}
