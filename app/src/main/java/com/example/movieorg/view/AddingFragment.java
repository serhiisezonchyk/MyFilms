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

public class AddingFragment extends Fragment {

    private EditText etName;
    private EditText etGanre;
    private EditText etRaiting;
    private EditText etLink;
    private EditText etDescription;

    private String status;

    FirebaseUser user;
    private DatabaseReference databaseReference;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Button btnBack = view.findViewById(R.id.btn_back_to_list);
        Button btnAdd = view.findViewById(R.id.btnAddFilm);
        etName = view.findViewById(R.id.et_name_film);
        etGanre = view.findViewById(R.id.et_ganre);
        etRaiting = view.findViewById(R.id.et_raiting);
        etLink = view.findViewById(R.id.et_link);
        etDescription = view.findViewById(R.id.et_description);
        RadioButton radioWatch = view.findViewById(R.id.radio_watch);
        RadioButton radioDWatch = view.findViewById(R.id.radio_dwatch);
        RadioButton radioForgot = view.findViewById(R.id.radio_forgot);
        radioDWatch.setChecked(true);
        setStatus("Didn`t watch");

        radioWatch.setOnClickListener(radioButtonClickListener);
        radioDWatch.setOnClickListener(radioButtonClickListener);
        radioForgot.setOnClickListener(radioButtonClickListener);
        etRaiting.setEnabled(false);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        btnBack.setOnClickListener(view1 -> goListFragment());

        btnAdd.setOnClickListener(view12 -> {
            String id = databaseReference.getKey();
            String name = etName.getText().toString();
            String ganre = etGanre.getText().toString();
            int raitinig = Integer.parseInt(etRaiting.getText().toString().isEmpty()?"0":etRaiting.getText().toString());
            String link = etLink.getText().toString();
            String description = etDescription.getText().toString();
            Film newFilm = new Film(id,name,getStatus(),ganre,raitinig,link,description);
            databaseReference.child(user.getUid()).child("Films").push().setValue(newFilm).addOnCompleteListener(
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
        return inflater.inflate(R.layout.fragment_adding, container, false);
    }


    public static  AddingFragment newInstance(){
        return new AddingFragment();
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton)v;
            switch (rb.getId()) {
                case R.id.radio_watch: {
                    etRaiting.setEnabled(true);
                    setStatus("Watched");
                    break;
                }
                case R.id.radio_forgot: {
                    etRaiting.setText("0");
                    etRaiting.setEnabled(false);
                    setStatus("Forgot");
                    break;
                }
                default:{
                    etRaiting.setText("0");
                    etRaiting.setEnabled(false);
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
    private String getStatus(){
        return status;
    }
    private void setStatus(String status){
        this.status = status;
    }
}
