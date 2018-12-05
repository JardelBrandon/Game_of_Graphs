package com.example.robotica.grafostudio;

import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

import com.example.robotica.grafostudio.utils.Ponto;
import com.example.robotica.grafostudio.utils.ZoomLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SingletonFacade {
    private static SingletonFacade instancia = null;
    private static CompositeSubjectGrafoFragment grafoFragment;
    private static MainActivity mainActivity;
    private SingletonFerramentas ferramentas;
    private int algoritmo;

    private SingletonFacade() {
        init();
    }

    private void init() {
        ferramentas = SingletonFerramentas.getInstancia();
    }

    public static SingletonFacade getInstancia() {
        if (instancia == null) {
            instancia = new SingletonFacade();
        }
        return instancia;
    }

    public void criarVertice(Ponto ponto) {
        grafoFragment.criarVertice(ponto);
    }

    public void criarAresta(Vertice verticeInicial, Vertice verticeFinal) {
        grafoFragment.criarAresta(verticeInicial, verticeFinal);
    }

    public void setEstadoFerramentas(int estadoFerramentas) {
        deselecionarVertice();
        ferramentas.setEstado(estadoFerramentas);
    }

    public int getEstadoFerramentas() {
        return ferramentas.getEstado();
    }

    public void snackBar(String mensagem) {
        ferramentas.setupSnackBar(mensagem);
    }

    public void removerVertice(Vertice vertice) {
        grafoFragment.removerElemento((Grafo) vertice);
    }

    public void removerAresta(Aresta aresta) {
        grafoFragment.removerElemento((Grafo) aresta);
    }

    public void selecionarVertice(Vertice vertice) {
        if (grafoFragment.getVerticeSelecionado() != null) {
            deselecionarVertice();
        }
        vertice.selecionar();
        grafoFragment.setVerticeSelecionado(vertice);
    }

    public void deselecionarVertice() {
        if (grafoFragment.getVerticeSelecionado() != null) {
            grafoFragment.getVerticeSelecionado().deselecionar();
            grafoFragment.setVerticeSelecionado(null);
        }
    }

    public void salvarGrafoArquivo(CompositeSubjectGrafoFragment grafo, final String FILE_NAME) {
        FileOutputStream fos = null;
        try {
            fos = mainActivity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(grafo);
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public CompositeSubjectGrafoFragment lerGrafoArquivo(final String FILE_NAME) {
        File file = mainActivity.getFileStreamPath(FILE_NAME);
        FileInputStream fis = null;
        CompositeSubjectGrafoFragment grafo = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream ois = new ObjectInputStream(fis)) {
            grafo = (CompositeSubjectGrafoFragment) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            return grafo;
        }
    }

    public static CompositeSubjectGrafoFragment getGrafoFragment() {
        return grafoFragment;
    }

    public int getQuantidadeDeVertices() {
        return grafoFragment.getListaVertices().size();
    }

    public static void setGrafoFragment(CompositeSubjectGrafoFragment grafoFragment) {
        SingletonFacade.grafoFragment = grafoFragment;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static void setMainActivity(MainActivity mainActivity) {
        SingletonFacade.mainActivity = mainActivity;
    }

    public static ZoomLayout getGrafoLayout() {
        return grafoFragment.getGrafoLayout();
    }

    public int getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(int algoritmo) {
        this.algoritmo = algoritmo;
    }

    public void rodarAlgoritmos(int algoritmo, Vertice verticeInicial, Vertice verticeFinal) {
        grafoFragment.rodarAlgoritmos(algoritmo, verticeInicial, verticeFinal);
    }
}
