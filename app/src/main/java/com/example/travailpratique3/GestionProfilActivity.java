package com.example.travailpratique3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class GestionProfilActivity extends AppCompatActivity {

    // Déclarations des vues
    TextInputEditText tiet_fullName, tiet_email;
    Button btn_updateProfile, btn_gotoProfileManagement, btn_signOut;
    FirebaseAuth bdAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_profil);

        // Initialisation de la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialisation des autres vues
        tiet_fullName = findViewById(R.id.edit_text_full_name);
        tiet_email = findViewById(R.id.edit_text_email);
        btn_updateProfile = findViewById(R.id.button_update_profile);
        btn_gotoProfileManagement = findViewById(R.id.button_goto_profile_management);
        btn_signOut = findViewById(R.id.button_sign_out);

        bdAuth = FirebaseAuth.getInstance();

        // Récupérer l'utilisateur actuellement connecté
        FirebaseUser currentUser = bdAuth.getCurrentUser();
        if (currentUser != null) {
            // Si l'utilisateur a déjà un nom complet enregistré
            if (currentUser.getDisplayName() != null && !currentUser.getDisplayName().isEmpty()) {
                tiet_fullName.setText(currentUser.getDisplayName());
            }
            // Définir le hint du champ courriel avec l'email de l'utilisateur
            tiet_email.setText(currentUser.getEmail());
        }


        // Bouton pour mettre à jour le profil
        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newFullName = tiet_fullName.getText().toString().trim();

                // Récupérer l'utilisateur actuellement connecté
                FirebaseUser currentUser = bdAuth.getCurrentUser();
                if (currentUser != null) {
                    // Mettre à jour le nom complet dans les informations du profil
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(newFullName)
                            .build();

                    currentUser.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Mise à jour du nom complet réussie
                                        Toast.makeText(GestionProfilActivity.this, "Nom complet mis à jour", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Échec de la mise à jour du nom complet
                                        Toast.makeText(GestionProfilActivity.this, "Erreur lors de la mise à jour du nom complet", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        btn_gotoProfileManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aller vers l'activité de gestion du profil
                startActivity(new Intent(GestionProfilActivity.this, GestionProfilActivity.class));
            }
        });

        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Déconnexion de l'utilisateur actuel
                bdAuth.signOut();
                startActivity(new Intent(GestionProfilActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
