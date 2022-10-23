package com.example.movieorg;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieorg.view.DetailsFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class FilmAdapter extends FirebaseRecyclerAdapter <Film, FilmAdapter.FilmViewHolder> implements Serializable {

    private FragmentManager fm ;
    public FilmAdapter(@NonNull FirebaseRecyclerOptions<Film> options, FragmentManager fm) {
        super(options);
        this.fm=fm;
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapter.FilmViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull Film model) {
        holder.name.setText(model.getName());
        holder.name.setSelected(true);
        holder.status.setText(model.getStatus());
        holder.raiting.setText(String.valueOf(model.getRaiting()));
        holder.ganre.setText(model.getGanre());
        holder.itemView.setOnClickListener(view -> {
            int position1 = holder.getAdapterPosition();
            DatabaseReference item = getRef(position1);

            DetailsFragment fragment = DetailsFragment.newInstance(model, item.getKey());
            fm.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        });
    }


    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_list, parent, false);
        return new FilmViewHolder(view);    }

    class FilmViewHolder extends RecyclerView.ViewHolder{
        TextView name, status, raiting, ganre;
        public FilmViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.film_name);
            status = itemView.findViewById(R.id.status);
            raiting = itemView.findViewById(R.id.raiting);
            ganre = itemView.findViewById(R.id.ganre);
        }
    }

    @Override
    public void updateOptions(@NonNull FirebaseRecyclerOptions<Film> options) {
        super.updateOptions(options);
    }

    @NonNull
    @Override
    public DatabaseReference getRef(int position) {
        return super.getRef(position);
    }

}
