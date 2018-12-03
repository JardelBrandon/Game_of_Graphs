package com.example.robotica.grafostudio.utils;

import java.io.Serializable;

public class Ponto implements Serializable {
    public float x;
    public float y;
    private static final long serialVersionUID = -6298516694275121213L;

    public Ponto(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
