package com.example.robotica.grafostudio;

import android.support.annotation.Nullable;
import android.view.View;

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
    public void setBackgroundResource(int resId);
    public void setOnClickListener(@Nullable View.OnClickListener l);
    public void setOnTouchListener(View.OnTouchListener l);
}
