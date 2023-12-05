package com.example.travailpratique3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class AddPersoActivitiy extends AppCompatActivity {
    TextInputEditText tiet_nom, tiet_description;
    SeekBar seekBarLevel, seekBarHealthPoints;
    TextView textViewLevel, textViewHealthPoints;
    Button btnAddPerso;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_perso_activitiy);

        tiet_nom = findViewById(R.id.edit_text_name);
        tiet_description = findViewById(R.id.edit_text_description);
        seekBarLevel = findViewById(R.id.seek_bar_level);
        seekBarHealthPoints = findViewById(R.id.seek_bar_health_points);
        textViewLevel = findViewById(R.id.text_view_level);
        textViewHealthPoints = findViewById(R.id.text_view_health_points);
        btnAddPerso = findViewById(R.id.button_add_perso);

        textViewLevel.setText("Niveau: " + 0);
        textViewHealthPoints.setText("Points de Vie: " + 50);

        databaseReference = FirebaseDatabase.getInstance().getReference("Personnages");

        seekBarLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewLevel.setText("Niveau: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Ne rien faire ici
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Ne rien faire ici
            }
        });
        seekBarHealthPoints.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewHealthPoints.setText("Points de Vie: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btnAddPerso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPerso();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        retour();
    }

    private void addPerso() {
        String nom = tiet_nom.getText().toString().trim();
        String description = tiet_description.getText().toString().trim();
        int level = seekBarLevel.getProgress();
        int healthPoints = seekBarHealthPoints.getProgress();
        tiet_nom.setError(null);
        tiet_description.setError(null);

        // Vérifie que le nom n'est pas vide avant d'ajouter
        if (!nom.isEmpty()) {
            if(!description.isEmpty()) {
                    String id = databaseReference.push().getKey();
                    Personnage personnage = new Personnage(id, nom, description, level, healthPoints);
                    Toast.makeText(AddPersoActivitiy.this, "Nouveau Personnage " + personnage.getNom(), Toast.LENGTH_SHORT).show();
                    databaseReference.child(id).setValue(personnage);

                    // Efface les champs après l'ajout
                    tiet_nom.setText("");
                    tiet_description.setText("");
                    seekBarLevel.setProgress(0);
                    seekBarHealthPoints.setProgress(0);

                    //Methode venant de StackOverFlow: wait-for-5-seconds
                    Handler handler = new Handler();
                    handler.postDelayed(() -> retour(), 4);   //5 seconds
            }else{
                tiet_description.setError("Veuillez saisir une description");
                tiet_description.requestFocus();
            }
        }else {
            tiet_nom.setError("Veuillez saisir un nom");
            tiet_nom.requestFocus();
        }
    }
    private void retour(){
        startActivity(new Intent(AddPersoActivitiy.this, MainActivity.class));
        finish();
    }
}