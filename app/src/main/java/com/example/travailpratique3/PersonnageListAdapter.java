package com.example.travailpratique3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PersonnageListAdapter extends ArrayAdapter<Personnage> {

    public PersonnageListAdapter(Context context, List<Personnage> personnages) {
        super(context, 0, personnages);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        Personnage personnage = getItem(position);

        if (personnage != null) {
            TextView textView = convertView.findViewById(android.R.id.text1);

            textView.setText(personnage.getNom());
        }
        return convertView;
    }
}
