package com.example.jardel.grafostudio;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlgoritmosFragment extends Fragment {

    public AlgoritmosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_algoritmos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            //String info = getIntent().getStringExtra("info");
            String info = bundle.getString("info");

            switch (info) {
                case "Card item 0":
                    Toast.makeText(getContext(), "Algoritmo 0", Toast.LENGTH_SHORT).show();
                    break;
                case "Card item 1":
                    Toast.makeText(getContext(), "Algoritmo 1", Toast.LENGTH_SHORT).show();
                    break;
                case "Card item 2":
                    Toast.makeText(getContext(), "Algoritmo 2", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* Esconder toolbar
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
    */
}
