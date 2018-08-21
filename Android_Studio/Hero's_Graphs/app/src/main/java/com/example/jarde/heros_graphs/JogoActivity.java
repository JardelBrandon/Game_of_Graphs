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
        x = 0;
        y = 0;

        for(int i=0;i<10;i++) {
            for(int j = 0;j<10;j++) {
                final Vertice vertice = new Vertice(getApplicationContext());
                verticeParams = new FrameLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
                //verticeParams.setMargins(x, y, 0, 0);
                vertice.setLayoutParams(verticeParams);
                vertice.setTranslationX(x);
                vertice.setTranslationY(y);
                vertice.setBackgroundResource(R.drawable.vertice_button);
                vertice.setGravity(Gravity.CENTER);
                y += 70;
                jogoLayout.addView(vertice);
            }
            x+=70;
            y=0;
        }
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
    }
}
