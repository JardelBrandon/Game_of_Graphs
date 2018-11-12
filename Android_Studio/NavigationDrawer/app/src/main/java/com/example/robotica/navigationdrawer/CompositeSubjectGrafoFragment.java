package com.example.robotica.navigationdrawer;


import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.robotica.navigationdrawer.utils.ZoomLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class CompositeSubjectGrafoFragment extends Fragment implements Grafo, Subject {
    private ArrayList<Grafo> folhasGrafo;

    public CompositeSubjectGrafoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafo, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void addElemento(Grafo elemento) {
        folhasGrafo.add(elemento);
        notificar();
    }

    public void removeElemento(Grafo elemento) {
        folhasGrafo.remove(elemento);
        notificar();
    }

    public void getElemento(int i) {
        folhasGrafo.get(i);
    }

    public ArrayList<Grafo> getGrafo() {
        return folhasGrafo;
    }

    @Override
    public void cadastrar(Observer observer) {
        OBSERVERS.add(observer);
    }

    @Override
    public void remover(Observer observer) {
        OBSERVERS.remove(observer);
    }

    @Override
    public void notificar() {
        for (Observer observer : OBSERVERS) {
            observer.atualizar(this);
        }
    }
}
