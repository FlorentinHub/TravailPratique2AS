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
        // Vérifie si une vue existante est utilisée, sinon inflat une nouvelle vue
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        // Récupère l'objet Personnage pour cette position
        Personnage personnage = getItem(position);

        // Assurez-vous que le personnage n'est pas nul
        if (personnage != null) {
            // Trouvez la vue TextView dans le layout simple_list_item_1
            TextView textView = convertView.findViewById(android.R.id.text1);

            // Définissez le texte du TextView comme le nom du personnage
            textView.setText(personnage.getNom());
        }

        // Renvoie la vue correctement configurée pour l'affichage
        return convertView;
    }
}
