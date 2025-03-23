package com.example.projet.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;


import com.example.projet.entities.Login;

@Entity(tableName = "user_table")
public class User extends Login  {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String birthdate;
    private Boolean smoking;
    private int cigarettesPerPack;

    private int packsPerDay;
    private double cigaretteCost;
    private String debutDate;
    private String quitDate;

    private Boolean drinking;
    private int bottlesPerWeek;
    private double bottleCost;
    private Boolean trackWeight;


    private double weight;
    private double height;

    private int age;
    private String gender;

    private String status;
    private int weeklyActivities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean getSmoking() {
        return smoking;
    }

    public void setSmoking(Boolean smoking) {
        this.smoking = smoking;
    }

    public int getCigarettesPerPack() {
        return cigarettesPerPack;
    }

    public void setCigarettesPerPack(int cigarettesPerPack) {
        this.cigarettesPerPack = cigarettesPerPack;
    }

    public int getPacksPerDay() {
        return packsPerDay;
    }

    public void setPacksPerDay(int packsPerDay ) {
        this.packsPerDay = packsPerDay ;
    }

    public double getCigaretteCost() {
        return cigaretteCost;
    }

    public void setCigaretteCost(double cigaretteCost ) {
        this.cigaretteCost = cigaretteCost ;
    }

    public String getDebutDate() {
        return debutDate;
    }

    public void setDebutDate(String debutDate) {
        this.debutDate = debutDate;
    }
    public String getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(String quitDate) {
        this.quitDate = quitDate;
    }


    public Boolean getDrinking() {
        return drinking;
    }

    public void setDrinking(Boolean drinking) {
        this.drinking = drinking;
    }

    public int getBottlesPerWeek() {
        return bottlesPerWeek;
    }

    public void setBottlesPerWeek(int bottlesPerWeek) {
        this.bottlesPerWeek = bottlesPerWeek;
    }

    public double getBottleCost() {
        return bottleCost;
    }

    public void setBottleCost(double bottleCost) {
        this.bottleCost = bottleCost;
    }

    public Boolean getTrackWeight() {
        return trackWeight;
    }

    public void setTrackWeight(Boolean TrackWeight) {
        this.trackWeight = TrackWeight;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWeeklyActivities() {
        return weeklyActivities;
    }

    public void setWeeklyActivities(int weeklyActivities) {
        this.weeklyActivities = weeklyActivities;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = Status;
    }
}

