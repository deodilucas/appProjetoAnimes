package com.example.appprojetoanimes;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {
    private final List<Object> dados;

    private static final String TAG = "Myactity";

    private final Context context;


    public AnimeAdapter(Context context, List<Object> list){
        Log.i(TAG, "AnimeAdapter: Conect");
        this.context = context;
        this.dados = list;
    }

    @NonNull
    @Override
    public AnimeAdapter.AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_item, parent, false);
        return new AnimeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeAdapter.AnimeViewHolder holder, int position) {
        Anime anime = (Anime) dados.get(position);
//        holder.viewId.setText(anime.getId());
        holder.viewNome.setText(anime.getNome());
        holder.viewTemp.setText("Temps. "+anime.getTemp());
        holder.viewGen.setText(anime.getGenero());
        holder.viewEp.setText("Eps. "+anime.getEp());
        holder.viewAno.setText(anime.getAno());
        holder.viewMaterial.setText(anime.getMaterial());
        holder.viewStatus.setText(anime.getStatus());
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class AnimeViewHolder extends RecyclerView.ViewHolder{
        private TextView viewId;
        private TextView viewNome;
        private TextView viewTemp;
        private TextView viewGen;
        private TextView viewEp;
        private TextView viewAno;
        private TextView viewMaterial;
        private TextView viewStatus;
        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Anime anime = (Anime) dados.get(pos);
                    Snackbar.make(v, "Selecionado: \n"
                    + anime.getNome(), Snackbar.LENGTH_LONG)
                            .setAction("Action",null).show();
                }
            });
//            viewId = (TextView) itemView.findViewById(R.id.id);
            viewNome = (TextView) itemView.findViewById(R.id.nome);
            viewTemp = (TextView) itemView.findViewById(R.id.temp);
            viewGen = (TextView) itemView.findViewById(R.id.gen);
            viewEp = (TextView) itemView.findViewById(R.id.ep);
            viewAno = (TextView) itemView.findViewById(R.id.ano);
            viewMaterial = (TextView) itemView.findViewById(R.id.material);
            viewStatus = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
