package com.example.robotica.navigationdrawer;

import android.content.Context;

public abstract class AbstractFactory {
    public abstract Grafo criarElemento(Context context, int posicaoX, int posicaoY);
}
