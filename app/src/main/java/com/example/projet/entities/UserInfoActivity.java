package com.example.projet.entities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.projet.R;
import com.example.projet.database.database;
import com.example.projet.database.User;
import com.example.projet.database.Medecin;
import com.example.projet.database.Coach;
import com.example.projet.ui.CertSpecialityActivity;
import com.example.projet.ui.SmokingQuestionsActivity;

public class UserInfoActivity extends AppCompatActivity {

    EditText editNom, editPrenom, editEmail, editPhone, editUserName; // Added username field
    RadioGroup radioGroupRole;
    ImageButton buttonNext;
    private database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Initialize Room database
        db = Room.databaseBuilder(getApplicationContext(), database.class, "health-db")
                .allowMainThreadQueries() // Use for quick testing only! Replace with async or ViewModel for production
                .fallbackToDestructiveMigration()
                .build();

        editNom = findViewById(R.id.editTextNom);
        editPrenom = findViewById(R.id.editTextPrenom);
        editEmail = findViewById(R.id.editTextEmail);
        editPhone = findViewById(R.id.editTextPhone);
        editUserName = findViewById(R.id.editTextUserName); // Initialize the username field
        radioGroupRole = findViewById(R.id.radioGroupRole);
        buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(v -> {
            String nom = editNom.getText().toString().trim();
            String prenom = editPrenom.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String userName = editUserName.getText().toString().trim(); // Get the username

            int selectedId = radioGroupRole.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRole = findViewById(selectedId);
            String role = selectedRole.getText().toString().toLowerCase();

            // Check the role and insert the data into the appropriate table
            if (role.equals("user")) {
                User user = new User();
                user.setNom(nom);
                user.setPrenom(prenom);
                user.setEmail(email);
                user.setNumTel(phone);
                user.setUserName(userName); // Set the username
                db.userDao().insert(user); // Insert into User table

                // Set the profile created flag in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isProfileCreated", true);
                editor.apply();

                Intent intent = new Intent(this, SmokingQuestionsActivity.class);
                intent.putExtra("currentUser", user);
                startActivity(intent);
            } else if (role.equals("doctor")) {
                Medecin doctor = new Medecin();
                doctor.setNom(nom);
                doctor.setPrenom(prenom);
                doctor.setEmail(email);
                doctor.setNumTel(phone);
                doctor.setUserName(userName); // Set the username
                db.medecinDao().insert(doctor); // Insert into Doctor table

                // Set the profile created flag in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isProfileCreated", true);
                editor.apply();

                Intent intent = new Intent(this, CertSpecialityActivity.class);
                intent.putExtra("doctorId", doctor.getId());
                startActivity(intent);
            } else if (role.equals("coach")) {
                Coach coach = new Coach();
                coach.setNom(nom);
                coach.setPrenom(prenom);
                coach.setEmail(email);
                coach.setNumTel(phone);
                coach.setUserName(userName); // Set the username
                db.coachDao().insert(coach); // Insert into Coach table

                // Set the profile created flag in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isProfileCreated", false);
                editor.apply();

                Intent intent = new Intent(this, CertSpecialityActivity.class);
                intent.putExtra("coachId", coach.getId());
                startActivity(intent);
            }
        });
    }
}
