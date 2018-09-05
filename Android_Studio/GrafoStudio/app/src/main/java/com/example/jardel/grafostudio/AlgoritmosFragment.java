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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate and initialize the bottom menu
        ActionMenuView bottomBar = (ActionMenuView) getActivity().findViewById(R.id.bottom_toolbar);
        Menu bottomMenu = bottomBar.getMenu();
        getActivity().getMenuInflater().inflate(R.menu.fragment_algoritmos, bottomMenu);
        for (int i = 0; i < bottomMenu.size(); i++) {
            bottomMenu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return onOptionsItemSelected(item);
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_comecar){
            Toast.makeText(getContext(), "Teste", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
