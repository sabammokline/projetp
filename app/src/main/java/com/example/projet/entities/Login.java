package com.example.projet.entities;

import java.io.Serializable;

public class Login implements Serializable {
    protected String nom;
    protected String prenom;
    protected String email;
    protected String numTel;
    protected String userName;

    protected boolean user;
    protected boolean coach;
    protected boolean medecin;
    protected String numCertificate;
    protected String specialty;

    // Getters and Setters

    public void setNumCertificate(String  NumCertificate) {
        this.numCertificate = NumCertificate;
    }

    public String getNumCertificate() {
        return numCertificate;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }
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

    public boolean getUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public boolean getCoach() {
        return coach;
    }

    public void setCoach(boolean coach) {
        this.coach = coach;
    }

    public boolean getMedecin() {
        return medecin;
    }

    public void setMedecin(boolean medecin) {
        this.medecin = medecin;
    }
}
