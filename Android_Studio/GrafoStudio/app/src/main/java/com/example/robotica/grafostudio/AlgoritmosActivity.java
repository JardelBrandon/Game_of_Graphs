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
import com.example.robotica.grafostudio.utils.ZoomLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AlgoritmosActivity extends AppCompatActivity {
    private SingletonFacade facade;
    private static final String FILE_NAME = "romenia";
    private ZoomLayout grafoLayoutRomenia;
    private final int LARGURA_TELA_REFERENCIA = 1794;
    private final int ALTURA_TELA_REFERENCIA = 1080;
    private int width;
    int height;

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algoritmos);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        grafoLayoutRomenia = findViewById(R.id.grafo_layout_romenia);
        grafoLayoutRomenia.setFocusable(false);
        grafoLayoutRomenia.setClickable(false);

        facade = SingletonFacade.getInstancia();

        CompositeSubjectGrafoFragment grafo = facade.lerGrafoArquivo(FILE_NAME);
        if (grafo == null || width != LARGURA_TELA_REFERENCIA || height != ALTURA_TELA_REFERENCIA) {
            facade.getGrafoFragment().carregarGrafo(coordenadasGrafoRomenia());
            facade.salvarGrafoArquivo(facade.getGrafoFragment(), FILE_NAME);
        }
        else {
            facade.getGrafoFragment().carregarGrafo(coordenadasGrafoRomenia());
            //facade.getGrafoFragment().carregarGrafo(grafo.getMapaPontosArquivos());
        }
    }

    private HashMap<Ponto, ArrayList<Ponto>>  coordenadasGrafoRomenia() {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics()) + 0;
        }
        // old x = 426 : y = 298
        Ponto oradea = new Ponto((float) 308 / LARGURA_TELA_REFERENCIA * width, (float) 262 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto zerind = new Ponto((float) 236 / LARGURA_TELA_REFERENCIA * width, (float) 346 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto arad = new Ponto((float) 182 / LARGURA_TELA_REFERENCIA * width, (float) 425 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto timisoara = new Ponto((float) 190 / LARGURA_TELA_REFERENCIA * width, (float) 593 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto lugoj = new Ponto((float) 408 / LARGURA_TELA_REFERENCIA * width, (float) 660 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto mehadia = new Ponto((float) 417 / LARGURA_TELA_REFERENCIA * width, (float) 740 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto dobreta = new Ponto((float) 408 / LARGURA_TELA_REFERENCIA * width, (float) 819 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto craiova = new Ponto((float) 681 / LARGURA_TELA_REFERENCIA * width, (float) 848 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto pitesti = new Ponto((float) 895 / LARGURA_TELA_REFERENCIA * width, (float) 685 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto rimnicu  = new Ponto((float) 622 / LARGURA_TELA_REFERENCIA * width, (float) 593 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto sibiu = new Ponto((float) 538 / LARGURA_TELA_REFERENCIA * width, (float) 497 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto fagaras = new Ponto((float) 853 / LARGURA_TELA_REFERENCIA * width, (float) 509 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto bucharest = new Ponto((float) 1143 / LARGURA_TELA_REFERENCIA * width, (float) 760 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto giurgiu = new Ponto((float) 1059 / LARGURA_TELA_REFERENCIA * width, (float) 878 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto urziceni = new Ponto((float)1323 / LARGURA_TELA_REFERENCIA * width, (float) 714 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto vaslui = new Ponto((float)1478 / LARGURA_TELA_REFERENCIA * width, (float) 522 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto iasi = new Ponto((float) 1369 / LARGURA_TELA_REFERENCIA * width, (float) 396 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto neamt = new Ponto((float) 1155 / LARGURA_TELA_REFERENCIA * width, (float) 333 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto hirsova = new Ponto((float) 1558 / LARGURA_TELA_REFERENCIA * width, (float) 719 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
        Ponto eforie = new Ponto((float) 1642 / LARGURA_TELA_REFERENCIA * width, (float) 832 / ALTURA_TELA_REFERENCIA * height - actionBarHeight / 2);
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
