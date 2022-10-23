package com.example.movieorg.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movieorg.R;
import com.example.movieorg.view.ListFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AuthFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText etEmail;
    private EditText etPass;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user!=null){
                ListFragment fragment = ListFragment.newInstance();
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
            }
        };
        etEmail = view.findViewById(R.id.et_email);
        etPass = view.findViewById(R.id.et_pass);

        Button btnAuth = view.findViewById(R.id.btn_auth);
        Button btnReg = view.findViewById(R.id.btn_reg);


        btnAuth.setOnClickListener(view1 -> auth(etEmail.getText().toString(), etPass.getText().toString()));
        btnReg.setOnClickListener(view12 -> {
            RegistrationFragment registrationFragment = RegistrationFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, registrationFragment)
                    .commit();
        });
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            ListFragment fragment = ListFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    public void auth(String email,String pass){
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(getActivity(), task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        ListFragment fragment = ListFragment.newInstance();
                        getFragmentManager().beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.fragmentContainer, fragment)
                                .commit();
                    }
                    else
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                });
    }

    public static AuthFragment newInstance(){
        return new AuthFragment();
    }

}
