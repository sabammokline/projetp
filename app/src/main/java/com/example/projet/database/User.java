package com.example.projet.database;


import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.View;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import com.example.projet.entities.Login;

@Entity(tableName = "user_table")
public class User extends Login  {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private int age;
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

    private double goalweight;
    private double height;

    private String gender;

    private String status;
    private int weeklyActivities;
    private String password;

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword(){
        return password;}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int birthdate) {
        this.age = age;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    public double getGoalweight() {
        return goalweight;
    }

    public void setGoalweight(double goalweight) {
        this.goalweight = goalweight;
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

    public int getDailyCalories(){
        return 0;

    }
    public int getWeightProgressPercent(){
        return 0;

    }
    public int getKidneyHealthPercent(){
        return 0;

    }
    public int getDrinkingDaysTracked(){
        return 0;

    }
    public int getDrinksAvoided(){
        return 0;

    }
    public int getDrinkingMoneySaved(){
        return 0;

    }
    public int getLungDamagePercent(){
        return 0;

    }
    public int getTimeWon(){
        return 0;

    }
    public int getMoneySaved(){
        return 0;

    }
    public int getCigarettesAvoided(){
        return 0;

    }
    public int getQuitTime(){
        return 0;

    }

    public int getYearsSmoked() {
        try {
            // Define the date format of the debutDate (make sure it matches the format of the input string)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Parse the debutDate string into a Date object
            Date debutDate = sdf.parse(this.debutDate); // Assuming debutDate is a member variable containing the start date

            // Get the current date
            Date currentDate = new Date();

            // Calculate the difference in milliseconds
            long diffMillis = currentDate.getTime() - debutDate.getTime();

            // Convert the difference from milliseconds to years
            long diffYears = TimeUnit.MILLISECONDS.toDays(diffMillis) / 365; // Approximation of years

            return (int) diffYears;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Return 0 if there's an error with parsing or calculating
        }
    }
    public int getCigarettesAvoidedCount(Context context){
        database db = Room.databaseBuilder(context, database.class, "user_database")
                .allowMainThreadQueries()
                .build();
        List<CigaretteLog> logs = db.cigaretteLogDao().getSmokedDays(id, cigarettesPerPack * packsPerDay);
        int cigsAvoided = 0;
        for (CigaretteLog log : logs){
            cigsAvoided += ((cigarettesPerPack * packsPerDay) - log.getCigarettesSmoked());
        }

        return cigsAvoided;
    }


    public double getMoneySaved(Context context){
        int avoidedCigs = getCigarettesAvoidedCount(context);

        return avoidedCigs * cigaretteCost;
    }

    public int getTimeWonBack(Context context){
        int avoidedCigs = getCigarettesAvoidedCount(context);

        return avoidedCigs * 11;
    }

    @Override
    public String toString() {
        return "User{" +
                // Fields from Login class
                "nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", numTel='" + getNumTel() + '\'' +
                ", userName='" + getUserName() + '\'' +
                ", user=" + getUser() +
                ", coach=" + getCoach() +
                ", medecin=" + getMedecin() +
                ", numCertificate='" + getNumCertificate() + '\'' +
                ", specialty='" + getSpecialty() + '\'' +

                // Fields from User class
                ", id=" + id +
                ", birthdate='" + age + '\'' +
                ", smoking=" + smoking +
                ", cigarettesPerPack=" + cigarettesPerPack +
                ", packsPerDay=" + packsPerDay +
                ", cigaretteCost=" + cigaretteCost +
                ", debutDate='" + debutDate + '\'' +
                ", quitDate='" + quitDate + '\'' +
                ", drinking=" + drinking +
                ", bottlesPerWeek=" + bottlesPerWeek +
                ", bottleCost=" + bottleCost +
                ", trackWeight=" + trackWeight +
                ", weight=" + weight +
                ", goalweight=" + goalweight +
                ", height=" + height +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                ", weeklyActivities=" + weeklyActivities +
                ", password='" + password + '\'' +
                '}';
    }




}

