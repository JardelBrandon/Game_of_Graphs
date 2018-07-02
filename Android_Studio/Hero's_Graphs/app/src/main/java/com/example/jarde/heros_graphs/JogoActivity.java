package com.example.jarde.heros_graphs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class JogoActivity extends AppCompatActivity {
    private LinearLayout jogoLayout;
    private LinearLayout.LayoutParams verticeParams;
    private int tamanhoVertice;
    private int x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

        //Inserindo um vertice dinamicamente e aleatoriamente para demonstração
        jogoLayout = (LinearLayout) findViewById(R.id.jogoLayout);
        tamanhoVertice = getResources().getDimensionPixelSize(R.dimen.tamanho_vertice);
        x = 100;
        y = 200;

        final Vertice vertice = new Vertice(getApplicationContext());
        verticeParams = new LinearLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
        verticeParams.setMargins(x, y, 0, 0);
        vertice.setLayoutParams(verticeParams);
        vertice.setBackgroundResource(R.drawable.vertice_button);
        vertice.setGravity(Gravity.CENTER);
        vertice.setText("0");
        vertice.setId(0);
        jogoLayout.addView(vertice);

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
