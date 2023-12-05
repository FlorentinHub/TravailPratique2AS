package com.example.travailpratique3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

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
                // Ne rien faire ici
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Ne rien faire ici
            }
        });

        btnAddPerso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPerso();
            }
        });
    }

    private void addPerso() {
        String nom = tiet_nom.getText().toString().trim();
        String description = tiet_description.getText().toString().trim();
        int level = seekBarLevel.getProgress();
        int healthPoints = seekBarHealthPoints.getProgress();

        // Vérifie que le nom n'est pas vide avant d'ajouter
        if (!nom.isEmpty()) {
            String id = databaseReference.push().getKey();
            Personnage personnage = new Personnage(id, nom, description, level, healthPoints);
            databaseReference.child(id).setValue(personnage);

            // Efface les champs après l'ajout
            tiet_nom.setText("");
            tiet_description.setText("");
            seekBarLevel.setProgress(0);
            seekBarHealthPoints.setProgress(0);
        }
    }
}