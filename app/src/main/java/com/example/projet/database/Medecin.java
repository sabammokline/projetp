package com.example.projet.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.projet.entities.Login;

@Entity(tableName = "medecin_table")
public class Medecin extends Login {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String certificateCode;
    private String specialty;

    // Getters and setters...
    public void setCertificateCode(String  certificateCode) {
        this.certificateCode = certificateCode;
    }

    public String getCertificateCode() {
        return certificateCode;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

