package com.example.robotica.grafostudio;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AlgoritmosRecyclerAdapter extends RecyclerView.Adapter<AlgoritmosRecyclerAdapter.ViewHolder> {

    private SingletonFacade facade = SingletonFacade.getInstancia();

    private Context contexto;

    private String[] titles = {"Busca em Profundidade",
                                "Busca em Largura",
                                "Busca A*"};

    private String[] details = {"DPS",
                                "BPS",
                                "A star"};

    private int[] images = { R.drawable.grafo_sample,
                            R.drawable.grafo_sample,
                            R.drawable.grafo_sample};

    public AlgoritmosRecyclerAdapter(Context context) {
        this.contexto = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail = (TextView)itemView.findViewById(R.id.item_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    Intent intentAlgoritmos = new Intent(contexto, AlgoritmosActivity.class);
                    contexto.startActivity(intentAlgoritmos);

                }
            });
        }
    }

    @Override
    public AlgoritmosRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlgoritmosRecyclerAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(details[i]);
        viewHolder.itemImage.setImageResource(images[i]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}