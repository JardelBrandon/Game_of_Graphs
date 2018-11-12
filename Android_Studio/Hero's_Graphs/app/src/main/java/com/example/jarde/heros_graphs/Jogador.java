package com.example.jarde.heros_graphs;
import android.content.Context;


public class Jogador extends android.support.v7.widget.AppCompatButton {
    private String nome;
    private int energia;
    private Vertice posicao;

    public Jogador(Context context, Vertice v) {
        super(context);
        this.posicao=v;
        this.atualizar();
    }
    public void atualizar(){
        this.setTranslationX(this.posicao.getTranslationX()-20);
        this.setTranslationY(this.posicao.getTranslationY()-20);
    }

    public void setPosicao(Vertice v){
        this.posicao = v;
        this.atualizar();
    }

    public Vertice getPosicao() {
        return posicao;
    }

}
