package com.example.jarde.heros_graphs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;


public class DesafioActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio);
        findViewById(R.id.textTeste);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        /*
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