package com.example.robotica.grafostudio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ArestaLight extends View implements Grafo, Aresta {
    private String nome;
    private float peso;
    private float larguraAresta;
    private int raio;
    private Vertice verticeInicial;
    private Vertice verticeFinal;
    private Paint paintLinha;
    private Paint paintCirculos;
    private PointF pointA, pointB;

    public ArestaLight(Context context) {
        super(context);
    }

    public ArestaLight(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ArestaLight(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onDraw(Canvas canvas) {
        paintLinha = new Paint();
        paintCirculos = new Paint();
        paintCirculos.setColor(getResources().getColor(R.color.md_amber_700));
        paintLinha.setColor(getResources().getColor(R.color.md_amber_900));
        larguraAresta = 10;
        raio = 10;
        paintLinha.setStrokeWidth(larguraAresta);
        canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, paintLinha);
        canvas.drawCircle(pointA.x, pointA.y, raio, paintCirculos);
        canvas.drawCircle(pointB.x, pointB.y, raio, paintCirculos);
        super.onDraw(canvas);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    @Override
    public Vertice getVerticeInicial() {
        return verticeInicial;
    }

    public void setVerticeInicial(Vertice verticeInicial) {
        this.verticeInicial = verticeInicial;
    }

    @Override
    public Vertice getVerticeFinal() {
        return verticeFinal;
    }

    public void setVerticeFinal(Vertice verticeFinal) {
        this.verticeFinal = verticeFinal;
    }

    public Paint getPaint() {
        return paintLinha;
    }

    public void setPaint(Paint paintLinha) {
        this.paintLinha = paintLinha;
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
