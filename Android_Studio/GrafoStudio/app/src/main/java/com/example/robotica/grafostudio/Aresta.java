package com.example.robotica.grafostudio;

import android.graphics.PointF;
import android.view.ViewGroup;

public interface Aresta {
    public Vertice getVerticeInicial();
    public Vertice getVerticeFinal();
    public void setPointA(PointF pointA);
    public void setPointB(PointF pointB);
    public PointF getPointA();
    public PointF getPointB();
    public void setVerticeInicial(Vertice verticeInicial);
    public void setVerticeFinal(Vertice verticeFinal);
    public void setLayoutParams(ViewGroup.LayoutParams params);
}
