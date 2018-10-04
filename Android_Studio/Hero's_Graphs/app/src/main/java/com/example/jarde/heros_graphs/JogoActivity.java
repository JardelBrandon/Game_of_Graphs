package com.example.jarde.heros_graphs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.graphics.PointF;

public class JogoActivity extends AppCompatActivity {
    private FrameLayout jogoLayout;
    private FrameLayout.LayoutParams verticeParams;
    private int tamanhoVertice;
    private int x, y;
    private PointF pointA;
    private PointF pointB;
    private Mapa mapaJogo;

    public void gerarCaminhos() {
        int n_caminhos = this.mapaJogo.caminhos.size();
        for (int w = 0; w < n_caminhos; w++) {
            for (int i = 0; i < mapaJogo.caminhos.get(w).length; i++) {
                for (int j = 0; j < mapaJogo.caminhos.get(w)[i].length; j++) {
                    if (mapaJogo.caminhos.get(w)[i][j] == 1) {
                        final Aresta arestaX = new Aresta(getApplicationContext());
                        pointA = new PointF(mapaJogo.mapa.get(w)[i].v.getX() + 20, mapaJogo.mapa.get(w)[i].v.getY() +20);
                        pointB = new PointF(mapaJogo.mapa.get(w + 1)[j].v.getX() + 20, mapaJogo.mapa.get(w + 1)[j].v.getY() + 20);
                        arestaX.setPointA(pointA);
                        arestaX.setPointB(pointB);
                        jogoLayout.addView(arestaX);

                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

        //Inserindo um vertice dinamicamente e aleatoriamente para demonstração
        jogoLayout = (FrameLayout) findViewById(R.id.jogoLayout);
        this.tamanhoVertice = getResources().getDimensionPixelSize(R.dimen.tamanho_vertice);
        int metadeTamanhoVertice = 20;

        Mapa mapaJogo = new Mapa(5);
        this.mapaJogo=mapaJogo;
        mapaJogo.gerarCaminhos();
        int n = mapaJogo.mapa.size();
        int n_maior = mapaJogo.maiorNumeroVertice;
        int lastX = 20, lastY = 50
                ;
        int altura = 0;
        int largura = 1120;

        for(int cont= 0;cont<n;cont++){
            int v = mapaJogo.mapa.get(cont).length;
            altura = v*40;
            if (v==1){
                lastY = altura/2;
            }else {
                lastY = (altura/2)-((v-1)*50/2);
            }
            for(int i = 0; i<v;i++){
                if (v==1){
                    final Vertice verticeX = new Vertice(getApplicationContext());
                    //ImageView verticeX = new ImageView(getApplicationContext());
                    verticeParams = new FrameLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
                    //verticeX.setImageResource(R.drawable.hexagono);
                    verticeX.setLayoutParams(verticeParams);
                    verticeX.setTranslationX(lastX);
                    verticeX.setTranslationY(lastY);
                    verticeX.setBackgroundResource(R.drawable.vertice_button);
                    verticeX.setGravity(Gravity.CENTER);
                    jogoLayout.addView(verticeX);
                    mapaJogo.mapa.get(cont)[i].v=verticeX;
                }else{
                    final Vertice verticeX = new Vertice(getApplicationContext());
                    //ImageView verticeX = new ImageView(getApplicationContext());
                    verticeParams = new FrameLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
                    //verticeX.setImageResource(R.drawable.hexagono);
                    verticeX.setLayoutParams(verticeParams);
                    verticeX.setTranslationX(lastX);
                    verticeX.setTranslationY(lastY);
                    verticeX.setBackgroundResource(R.drawable.vertice_button);
                    verticeX.setGravity(Gravity.CENTER);
                    jogoLayout.addView(verticeX);
                    mapaJogo.mapa.get(cont)[i].v=verticeX;
                }
                lastY+=100;
            }
            lastX+=180;
        }
        gerarCaminhos();
        /*
        Button teste1 = (Button) findViewById(R.id.teste1);
        teste1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPdfView = new Intent(getApplicationContext(), PdfViewActivity.class);
                startActivity(intentPdfView);

            }
        });

        Button teste2 = (Button) findViewById(R.id.teste2);
        teste2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMonitoria = new Intent(getApplicationContext(), DesafioActivity.class);
                startActivity(intentMonitoria);
            }
        });
        */



    }
}
