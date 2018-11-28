package com.example.robotica.grafostudio;

import android.content.Context;
import android.graphics.PointF;

public abstract class ThemeFactory {
    public abstract Vertice criarVertice(Context contexto, PointF ponto);

    public abstract Aresta criarAresta(Context contexto, Vertice verticeInicial, Vertice verticeFinal);
}
