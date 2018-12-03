package com.example.robotica.grafostudio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.robotica.grafostudio.utils.Ponto;

public class ArestaDark extends View implements Grafo, Aresta {
    private String nome;
    private float peso;
    private float larguraAresta;
    private int raio;
    private Vertice verticeInicial;
    private Vertice verticeFinal;
    private Paint paintLinha;
    private Paint paintCirculos;
    private Ponto pontoInicial, pontoFinal;

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
    public Ponto getPontoInicial() {
        return pontoInicial;
    }

    @Override
    public void setPontoInicial(Ponto pontoInicial) {
        this.pontoInicial = pontoInicial;
    }

    @Override
    public Ponto getPontoFinal() {
        return pontoFinal;
    }

    @Override
    public void setPontoFinal(Ponto pontoFinal) {
        this.pontoFinal = pontoFinal;
    }

}
