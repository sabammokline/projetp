package com.example.projet.entities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.R;
import com.example.projet.database.database;
import com.example.projet.database.User;
import com.example.projet.ui.CertSpecialityActivity;
import com.example.projet.ui.SmokingQuestionsActivity;

public class UserInfoActivity extends AppCompatActivity {

    EditText editNom, editPrenom, editEmail, editPhone, editUserName; // Added username field
    RadioGroup radioGroupRole;
    Button buttonNext;
    private database db;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Initialize Room database
        db = database.getInstance(getApplicationContext());

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
            User user = new User();
            user.setNom(nom);
            user.setPrenom(prenom);
            user.setEmail(email);
            user.setNumTel(phone);
            user.setUserName(userName);
            user.setPassword(null);

            switch (role) {
                case "user":
                    user.setCoach(false);
                    user.setUser(true);
                    user.setMedecin(false);

                    intent = new Intent(this, SmokingQuestionsActivity.class);
                    intent.putExtra("currentUser", user);
                    startActivity(intent);

                    break;
                case "doctor": {
                    user.setCoach(false);
                    user.setUser(false);
                    user.setMedecin(true);

                    intent = new Intent(this, CertSpecialityActivity.class);
                    intent.putExtra("currentUser", user);
                    startActivity(intent);

                    break;
                }
                case "coach": {
                    user.setCoach(true);
                    user.setUser(false);
                    user.setMedecin(false);

                    intent = new Intent(this, CertSpecialityActivity.class);
                    intent.putExtra("currentUser", user);
                    startActivity(intent);

                    break;
                }
            }

        });
    }
}
