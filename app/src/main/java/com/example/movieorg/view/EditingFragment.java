package com.example.movieorg.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movieorg.Film;
import com.example.movieorg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditingFragment extends Fragment {

    public static final String ARG = "ARG";
    public static final String FILM_ID = "FILM_ID";

    private EditText etName;
    private EditText etGanre;
    private EditText etRaiting;
    private EditText etLink;
    private EditText etDescription;

    RadioButton radioWatch;
    RadioButton radioDWatch;
    RadioButton radioForgot;
    private String status;

    FirebaseUser user;
    private DatabaseReference databaseReference;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        Film film = (Film) getArguments().getSerializable(ARG);
        String filmId = getArguments().getString(FILM_ID);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Button btnBack = view.findViewById(R.id.btn_back_to_list_ed);
        Button btnEd = view.findViewById(R.id.btnEdFilm);
        etName = view.findViewById(R.id.ed_name_film);
        etGanre = view.findViewById(R.id.ed_ganre);
        etRaiting = view.findViewById(R.id.ed_raiting);
        etLink = view.findViewById(R.id.ed_link);
        etDescription = view.findViewById(R.id.ed_description);
        radioWatch = view.findViewById(R.id.radio_watch_ed);
        radioDWatch = view.findViewById(R.id.radio_dwatch_ed);
        radioForgot = view.findViewById(R.id.radio_forgot_ed);

        radioWatch.setOnClickListener(radioButtonClickListener);
        radioDWatch.setOnClickListener(radioButtonClickListener);
        radioForgot.setOnClickListener(radioButtonClickListener);
        setEditTextFilmInfo(film);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        btnBack.setOnClickListener(view1 -> goListFragment());

        btnEd.setOnClickListener(view12 -> {
            String name = etName.getText().toString();
            String ganre = etGanre.getText().toString();
            int raitinig = Integer.parseInt(etRaiting.getText().toString().isEmpty()?"0":etRaiting.getText().toString());
            String link = etLink.getText().toString();
            String description = etDescription.getText().toString();

            film.setName(name);
            film.setGanre(ganre);
            film.setRaiting(raitinig);
            film.setStatus(getStatus());
            film.setLink(link);
            film.setDescription(description);

            databaseReference.child(user.getUid()).child("Films").child(filmId).setValue(film).addOnCompleteListener(
                    getActivity(), task -> {
                        if(task.isSuccessful())
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        goListFragment();
                    }
            );
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editing, container, false);
    }


    public static  EditingFragment newInstance(Film id,String filmId){
        Bundle args =new Bundle();
        args.putSerializable(ARG,id);
        args.putString(FILM_ID,filmId);
        EditingFragment fragment= new EditingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton)v;
            switch (rb.getId()) {
                case R.id.radio_watch_ed: {
                    etRaiting.setEnabled(true);
                    setStatus("Watched");
                    break;
                }
                case R.id.radio_forgot_ed: {
                    etRaiting.setEnabled(false);
                    etRaiting.setText("0");
                    setStatus("Forgot");
                    break;
                }
                default:{
                    etRaiting.setEnabled(false);
                    etRaiting.setText("0");
                    setStatus("Didn`t watch");
                    break;
                }
            }
        }
    };
    private void goListFragment(){
        ListFragment fragment = ListFragment.newInstance();
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
    public void setEditTextFilmInfo(Film film) {
        etName.setText(film.getName());
        etGanre.setText(film.getGanre());
        etRaiting.setText(String.valueOf(film.getRaiting()));
        etLink.setText(film.getLink());
        etDescription.setText(film.getDescription());
        switch (film.getStatus()) {
            case "Watched": {
                radioWatch.setChecked(true);
                etRaiting.setEnabled(true);
                setStatus("Watched");
                break;
            }
            case "Didn`t watch": {
                radioDWatch.setChecked(true);
                etRaiting.setEnabled(false);
                setStatus("Forgot");
                break;
            }
            default: {
                radioForgot.setChecked(true);
                etRaiting.setEnabled(false);
                setStatus("Didn`t watch");
                break;
            }
        }
    }
    private String getStatus(){
        return status;
    }
    private void setStatus(String status){
        this.status = status;
    }
}
