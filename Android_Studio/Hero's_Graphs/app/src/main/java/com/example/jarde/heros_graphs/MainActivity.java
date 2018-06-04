package com.example.jarde.heros_graphs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonNovoJogo = (Button) findViewById(R.id.buttonNovoJogo);
        buttonNovoJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentJogo = new Intent(getApplicationContext(), JogoActivity.class);
                startActivity(intentJogo);
            }
        });

        Button buttonContinuarJogo = (Button) findViewById(R.id.buttonContinuarJogo);
        buttonContinuarJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert("Continuar Jogo...");
            }
        });

        Button buttonConfiguracoes = (Button) findViewById(R.id.buttonConfiguracoes);
        buttonConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert("Configurações");
            }
        });

        Button buttonSair = (Button) findViewById(R.id.buttonSair);
        buttonSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void alert(String string) {
        //A classe Toast mostra um alerta temporário
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
