package com.example.jardel.grafostudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AlgoritmosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritmos);

        if(getIntent() != null) {
            String info = getIntent().getStringExtra("info");

            switch (info) {
                case "Card item 0":
                    Toast.makeText(getApplicationContext(), "Algoritmo 0", Toast.LENGTH_SHORT).show();
                    break;
                case "Card item 1":
                    Toast.makeText(getApplicationContext(), "Algoritmo 1", Toast.LENGTH_SHORT).show();
                    break;
                case "Card item 2":
                    Toast.makeText(getApplicationContext(), "Algoritmo 2", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
