package com.example.robotica.grafostudio;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;

import com.example.robotica.grafostudio.utils.Ponto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AlgoritmosRomeniaActivity extends AppCompatActivity {
    private SingletonFacade facade;
    private static final String FILE_NAME = "romenia";

    private final int larguraTelaReferencia = 1794;
    private final int alturaTelaReferencia = 1080;
    private int larguraAtual;
    private int alturaAtual;
    private boolean salvarGrafo;

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritmos_romenia);

        facade = SingletonFacade.getInstancia();
        salvarGrafo = false;

        CompositeSubjectGrafoFragment grafo = facade.lerGrafoArquivo(FILE_NAME);
        if (grafo == null) {
            facade.getGrafoFragment().carregarGrafo(coordenadasGrafoRomenia());
            salvarGrafo = true;
        } else {
            facade.getGrafoFragment().carregarGrafo(grafo.getMapaPontosArquivos());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (salvarGrafo) {
            facade.getGrafoFragment().salvarPontos();
            facade.salvarGrafoArquivo(facade.getGrafoFragment(), FILE_NAME);
        }
        facade.getGrafoFragment().removerVisualizacoes();
    }


    private HashMap<Ponto, ArrayList<Ponto>>  coordenadasGrafoRomenia() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        larguraAtual = size.x;
        alturaAtual = size.y;

        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics()) + getResources().getDimensionPixelSize(R.dimen.tamanho_vertice) / 2 - 10;
        }

        Ponto oradea = new Ponto((float) 308 / larguraTelaReferencia * larguraAtual, (float) 280 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto zerind = new Ponto((float) 236 / larguraTelaReferencia * larguraAtual, (float) 360 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto arad = new Ponto((float) 185 / larguraTelaReferencia * larguraAtual, (float) 435 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto timisoara = new Ponto((float) 195 / larguraTelaReferencia * larguraAtual, (float) 600 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto lugoj = new Ponto((float) 410 / larguraTelaReferencia * larguraAtual, (float) 663 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto mehadia = new Ponto((float) 420 / larguraTelaReferencia * larguraAtual, (float) 740 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto dobreta = new Ponto((float) 410 / larguraTelaReferencia * larguraAtual, (float) 819 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto craiova = new Ponto((float) 685 / larguraTelaReferencia * larguraAtual, (float) 842 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto pitesti = new Ponto((float) 895 / larguraTelaReferencia * larguraAtual, (float) 685 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto rimnicu  = new Ponto((float) 625 / larguraTelaReferencia * larguraAtual, (float) 600 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto sibiu = new Ponto((float) 545 / larguraTelaReferencia * larguraAtual, (float) 505 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto fagaras = new Ponto((float) 853 / larguraTelaReferencia * larguraAtual, (float) 522 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto bucharest = new Ponto((float) 1140 / larguraTelaReferencia * larguraAtual, (float) 765 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto giurgiu = new Ponto((float) 1059 / larguraTelaReferencia * larguraAtual, (float) 875 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto urziceni = new Ponto((float)1318 / larguraTelaReferencia * larguraAtual, (float) 720 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto vaslui = new Ponto((float)1480 / larguraTelaReferencia * larguraAtual, (float) 530 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto iasi = new Ponto((float) 1369 / larguraTelaReferencia * larguraAtual, (float) 410 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto neamt = new Ponto((float) 1160 / larguraTelaReferencia * larguraAtual, (float) 350 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto hirsova = new Ponto((float) 1553 / larguraTelaReferencia * larguraAtual, (float) 719 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        Ponto eforie = new Ponto((float) 1642 / larguraTelaReferencia * larguraAtual, (float) 832 / alturaTelaReferencia * alturaAtual - actionBarHeight / 2);
        HashMap<Ponto, ArrayList<Ponto>> mapaPontosArquivos = new HashMap<>();
        mapaPontosArquivos.put(oradea, new ArrayList<Ponto>(Arrays.asList(zerind, sibiu)));
        mapaPontosArquivos.put(zerind, new ArrayList<Ponto>(Arrays.asList(oradea, arad)));
        mapaPontosArquivos.put(arad, new ArrayList<Ponto>(Arrays.asList(zerind, sibiu, timisoara)));
        mapaPontosArquivos.put(timisoara, new ArrayList<Ponto>(Arrays.asList(lugoj, arad)));
        mapaPontosArquivos.put(lugoj, new ArrayList<Ponto>(Arrays.asList(mehadia, timisoara)));
        mapaPontosArquivos.put(mehadia, new ArrayList<Ponto>(Arrays.asList(lugoj, dobreta)));
        mapaPontosArquivos.put(dobreta, new ArrayList<Ponto>(Arrays.asList(mehadia, craiova)));
        mapaPontosArquivos.put(craiova, new ArrayList<Ponto>(Arrays.asList(dobreta, pitesti, rimnicu)));
        mapaPontosArquivos.put(pitesti, new ArrayList<Ponto>(Arrays.asList(craiova, rimnicu, bucharest)));
        mapaPontosArquivos.put(rimnicu, new ArrayList<Ponto>(Arrays.asList(pitesti, craiova, sibiu)));
        mapaPontosArquivos.put(sibiu, new ArrayList<Ponto>(Arrays.asList(rimnicu, arad, oradea, fagaras)));
        mapaPontosArquivos.put(fagaras, new ArrayList<Ponto>(Arrays.asList(sibiu, bucharest)));
        mapaPontosArquivos.put(bucharest, new ArrayList<Ponto>(Arrays.asList(fagaras, pitesti, giurgiu, urziceni)));
        mapaPontosArquivos.put(giurgiu, new ArrayList<Ponto>(Arrays.asList(bucharest)));
        mapaPontosArquivos.put(urziceni, new ArrayList<Ponto>(Arrays.asList(bucharest, hirsova, vaslui)));
        mapaPontosArquivos.put(vaslui, new ArrayList<Ponto>(Arrays.asList(urziceni, iasi)));
        mapaPontosArquivos.put(iasi, new ArrayList<Ponto>(Arrays.asList(vaslui, neamt)));
        mapaPontosArquivos.put(neamt, new ArrayList<Ponto>(Arrays.asList(iasi)));
        mapaPontosArquivos.put(hirsova, new ArrayList<Ponto>(Arrays.asList(urziceni, eforie)));
        mapaPontosArquivos.put(eforie, new ArrayList<Ponto>(Arrays.asList(hirsova)));

        return mapaPontosArquivos;
    }

}
