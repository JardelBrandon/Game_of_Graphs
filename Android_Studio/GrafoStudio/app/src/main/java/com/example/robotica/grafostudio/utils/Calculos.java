package com.example.robotica.grafostudio.utils;

import com.example.robotica.grafostudio.Aresta;
import com.example.robotica.grafostudio.Vertice;

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

    public boolean pontoToqueSobreAresta(Ponto pontoInicial, Ponto pontoFinal, Ponto pontoTeste) {
        int larguraAresta = 15;
        if (Math.abs(pontoInicial.x - pontoFinal.x) < 45) {
            return (pontoTeste.x < (pontoFinal.x + larguraAresta)) && (pontoTeste.x > (pontoFinal.x - larguraAresta));
        }
        else {
            float y = pontoInicial.y + ((pontoFinal.y - pontoInicial.y) / (pontoFinal.x - pontoInicial.x)) * (pontoTeste.x - pontoInicial.x);
            return pontoTeste.y < (y + larguraAresta) && pontoTeste.y > (y - larguraAresta);
        }
    }

    public boolean pontoDentroDoCirculo(Ponto test, Ponto center, float width, float height) {
        float dx = test.x - center.x;
        float dy = test.y - center.y;
        return ((dx * dx) / (width * width) + (dy * dy) / (height * height)) * 4 <= 1;
    }


    public List<Ponto> getPontoInterseccaoAresta(Aresta aresta) {
        Vertice verticeInicial = aresta.getVerticeInicial();
        Vertice verticeFinal = aresta.getVerticeFinal();
        int metadeTamanhoVerticeA = verticeInicial.getMetadeTamanhoVertice();
        int metadeTamanhoVerticeB = verticeInicial.getMetadeTamanhoVertice();
        Ponto pointCentroA = new Ponto(verticeInicial.getX() + metadeTamanhoVerticeA, verticeInicial.getY() + metadeTamanhoVerticeA);
        Ponto pointCentroB = new Ponto(verticeFinal.getX() + metadeTamanhoVerticeB, verticeFinal.getY() + metadeTamanhoVerticeB);
        List interseccaoA = getPontoInterseccaoVertice(pointCentroA, pointCentroB, pointCentroA, metadeTamanhoVerticeA);
        List interseccaoB = getPontoInterseccaoVertice(pointCentroA, pointCentroB, pointCentroB, metadeTamanhoVerticeB);
        return Arrays.asList((Ponto) interseccaoA.get(1), (Ponto) interseccaoB.get(0));
    }

    private List<Ponto> getPontoInterseccaoVertice(Ponto pointA, Ponto pointB, Ponto center, float radius) {
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

        Ponto p1 = new Ponto(pointA.x - baX * abScalingFactor1, pointA.y - baY * abScalingFactor1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return Collections.singletonList(p1);
        }
        Ponto p2 = new Ponto(pointA.x - baX * abScalingFactor2, pointA.y - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }

    public static float getDistanciaVertices(Vertice verticeInicial, Vertice verticeFinal) {
        Ponto pontoInicial = new Ponto(verticeInicial.getX(), verticeInicial.getY());
        Ponto pontoFinal = new Ponto(verticeFinal.getX(), verticeFinal.getY());
        return (float) Math.sqrt(Math.pow((pontoFinal.x - pontoInicial.x), 2) + Math.pow((pontoFinal.y - pontoInicial.y), 2));
    }
}
