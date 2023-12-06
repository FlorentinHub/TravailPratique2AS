package com.example.travailpratique3;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class PersonnagesAdapter extends BaseAdapter {
    List<Personnage> mesPersonnages;

    public PersonnagesAdapter(List<Personnage> mesPersonnages) {
        this.mesPersonnages = mesPersonnages;
    }
    @Override
    public int getCount() {
        return mesPersonnages.size();
    }

    @Override
    public Object getItem(int position) {
        return mesPersonnages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
