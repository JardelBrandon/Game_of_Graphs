package com.example.robotica.grafostudio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.robotica.grafostudio.utils.Ponto;

public class ArestaLight extends View implements Grafo, Aresta {
    private String nome;
    private float peso;
    private float larguraAresta;
    private int raio;
    private Vertice verticeInicial;
    private Vertice verticeFinal;
    private Paint paintLinha;
    private Paint paintCirculos;
    private Ponto pontoInicial, pontoFinal;

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
        canvas.drawLine(pontoInicial.x, pontoInicial.y, pontoFinal.x, pontoFinal.y, paintLinha);
        canvas.drawCircle(pontoInicial.x, pontoInicial.y, raio, paintCirculos);
        canvas.drawCircle(pontoFinal.x, pontoFinal.y, raio, paintCirculos);
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

    public Ponto getPontoInicial() {
        return pontoInicial;
    }

    public void setPontoInicial(Ponto pontoInicial) {
        this.pontoInicial = pontoInicial;
    }

    public Ponto getPontoFinal() {
        return pontoFinal;
    }

    public void setPontoFinal(Ponto pontoFinal) {
        this.pontoFinal = pontoFinal;
    }
}
