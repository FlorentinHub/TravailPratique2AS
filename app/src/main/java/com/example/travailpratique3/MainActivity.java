package com.example.travailpratique3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ajout de la barre de menu personnalisée
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
//        btnLogout = findViewById(R.id.btn_logout);

        // Vérifie si l'utilisateur est connecté
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Redirige vers l'activité de connexion si l'utilisateur n'est pas connecté
            Intent intent = new Intent(MainActivity.this, ConnexionActivity.class);
            startActivity(intent);
            finish(); // Empêche l'utilisateur de revenir à cette activité via le bouton "Retour"
        }
    }

    // Méthode pour créer la barre de navigation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //Savoir quel action est demandee par le user
            //Deconnexion
        if (id == R.id.action_logout) {
            mAuth.signOut();
            //Renvoi sur la page de connexion
            Intent logoutIntent = new Intent(MainActivity.this, ConnexionActivity.class);
            startActivity(logoutIntent);
            finish();
            return true;
            //Gestion profil
        } else if (id == R.id.action_edit_profile) {
            Intent editProfileIntent = new Intent(MainActivity.this, GestionProfilActivity.class);
            startActivity(editProfileIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}