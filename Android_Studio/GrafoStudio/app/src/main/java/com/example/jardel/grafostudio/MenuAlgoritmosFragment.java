package com.example.jardel.grafostudio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuAlgoritmosFragment extends Fragment {
    GridLayout mainGrid;

    public MenuAlgoritmosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_algoritmos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainGrid = (GridLayout) view.findViewById(R.id.mainGridAlgoritmos);

        setSingleEvent(mainGrid);
    }

    private void setSingleEvent(android.support.v7.widget.GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("info", "Card item " + finalI);


                    AlgoritmosFragment algoritmosFragment = new AlgoritmosFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment, algoritmosFragment, "algoritmos");
                    fragmentTransaction.addToBackStack("pilha");
                    algoritmosFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                    /*
                    Intent intent = new Intent(getContext(), AlgoritmosActivity.class);
                    intent.putExtra("info","Card item "+finalI);
                    startActivity(intent);
                    */

                }
            });
        }
    }
}
