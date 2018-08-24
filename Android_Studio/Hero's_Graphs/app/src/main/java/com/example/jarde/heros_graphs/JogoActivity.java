package com.example.jarde.heros_graphs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout;

public class JogoActivity extends AppCompatActivity {
    private FrameLayout jogoLayout;
    private FrameLayout.LayoutParams verticeParams;
    private int tamanhoVertice;
    private int x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

        //Inserindo um vertice dinamicamente e aleatoriamente para demonstração
        jogoLayout = (FrameLayout) findViewById(R.id.jogoLayout);
        tamanhoVertice = getResources().getDimensionPixelSize(R.dimen.tamanho_vertice);

        Mapa mapaJogo = new Mapa(5);
        mapaJogo.gerarCaminhos();
        int n = mapaJogo.mapa.size();
        int n_maior = mapaJogo.maiorNumeroVertice;
        int lastX = 110, lastY = 110;
        int altura = 580;
        int largura = 1150;

        for(int cont= 0;cont<n;cont++){
            int v = mapaJogo.mapa.get(cont).length;
            if (v==1){
                lastY = altura/2;
            }else {
                lastY = (altura/2)-((v-1)*30/2);
            }
            for(int i = 0; i<v;i++){
                if (v==1){
                    final Vertice verticeX = new Vertice(getApplicationContext());
                    verticeParams = new FrameLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
                    verticeX.setLayoutParams(verticeParams);
                    verticeX.setTranslationX(lastX);
                    verticeX.setTranslationY(lastY);
                    verticeX.setBackgroundResource(R.drawable.vertice_button);
                    verticeX.setGravity(Gravity.CENTER);
                    jogoLayout.addView(verticeX);
                }else{
                    final Vertice verticeX = new Vertice(getApplicationContext());
                    verticeParams = new FrameLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
                    verticeX.setLayoutParams(verticeParams);
                    verticeX.setTranslationX(lastX);
                    verticeX.setTranslationY(lastY);
                    verticeX.setBackgroundResource(R.drawable.vertice_button);
                    verticeX.setGravity(Gravity.CENTER);
                    jogoLayout.addView(verticeX);
                }
                lastY+=60;
            }
            lastX+=100;
        }
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
                Intent intentMonitoria = new Intent(getApplicationContext(), MonitoriaActivity.class);
                startActivity(intentMonitoria);
            }
        });
        */
    }
}
