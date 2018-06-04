package com.example.jarde.heros_graphs;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MonitoriaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoria);

        FloatingActionButton fabMonitoria = (FloatingActionButton) findViewById(R.id.fabMonitoria);
        fabMonitoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDesafio = new Intent(getApplicationContext(), DesafioActivity.class);
                startActivity(intentDesafio);
            }
        });
    }
}
