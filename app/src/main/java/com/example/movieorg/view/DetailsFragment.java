package com.example.movieorg.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movieorg.Film;
import com.example.movieorg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class DetailsFragment extends Fragment {
    public static final String ARG = "ARG";
    public static final String FILM_ID = "FILM_ID";

    FirebaseUser user;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        assert getArguments() != null;
        Film film = (Film) getArguments().getSerializable(ARG);
        String filmId = getArguments().getString(FILM_ID);

        TextView nameDetails = view.findViewById(R.id.film_name_details);
        TextView raitingDetails = view.findViewById(R.id.film_raiting_details);
        TextView statusDetails = view.findViewById(R.id.film_status_details);
        TextView ganreDetail = view.findViewById(R.id.film_ganre_details);
        TextView descriptionDetails = view.findViewById(R.id.film_description_details);

        Button linkDetails = view.findViewById(R.id.link_details);

        nameDetails.setText(film.getName());
        nameDetails.setSelected(true);

        raitingDetails.setText(film.getRaiting() == 0?"-":String.valueOf(film.getRaiting()));
        statusDetails.setText("Status - " + film.getStatus());
        ganreDetail.setText("Ganre - " + film.getGanre());
        descriptionDetails.setText(film.getDescription());

        linkDetails.setOnClickListener(view1 -> {
            try {
                Intent browserIntent = new
                        Intent(Intent.ACTION_VIEW, Uri.parse(film.getLink()));
                startActivity(browserIntent);
            }catch (Exception e){
                Toast.makeText(getActivity(), "Invalid link", Toast.LENGTH_SHORT).show();
            }
        });
        Button homeDetails = view.findViewById(R.id.back_to_list);
        homeDetails.setOnClickListener(view12 -> {
            ListFragment fragment = ListFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        });

        Button editDetails = view.findViewById(R.id.edit_details);
        editDetails.setOnClickListener(view13 -> {
            EditingFragment fragment = EditingFragment.newInstance(film,filmId);
            getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        });
    }
    public static  DetailsFragment newInstance(Film id,  String filmId){
        Bundle args =new Bundle();
        args.putSerializable(ARG,id);
        args.putString(FILM_ID, filmId);
        DetailsFragment fragment= new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
