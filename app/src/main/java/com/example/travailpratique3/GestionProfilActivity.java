package com.example.travailpratique3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class GestionProfilActivity extends AppCompatActivity {

    // Déclarations des vues
    TextInputEditText tiet_fullName, tiet_email;
    Button btn_updateProfile, btn_gotoProfileManagement, btn_signOut;
    FirebaseAuth bdAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_profil);

        // Initialisation des vues
        tiet_fullName = findViewById(R.id.edit_text_full_name);
        tiet_email = findViewById(R.id.edit_text_email);
        btn_updateProfile = findViewById(R.id.button_update_profile);
        btn_gotoProfileManagement = findViewById(R.id.button_goto_profile_management);
        btn_signOut = findViewById(R.id.button_sign_out);

        bdAuth = FirebaseAuth.getInstance();

        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mettre à jour les informations du profil dans Firebase
                // ...
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
