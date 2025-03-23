package com.example.projet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Room;

import com.example.projet.MainActivity;
import com.example.projet.R;
import com.example.projet.dao.MedecinDao;
import com.example.projet.dao.UserDao;
import com.example.projet.database.database;
import com.example.projet.database.Medecin;
import com.example.projet.database.Coach;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CertSpecialityActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private EditText editTextCert, editTextSpeciality;
    private ImageButton buttonFinish;
    private database db;
    private int doctorId = -1;
    private int coachId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certspeciality);

        db = Room.databaseBuilder(getApplicationContext(), database.class, "health-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        editTextCert = findViewById(R.id.editTextCert);
        editTextSpeciality = findViewById(R.id.editTextSpeciality);
        buttonFinish = findViewById(R.id.buttonFinish);

        Intent intent = getIntent();
        doctorId = intent.getIntExtra("doctorId", -1);
        coachId = intent.getIntExtra("coachId", -1);

        buttonFinish.setOnClickListener(v -> {
            // Validate input fields
            String certCode = editTextCert.getText().toString().trim();
            String speciality = editTextSpeciality.getText().toString().trim();

            if (certCode.isEmpty() || speciality.isEmpty()) {
                Toast.makeText(CertSpecialityActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (doctorId != -1) {
                Medecin doctor = db.medecinDao().getMedecinById(doctorId);
                if (doctor != null) {
                    doctor.setCertificateCode(certCode);
                    doctor.setSpecialty(speciality);
                    db.medecinDao().update(doctor); // Update the doctor info
                }
            } else if (coachId != -1) {
                Coach coach = db.coachDao().getById(coachId);
                if (coach != null) {
                    coach.setCertificateCode(certCode);
                    coach.setSpecialty(speciality);
                    db.coachDao().update(coach); // Update the coach info
                }
            }

            // Show a success toast
            Toast.makeText(CertSpecialityActivity.this, "Information saved successfully", Toast.LENGTH_SHORT).show();

            // Navigate to MainActivity and clear the back stack
            Intent intentToMain = new Intent(CertSpecialityActivity.this, MainActivity.class);
            intentToMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentToMain);
        });
    }
}
