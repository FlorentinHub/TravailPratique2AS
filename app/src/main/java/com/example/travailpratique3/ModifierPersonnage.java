package com.example.travailpratique3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travailpratique3.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ModifierPersonnage extends AppCompatActivity {

    private EditText editTextNom;
    private EditText editTextDescription;
    private SeekBar editSeekValLevel;
    private SeekBar editSeekValHp;
    private Button buttonEnregistrer, buttonSupprimer;
    private Personnage selectedPersonnage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_personnage);

        // Récupération des vues
        editTextNom = findViewById(R.id.edit_text_nom);
        editTextDescription = findViewById(R.id.edit_text_description);
        editSeekValLevel = findViewById(R.id.seek_bar_level);
        editSeekValHp = findViewById(R.id.seek_bar_health_points);
        buttonEnregistrer = findViewById(R.id.btn_Enregistrer);
        buttonSupprimer = findViewById(R.id.btn_supprimer);

        // Récupération du selectedPersonnage du Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectedPersonnage = bundle.getParcelable("selectedPersonnage");
            if (selectedPersonnage != null) {
                // Affichage des détails du selectedPersonnage dans les champs
                editTextNom.setText(selectedPersonnage.getNom());
                editTextDescription.setText(selectedPersonnage.getDescription());
                editSeekValLevel.setProgress(selectedPersonnage.getNiveau());
                editSeekValHp.setProgress(selectedPersonnage.getPointsDeVie());
            }
        }

        // Écouteur de clic pour le bouton Enregistrer
        buttonEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enregistrerModifications();
            }
        });
        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supprimerPersonnage();
            }
        });
    }

    // Méthode pour enregistrer les modifications
    private void enregistrerModifications() {
        // Récupération des nouvelles valeurs des champs EditText
        String nom = editTextNom.getText().toString();
        String description = editTextDescription.getText().toString();
        int niveau = editSeekValLevel.getProgress();
        int pointsDeVie = editSeekValHp.getProgress();

        // Mettre à jour les détails du selectedPersonnage
        if (selectedPersonnage != null) {
            selectedPersonnage.setNom(nom);
            selectedPersonnage.setDescription(description);
            selectedPersonnage.setNiveau(niveau);
            selectedPersonnage.setPointsDeVie(pointsDeVie);

            String personnageId = selectedPersonnage.getId();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Personnages").child(personnageId);
            databaseReference.setValue(selectedPersonnage)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Notifier via un Toast que les modifications ont été enregistrées
                            Toast.makeText(ModifierPersonnage.this, R.string.modifications_saved, Toast.LENGTH_SHORT).show();
                            //Renvoyer sur le main
                            Intent intent = new Intent(ModifierPersonnage.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("ModifierPerso", "Erreur lors de l'enregistrement des modifications : " + e.getMessage());
                            Toast.makeText(ModifierPersonnage.this, R.string.error_modifications, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void supprimerPersonnage() {
        if (selectedPersonnage != null) {
            String personnageId = selectedPersonnage.getId();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Personnages").child(personnageId);
            databaseReference.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Notification Toast success
                            Toast.makeText(ModifierPersonnage.this, R.string.character_deleted, Toast.LENGTH_SHORT).show();

                            // Retour au MainActivity
                            Intent intent = new Intent(ModifierPersonnage.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("ModifierPerso", "Erreur : " + e.getMessage());
                            Toast.makeText(ModifierPersonnage.this, R.string.error_deleting_character, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}