package com.example.jarde.heros_graphs;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class    GrafoFragment extends Fragment {
    private LinearLayout grafoLayout;
    private int _xDelta;
    private int _yDelta;
    private int contadorVertices;
    private int contadorArestas;
    private int tamanhoVertice;
    private LinearLayout.LayoutParams verticeParams;
    private Map<String, Vertice> mapaVertices;
    private Map<String, Aresta> mapaArestas;

    public GrafoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grafo, container, false);

        //Inicializando Variávies
        contadorVertices = 0;
        contadorArestas = 0;
        mapaVertices = new HashMap<>();
        mapaArestas = new HashMap<>();
        tamanhoVertice = getResources().getDimensionPixelSize(R.dimen.tamanho_vertice);
        verticeParams = new LinearLayout.LayoutParams(tamanhoVertice, tamanhoVertice);
        grafoLayout = (LinearLayout) view.findViewById(R.id.grafoLayout);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        grafoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vertice vertice = new Vertice(getActivity());
                vertice.setLayoutParams(verticeParams);
                vertice.setBackgroundResource(R.drawable.vertice_button);
                vertice.setText(String.valueOf(contadorVertices));
                vertice.setId(contadorVertices);
                vertice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                mapaVertices.put(String.valueOf(contadorVertices), vertice);
                grafoLayout.addView(vertice);
                contadorVertices ++;
            }
        });
    }

    private class Vertice extends android.support.v7.widget.AppCompatButton{
        private String nome;
        private int grau;
        private Color cor;
        private boolean visitado;
        private Vertice[] verticesAdjacentes;

        public Vertice(Context context) {
            super(context);
        }
    }


    private class Aresta {
        private String nome;
        private Color cor;
        private float peso;
        private Vertice verticeInicial;
        private Vertice verticeFinal;
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            grafoLayout.invalidate();
            return true;
        }
    }
}

/*
        rootLayout = (ViewGroup) findViewById(R.id.rootLayout);
        vertice = (Button) rootLayout.findViewById(R.id.vetice1);
        vertice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Teste click", Toast.LENGTH_SHORT).show();
            }
        });

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        vertice.setLayoutParams(layoutParams);
        vertice.setOnTouchListener(new ChoiceTouchListener());

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Longo click", Toast.LENGTH_SHORT).show();
            }
        });
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GrafoFragment grafoFragment = new GrafoFragment();
        fragmentTransaction.add(R.id.rootLayout, grafoFragment, "Fragmento");
 */