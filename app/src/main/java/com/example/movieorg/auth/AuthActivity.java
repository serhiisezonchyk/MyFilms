package com.example.movieorg.auth;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieorg.R;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (savedInstanceState == null) {
            AuthFragment authFragment = new AuthFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, authFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            AuthFragment authFragment = new AuthFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, authFragment)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}