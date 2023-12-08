package com.example.travailpratique3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
public class ConnexionActivity extends AppCompatActivity {
    TextInputEditText tiet_email, tiet_password;
    Button btn_register, btn_connexion;
    FirebaseAuth bdAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        tiet_email = findViewById(R.id.tiet_email);
        tiet_password = findViewById(R.id.tiet_password);
        btn_register = findViewById(R.id.btn_register);
        btn_connexion = findViewById(R.id.btn_connexion);

        bdAuth = FirebaseAuth.getInstance();

        // Initialisation des vues et des éléments nécessaires
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConnexionActivity.this, RegisterActivity.class));
                finish();
            }
        });

        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Récupération des informations d'authentification
                String email = tiet_email.getText().toString().trim();
                String password = tiet_password.getText().toString().trim();

                // Connexion d'un utilisateur existant avec Firebase
                bdAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Connexion réussie, rediriger vers MainActivity
                                Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Échec de la connexion, afficher un message d'erreur
                                Toast.makeText(ConnexionActivity.this, "Login failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
