package com.example.projet.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.projet.MainActivity;
import com.example.projet.R;
import com.example.projet.database.User;
import com.example.projet.database.database;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeightQuestionsActivity extends AppCompatActivity {

    private RadioGroup radioGroupDoYouTrackWeight, radioGroupWeightType, radioGroupGender;
    private RadioButton radioTrackYes, radioTrackNo;
    private LinearLayout weightQuestionsLayout;
    private EditText editAge, editHeight, editWeight, editGoalWeight, editExerciseFrequency;
    private Button buttonNext;

    private User currentUser;
    private database db;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weightquestions);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        radioGroupDoYouTrackWeight = findViewById(R.id.radioGroupDoYouTrackWeight);
        radioTrackYes = findViewById(R.id.radioTrackYes);
        radioTrackNo = findViewById(R.id.radioTrackNo);
        weightQuestionsLayout = findViewById(R.id.weightQuestionsLayout);
        radioGroupWeightType = findViewById(R.id.radioGroupWeightType);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        editHeight = findViewById(R.id.editHeight);
        editWeight = findViewById(R.id.editWeight);
        editGoalWeight = findViewById(R.id.editGoalWeight); // New field
        editExerciseFrequency = findViewById(R.id.editExerciseFrequency);
        buttonNext = findViewById(R.id.buttonNext);


        db = database.getInstance(getApplicationContext());

        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        radioGroupDoYouTrackWeight.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioTrackYes) {
                weightQuestionsLayout.setVisibility(View.VISIBLE);
            } else {
                weightQuestionsLayout.setVisibility(View.GONE);
            }
        });

        buttonNext.setOnClickListener(v -> {
            int selectedId = radioGroupDoYouTrackWeight.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Please select if you want to track your weight.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedId == R.id.radioTrackYes) {
                String ageStr = editAge.getText().toString().trim();
                String heightStr = editHeight.getText().toString().trim();
                String weightStr = editWeight.getText().toString().trim();
                String goalWeightStr = editGoalWeight.getText().toString().trim(); // New field
                String exerciseStr = editExerciseFrequency.getText().toString().trim();

                if (ageStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() ||
                        goalWeightStr.isEmpty() || exerciseStr.isEmpty() || // Added goal weight check
                        radioGroupWeightType.getCheckedRadioButtonId() == -1 ||
                        radioGroupGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                currentUser.setTrackWeight(true);
                currentUser.setHeight(Double.parseDouble(heightStr));
                currentUser.setWeight(Double.parseDouble(weightStr));
                currentUser.setGoalweight(Double.parseDouble(goalWeightStr)); // New field
                currentUser.setWeeklyActivities(Integer.parseInt(exerciseStr));

                int selectedWeightTypeId = radioGroupWeightType.getCheckedRadioButtonId();
                currentUser.setStatus(selectedWeightTypeId == R.id.radioOverweight ? "Overweight" : "Underweight");

                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                currentUser.setGender(selectedGenderId == R.id.radioMale ? "Male" : "Female");

            } else {
                currentUser.setTrackWeight(false);
            }

            executor.execute(() -> {
                // Background thread work
                long insertedId = db.userDao().insert(currentUser);

                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("userId", insertedId);
                editor.apply(); // Or use editor.commit() for immediate write

                // Update UI (must run on UI thread)
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Insert done", Toast.LENGTH_SHORT).show();
                });
            });

            SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isProfileCreated", true); // Set to true
            editor.apply(); // Or use editor.commit() for immediate write


            Intent intent = new Intent(WeightQuestionsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}