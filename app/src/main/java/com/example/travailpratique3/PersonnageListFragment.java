package com.example.travailpratique3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PersonnageListFragment extends Fragment {

    public PersonnageListFragment() {
        // Constructeur vide requis
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personnage_list, container, false);
        // Initialisation de la RecyclerView et du gestionnaire de disposition ici
        return view;
    }
}