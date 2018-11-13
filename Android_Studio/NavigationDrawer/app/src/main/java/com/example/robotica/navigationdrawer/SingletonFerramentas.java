package com.example.robotica.navigationdrawer;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SingletonFerramentas {
    private static SingletonFerramentas instancia = null;
    private int estado;
    private SingletonFacade facade = null;
    private Snackbar snackbar;

    private SingletonFerramentas() {
        this.facade = SingletonFacade.getInstancia();

    }

    public static SingletonFerramentas getInstancia() {
        if (instancia == null) {
            instancia = new SingletonFerramentas();
        }
        return instancia;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        if (this.estado != estado) {
            if (0 < estado && estado < 5)
                this.estado = estado;
                mudancaDeEstado(estado);
        }
    }

    private void mudancaDeEstado(int estado) {
        facade.deselecionarVertice();
        String mensagem;
        switch (estado) {
            case 1: //Selecionar

                break;
            case 2: //Criar Vertice
                mensagem = "Toque na tela para adicionar vertices";
                setupSnackBar(mensagem);

                break;
            case 3: //Criar Aresta
                mensagem = "Selecione o vertice inicial";
                setupSnackBar(mensagem);

                break;
            case 4: //Excluir
                mensagem = "Selecione algum elemento para exluí-lo";
                setupSnackBar(mensagem);

                break;
            default:
                break;
        }
    }

    public void setupSnackBar(String mensagem) {
        snackbar = Snackbar.make(facade.getGrafoLayout(), mensagem, Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Esconder", onClickSnackbarEsconder());
        snackbar.show();
    }

    private View.OnClickListener onClickSnackbarEsconder() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        };
    }
}
