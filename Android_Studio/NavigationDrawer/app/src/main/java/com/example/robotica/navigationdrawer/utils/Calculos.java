package com.example.robotica.navigationdrawer.utils;

import android.graphics.PointF;

import com.example.robotica.navigationdrawer.Aresta;
import com.example.robotica.navigationdrawer.Vertice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Calculos {
    public int[] calcularRetanguloDaAresta(Vertice v1, Vertice v2, int tamanhoVertice) {
        int[] ladosDoRetangulo = new int[4];
        if (v1.getX() <= v2.getX()) {
            ladosDoRetangulo[0] = (int) v1.getX();
            ladosDoRetangulo[2] = (int) v2.getX() + tamanhoVertice;
        }
        else {
            ladosDoRetangulo[0] = (int) v2.getX();
            ladosDoRetangulo[2] = (int) v1.getX() + tamanhoVertice;
        }
        if (v1.getY() <= v2.getY()) {
            ladosDoRetangulo[1] = (int) v1.getY();
            ladosDoRetangulo[3] = (int) v2.getY() + tamanhoVertice;
        }
        else {
            ladosDoRetangulo[1] = (int) v2.getY();
            ladosDoRetangulo[3] = (int) v1.getY() + tamanhoVertice;
        }
        return ladosDoRetangulo;
    }

    public boolean pontoToqueSobreAresta(PointF pontoInicial, PointF pontoFinal, PointF pontoTeste) {
        int larguraAresta = 15;
        if (Math.abs(pontoInicial.x - pontoFinal.x) < 45) {
            return (pontoTeste.x < (pontoFinal.x + larguraAresta)) && (pontoTeste.x > (pontoFinal.x - larguraAresta));
        }
        else {
            float y = pontoInicial.y + ((pontoFinal.y - pontoInicial.y) / (pontoFinal.x - pontoInicial.x)) * (pontoTeste.x - pontoInicial.x);
            return pontoTeste.y < (y + larguraAresta) && pontoTeste.y > (y - larguraAresta);
        }
    }

    public boolean pontoDentroDoCirculo(PointF test, PointF center, float width, float height) {
        float dx = test.x - center.x;
        float dy = test.y - center.y;
        return ((dx * dx) / (width * width) + (dy * dy) / (height * height)) * 4 <= 1;
    }


    public List<PointF> getPontoInterseccaoAresta(Aresta aresta) {
        Vertice verticeInicial = aresta.getVerticeInicial();
        Vertice verticeFinal = aresta.getVerticeFinal();
        int metadeTamanhoVerticeA = verticeInicial.getMetadeTamanhoVertice();
        int metadeTamanhoVerticeB = verticeInicial.getMetadeTamanhoVertice();
        PointF pointCentroA = new PointF(verticeInicial.getX() + metadeTamanhoVerticeA, verticeInicial.getY() + metadeTamanhoVerticeA);
        PointF pointCentroB = new PointF(verticeFinal.getX() + metadeTamanhoVerticeB, verticeFinal.getY() + metadeTamanhoVerticeB);
        List interseccaoA = getPontoInterseccaoVertice(pointCentroA, pointCentroB, pointCentroA, metadeTamanhoVerticeA);
        List interseccaoB = getPontoInterseccaoVertice(pointCentroA, pointCentroB, pointCentroB, metadeTamanhoVerticeB);
        return Arrays.asList((PointF) interseccaoA.get(1), (PointF) interseccaoB.get(0));
    }

    private List<PointF> getPontoInterseccaoVertice(PointF pointA, PointF pointB, PointF center, float radius) {
        float baX = pointB.x - pointA.x;
        float baY = pointB.y - pointA.y;
        float caX = center.x - pointA.x;
        float caY = center.y - pointA.y;

        float a = baX * baX + baY * baY;
        float bBy2 = baX * caX + baY * caY;
        float c = caX * caX + caY * caY - radius * radius;

        float pBy2 = bBy2 / a;
        float q = c / a;

        float disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }

        // if disc == 0 ... dealt with later
        float tmpSqrt = (float) Math.sqrt(disc);
        float abScalingFactor1 = -pBy2 + tmpSqrt;
        float abScalingFactor2 = -pBy2 - tmpSqrt;

        PointF p1 = new PointF(pointA.x - baX * abScalingFactor1, pointA.y - baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        PointF p2 = new PointF(pointA.x - baX * abScalingFactor2, pointA.y - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }

}
