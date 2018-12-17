package com.example.robotica.grafostudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.robotica.grafostudio.utils.Ponto;

import java.util.ArrayList;
import java.util.HashMap;

public class AlgoritmosActivity extends AppCompatActivity {
    private HashMap<Ponto, ArrayList<Ponto>> mapaPontosArquivos;
    private SingletonFacade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritmos);

        facade = SingletonFacade.getInstancia();

        Bundle bundle = this.getIntent().getExtras();

        if(bundle != null) {
            mapaPontosArquivos = (HashMap<Ponto, ArrayList<Ponto>>) bundle.getSerializable("HashMap");
        }
        facade.getGrafoFragment().carregarGrafo(mapaPontosArquivos);
        for (Vertice vertice : facade.getGrafoFragment().getListaVertices()) {
            vertice.setOnTouchListener(null);
            vertice.setOnClickListener(new ClickVerticeRomenia());
        }
    }
}
