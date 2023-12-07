package com.example.travailpratique3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentPersonnages extends Fragment {
    private EditText editTextNom, editTextDescription;
    private SeekBar editSeekValHp, editSeekValLevel;
    private TextView text_view_health_points, text_view_level;
    ArrayAdapter<Personnage> adapter;
    private Map<String, Personnage> personnagesMap;

    public FragmentPersonnages() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vue = inflater.inflate(R.layout.fragment_personnages, container, false);
        editTextNom = vue.findViewById(R.id.edit_text_nom);
        editTextDescription = vue.findViewById(R.id.edit_text_description);
        editSeekValHp = vue.findViewById(R.id.seek_bar_health_points);
        editSeekValLevel = vue.findViewById(R.id.seek_bar_level);
        text_view_health_points= vue.findViewById(R.id.text_view_health_points);
        text_view_level= vue.findViewById(R.id.text_view_level);

        editTextNom.setEnabled(false);
        editTextDescription.setEnabled(false);
        editSeekValHp.setEnabled(false);
        editSeekValLevel.setEnabled(false);
        text_view_health_points.setEnabled(false);
        text_view_level.setEnabled(false);

        Bundle bundle = getArguments();
        if (bundle!=null){
            Personnage monPersonnage = bundle.getParcelable("selectedPersonnage");
            editTextNom.setText(monPersonnage.getNom());
            editTextDescription.setText(monPersonnage.getDescription());
            editSeekValHp.setProgress(monPersonnage.getPointsDeVie());
            editSeekValLevel.setProgress(monPersonnage.getNiveau());
        }
        return vue;
    }

}
//
// editSeekValHp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//@Override
//public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        text_view_health_points.setText("Points de Vie: " + progress);
//        }
//
//@Override
//public void onStartTrackingTouch(SeekBar seekBar) {
//        }
//
//@Override
//public void onStopTrackingTouch(SeekBar seekBar) {
//        }
//        });
//        editSeekValLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//@Override
//public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        text_view_level.setText("Niveau: " + progress);
//        }
//
//@Override
//public void onStartTrackingTouch(SeekBar seekBar) {
//        }
//
//@Override
//public void onStopTrackingTouch(SeekBar seekBar) {
//        }
//        });