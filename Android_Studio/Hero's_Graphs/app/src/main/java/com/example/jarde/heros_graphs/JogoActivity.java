package com.example.jarde.heros_graphs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ActionBar;

public class JogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 /*
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Grafos_World");
        actionBar.setDisplayHomeAsUpEnabled(true);
*/
    }
}
