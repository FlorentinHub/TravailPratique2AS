package com.example.travailpratique3;

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
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travailpratique3.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseDatabase bd;
    DatabaseReference ref;
    private Button btn_gotoAddPerso;
    private FirebaseAuth mAuth;
    private Spinner spinnerPersonnages;
    private EditText editTextNom, editTextDescription, editValLevel, editValHp;
    private Button buttonModifier, buttonSupprimer, buttonEnregistrer;

    private Map<String, Personnage> personnagesMap; // Hashmap de mes personnages
    private boolean champsEditable = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        // Ajout de la barre de menu personnalisée
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button btn_gotoAddPerso = findViewById(R.id.btn_gotoAddPersonnage);

        spinnerPersonnages = findViewById(R.id.spinner_personnages);
        editTextNom = findViewById(R.id.edit_text_nom);
        editTextDescription = findViewById(R.id.edit_text_description);
        editValHp = findViewById(R.id.edit_val_hp);
        editValLevel = findViewById(R.id.edit_val_level);

        // Désactiver les champs EditText
        editTextNom.setEnabled(false);
        editTextDescription.setEnabled(false);
        editValLevel.setEnabled(false);
        editValHp.setEnabled(false);
    //  buttonEnregistrer.setVisibility(View.GONE);

        // Référencez les autres champs pour les détails du personnage
        buttonModifier = findViewById(R.id.btn_modifier);
        buttonSupprimer = findViewById(R.id.btn_supprimer);
        buttonEnregistrer = findViewById(R.id.btn_Enregistrer);

        // Initialisez et remplissez le Spinner avec les noms des personnages
        initializeSpinner();
        mAuth = FirebaseAuth.getInstance();

        // Vérifie si l'utilisateur est connecté
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Redirige vers l'activité de connexion si l'utilisateur n'est pas connecté
            Intent intent = new Intent(MainActivity.this, ConnexionActivity.class);
            startActivity(intent);
            finish(); // Empêche l'utilisateur de revenir à cette activité via le bouton "Retour"
        }
        buttonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click","buttonModifier is clicked");
                    if (champsEditable) {
                        // Sauvegarder les modifications
                        enregistrerModifications();

                        // Désactiver les champs EditText
                        editTextNom.setEnabled(false);
                        editTextDescription.setEnabled(false);
                        editValLevel.setEnabled(false);
                        editValHp.setEnabled(false);

                        // Désactiver le bouton Enregistrer
                        buttonEnregistrer.setEnabled(false);
                        buttonEnregistrer.setVisibility(View.GONE);
                        champsEditable = false;
                    } else {
                        // Activer les champs EditText pour la modification
                        editTextNom.setEnabled(true);
                        editTextDescription.setEnabled(true);
                        editValLevel.setEnabled(true);
                        editValHp.setEnabled(true);

                        // Activer le bouton Enregistrer
                        buttonEnregistrer.setEnabled(true);
                        buttonEnregistrer.setVisibility(View.VISIBLE);
                        champsEditable = true;
                    }
                }
            });

        // Set onClickListener for btn_gotoAddPerso
        btn_gotoAddPerso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to the RegisterActivity
                startActivity(new Intent(MainActivity.this, AddPersoActivitiy.class));
            }
        });

        // Écouteur de sélection d'élément dans le Spinner
        spinnerPersonnages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Affichez les détails du personnage sélectionné
                String nom = spinnerPersonnages.getSelectedItem().toString();
                Personnage selectedPersonnage = personnagesMap.get(nom);
                displaySelectedPersonnage(selectedPersonnage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Gérez le cas où aucun élément n'est sélectionné dans le Spinner
            }
        });

        // Écouteurs de clic pour les boutons Modifier et Supprimer
        buttonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Modifier le personnage sélectionné
                modifySelectedPersonnage();
            }
        });

        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Supprimer le personnage sélectionné
                deleteSelectedPersonnage();
            }
        });
    }
    private void enregistrerModifications() {
        // Récupérer les nouvelles valeurs des champs EditText
        String nom = editTextNom.getText().toString();
        String description = editTextDescription.getText().toString();
        int niveau = Integer.parseInt(editValLevel.getText().toString());
        int pointsDeVie = Integer.parseInt(editValHp.getText().toString());

        // Mettre à jour l'objet Personnage sélectionné dans la structure de données ou la base de données
        // personnagesMap contient votre Personnage sélectionné

        // Exemple pour la mise à jour dans personnagesMap :
        Personnage selectedPersonnage = personnagesMap.get(spinnerPersonnages.getSelectedItem().toString());
        if (selectedPersonnage != null) {
            selectedPersonnage.setNom(nom);
            selectedPersonnage.setDescription(description);
            selectedPersonnage.setNiveau(niveau);
            selectedPersonnage.setPointsDeVie(pointsDeVie);
            // Mettre à jour l'objet Personnage dans la structure de données
            personnagesMap.put(selectedPersonnage.getNom(), selectedPersonnage);
        }

        //Refresh le spinner:
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, new ArrayList<>(personnagesMap.keySet()));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPersonnages.setAdapter(spinnerAdapter);
    }
    // Initialise le Spinner avec les noms des personnages
    private void initializeSpinner() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Personnages");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                personnagesMap = new HashMap<>();
                List<String> spinnerItems = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id").getValue(String.class);
                    String nom = snapshot.child("nom").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    int niveau = snapshot.child("niveau").getValue(Integer.class);
                    int pointsDeVie = snapshot.child("pointsDeVie").getValue(Integer.class);

                    Personnage personnage = new Personnage(id, nom, description, niveau, pointsDeVie);
                    String spinnerItem = personnage.getNom();
                    spinnerItems.add(spinnerItem);

                    personnagesMap.put(spinnerItem, personnage);
                }

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, spinnerItems);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPersonnages.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gérer les erreurs de chargement des données depuis Firebase
            }
        });
    }




    // Affiche les détails du personnage sélectionné
    private void displaySelectedPersonnage(Personnage personnage) {
        if (personnage != null) {
            editTextNom.setText(personnage.getNom());
            editTextDescription.setText(personnage.getDescription());
            editValLevel.setText(Integer.toString(personnage.getNiveau()));
            editValHp.setText(Integer.toString(personnage.getPointsDeVie()));
        }
    }

    // Modifie le personnage sélectionné
    private void modifySelectedPersonnage() {
        // Récupérez le personnage sélectionné depuis la structure de données (personnagesMap)
        // Modifiez les détails du personnage selon les valeurs des champs d'entrée
        // Mettez à jour la structure de données avec les nouvelles valeurs du personnage modifié
    }

    // Supprime le personnage sélectionné
    private void deleteSelectedPersonnage() {
        // Récupérez le nom du personnage sélectionné depuis le Spinner
        String nomPersonnage = spinnerPersonnages.getSelectedItem().toString();
        // Supprimez le personnage correspondant de la structure de données (personnagesMap)
        // Mettez à jour l'interface utilisateur (champs d'entrée) en vidant les champs après la suppression
        editTextNom.setText("");
        // Videz les autres champs correspondant aux détails du personnage
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