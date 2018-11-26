package com.example.robotica.navigationdrawer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ArestaDark extends View implements Grafo, Aresta {
    private String nome;
    private float peso;
    private float larguraAresta;
    private int raio;
    private Vertice verticeInicial;
    private Vertice verticeFinal;
    private Paint paintLinha;
    private Paint paintCirculos;
    private PointF pointA, pointB;

    public ArestaDark(Context context) {
        super(context);
    }

    public ArestaDark(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ArestaDark(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onDraw(Canvas canvas) {
        paintLinha = new Paint();
        paintCirculos = new Paint();
        paintCirculos.setColor(getResources().getColor(R.color.colorPrimary));
        paintLinha.setColor(getResources().getColor(R.color.colorPrimaryDark));
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

    public Paint getPaint() {
        return paintLinha;
    }

    public void setPaint(Paint paintLinha) {
        this.paintLinha = paintLinha;
    }

    @Override
    public Vertice getVerticeInicial() {
        return verticeInicial;
    }

    @Override
    public void setVerticeInicial(Vertice verticeInicial) {
        this.verticeInicial = verticeInicial;
    }

    @Override
    public Vertice getVerticeFinal() {
        return verticeFinal;
    }

    @Override
    public void setVerticeFinal(Vertice verticeFinal) {
        this.verticeFinal = verticeFinal;
    }

    @Override
    public PointF getPointA() {
        return pointA;
    }

    @Override
    public void setPointA(PointF pointA) {
        this.pointA = pointA;
    }

    @Override
    public PointF getPointB() {
        return pointB;
    }

    @Override
    public void setPointB(PointF pointB) {
        this.pointB = pointB;
    }

}
