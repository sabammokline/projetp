package com.example.projet.entities;

import java.io.Serializable;

public class Login implements Serializable {
    private String nom;
    private String prenom;
    private String email;
    private String numTel;
    private String userName;

    private boolean user;
    private boolean coach;
    private boolean medecin;

    // Getters and Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public boolean isCoach() {
        return coach;
    }

    public void setCoach(boolean coach) {
        this.coach = coach;
    }

    public boolean isMedecin() {
        return medecin;
    }

    public void setMedecin(boolean medecin) {
        this.medecin = medecin;
    }
}
