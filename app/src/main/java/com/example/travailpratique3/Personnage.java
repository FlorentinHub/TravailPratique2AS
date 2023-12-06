package com.example.travailpratique3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Personnage implements Parcelable {
    private String id;
    private String nom;
    private String description;
    private int niveau;
    private int pointsDeVie;

    public Personnage() {
        // Constructeur vide requis pour Firebase
    }

    public Personnage(String id, String nom, String description, int niveau, int pointsDeVie) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.niveau = niveau;
        this.pointsDeVie = pointsDeVie;
    }

    protected Personnage(Parcel in) {
        id = in.readString();
        nom = in.readString();
        description = in.readString();
        niveau = in.readInt();
        pointsDeVie = in.readInt();
    }

    public static final Creator<Personnage> CREATOR = new Creator<Personnage>() {
        @Override
        public Personnage createFromParcel(Parcel in) {
            return new Personnage(in);
        }

        @Override
        public Personnage[] newArray(int size) {
            return new Personnage[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getPointsDeVie() {
        return pointsDeVie;
    }

    public void setPointsDeVie(int pointsDeVie) {
        this.pointsDeVie = pointsDeVie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nom);
        dest.writeString(description);
        dest.writeInt(niveau);
        dest.writeInt(pointsDeVie);
    }
}
