package com.example.jarde.heros_graphs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Aresta extends View {
    private String nome;
    private Color cor;
    private float peso;
    private float larguraAresta;
    private Vertice verticeInicial;
    private Vertice verticeFinal;
    private Paint paint;
    private PointF pointA, pointB;

     public Aresta(Context context) {
        super(context);
    }

    public Aresta(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Aresta(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onDraw(Canvas canvas) {
        paint = new Paint();
        larguraAresta = 10;
        paint.setColor(Color.RED);
        paint.setStrokeWidth(larguraAresta);
        canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, paint);
        super.onDraw(canvas);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Vertice getVerticeInicial() {
        return verticeInicial;
    }

    public void setVerticeInicial(Vertice verticeInicial) {
        this.verticeInicial = verticeInicial;
    }

    public Vertice getVerticeFinal() {
        return verticeFinal;
    }

    public void setVerticeFinal(Vertice verticeFinal) {
        this.verticeFinal = verticeFinal;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public PointF getPointA() {
        return pointA;
    }

    public void setPointA(PointF pointA) {
        this.pointA = pointA;
    }

    public PointF getPointB() {
        return pointB;
    }

    public void setPointB(PointF pointB) {
        this.pointB = pointB;
    }
}
