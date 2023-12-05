package com.example.travailpratique3;

public class Personnage {
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
}
