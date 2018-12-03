package com.example.robotica.grafostudio;

import android.content.Context;
import android.graphics.PointF;

import com.example.robotica.grafostudio.utils.Ponto;

public abstract class ThemeFactory {
    public abstract Vertice criarVertice(Context contexto, Ponto ponto);

    public abstract Aresta criarAresta(Context contexto, Vertice verticeInicial, Vertice verticeFinal);
}
