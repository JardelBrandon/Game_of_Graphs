package com.example.robotica.grafostudio;

import android.graphics.PointF;
import android.view.ViewGroup;

import com.example.robotica.grafostudio.utils.Ponto;

public interface Aresta {
    public Vertice getVerticeInicial();
    public Vertice getVerticeFinal();
    public void setPontoInicial(Ponto pontoInicial);
    public void setPontoFinal(Ponto pontoFinal);
    public Ponto getPontoInicial();
    public Ponto getPontoFinal();
    public void setVerticeInicial(Vertice verticeInicial);
    public void setVerticeFinal(Vertice verticeFinal);
    public void setLayoutParams(ViewGroup.LayoutParams params);
    public float getPeso();
}
