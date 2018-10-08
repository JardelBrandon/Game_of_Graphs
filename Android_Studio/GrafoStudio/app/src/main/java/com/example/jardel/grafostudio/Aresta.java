package com.example.jardel.grafostudio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Aresta extends View {
    private String nome;
    private Color cor;
    private float peso;
    private float larguraAresta;
    private int raio;
    private Vertice verticeInicial;
    private Vertice verticeFinal;
    private Paint paintLinha;
    private Paint paintCirculos;
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

    protected void reDraw() {
        this.invalidate();
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
