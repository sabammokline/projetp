package com.example.projet.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;


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

