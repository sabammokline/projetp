package com.example.projet.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet.MainActivity;
import com.example.projet.R;
import com.example.projet.database.User;
import com.example.projet.database.database;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CertSpecialityActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private EditText editTextCert, editTextSpeciality;
    private ImageButton buttonFinish;
    private database db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certspeciality);

        db = database.getInstance(getApplicationContext());

        editTextCert = findViewById(R.id.editTextCert);
        editTextSpeciality = findViewById(R.id.editTextSpeciality);
        buttonFinish = findViewById(R.id.buttonSubmit);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("currentUser");

        buttonFinish.setOnClickListener(v -> {
            // Validate input fields
            String certCode = editTextCert.getText().toString().trim();
            String speciality = editTextSpeciality.getText().toString().trim();

            if (certCode.isEmpty() || speciality.isEmpty()) {
                Toast.makeText(CertSpecialityActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            user.setNumCertificate(certCode);
            user.setSpecialty(speciality);

            executor.execute(() -> {
                // Background thread work
                long insertedId = db.userDao().insert(user);

                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("userId", insertedId);
                editor.apply(); // Or use editor.commit() for immediate write

                // Update UI (must run on UI thread)
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Insert done", Toast.LENGTH_SHORT).show();
                });
            });

            // Show a success toast
            Toast.makeText(CertSpecialityActivity.this, "Information saved successfully", Toast.LENGTH_SHORT).show();

            // Navigate to MainActivity and clear the back stack


            Intent intentToMain = new Intent(CertSpecialityActivity.this, MainActivity.class);
            intentToMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentToMain);
        });
    }
}
