package com.example.jarde.heros_graphs;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.github.barteksc.pdfviewer.PDFView;

public class PdfViewActivity extends AppCompatActivity {
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.fromAsset("Teoria dos Grafos.pdf").load();

        FloatingActionButton fabPdf = (FloatingActionButton) findViewById(R.id.fabPdf);
        fabPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent desafio = new Intent(getApplicationContext(), DesafioActivity.class);
                startActivity(desafio);
            }
        });
    }
}
