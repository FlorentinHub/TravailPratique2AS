package com.example.travailpratique3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText tiet_courriel, tiet_mdp, tiet_mdpConfirmation;
    Button btn_inscription, btn_connexion;
    FirebaseAuth bdAuth;
    Dialog bteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tiet_courriel = findViewById(R.id.tiet_courriel);
        tiet_mdp = findViewById(R.id.tiet_mdp);
        tiet_mdpConfirmation = findViewById(R.id.tiet_mdpconfirmation);
        btn_inscription = findViewById(R.id.btn_inscription);
        btn_connexion = findViewById(R.id.btn_connexion);

        // Initialiser FirebaseAuth
        bdAuth = FirebaseAuth.getInstance();

        // Vérifier si un utilisateur est déjà connecté
        FirebaseUser currentUser = bdAuth.getCurrentUser();
        if (currentUser != null) {
            // Rediriger vers l'activité de gestion du profil
            startActivity(new Intent(RegisterActivity.this, GestionProfilActivity.class));
            finish();
        }

        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, ConnexionActivity.class));
                finish();
            }
        });

        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tiet_courriel.getText().toString().trim();
                String password = tiet_mdp.getText().toString().trim();
                String confirmPassword = tiet_mdpConfirmation.getText().toString().trim();

                if (email.isEmpty()) {
                    tiet_courriel.setError(getString(R.string.register_email_hint));
                    tiet_courriel.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    tiet_courriel.setError(getString(R.string.invalid_email_format));
                    tiet_courriel.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    tiet_mdp.setError(getString(R.string.register_password_hint));
                    tiet_mdp.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    tiet_mdp.setError(getString(R.string.password_length_error));
                    tiet_mdp.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    tiet_mdpConfirmation.setError(getString(R.string.password_mismatch_error));
                    tiet_mdpConfirmation.requestFocus();
                    return;
                }

                // Création d'un objet Utilisateur
                Utilisateur utilisateur = new Utilisateur("NomUtilisateur", email, password);

                // Enregistrement de l'utilisateur avec Firebase
                signInUser(utilisateur.getEmail(), utilisateur.getPassword());
            }
        });
    }

    // Méthode pour authentifier l'utilisateur existant avec Firebase
    private void signInUser(String email, String password) {
        bdAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // L'inscription est réussie
                            Toast.makeText(RegisterActivity.this, getString(R.string.user_created), Toast.LENGTH_SHORT).show();
                            FirebaseUser user = bdAuth.getCurrentUser();
                            if (user != null) {
                                startActivity(new Intent(RegisterActivity.this, GestionProfilActivity.class));
                                finish();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, getString(R.string.error_prefix) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
