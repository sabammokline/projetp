package com.example.projet.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.projet.R;
import com.example.projet.database.database;
import com.example.projet.database.User;

import java.util.Calendar;

public class SmokingQuestionsActivity extends AppCompatActivity {

    RadioGroup radioGroupDoYouSmoke;
    RadioButton radioYes, radioNo;
    LinearLayout smokingQuestionsLayout;
    NumberPicker numberPickerPacksPerDay, numberPickerCigarettesPerPack;
    EditText editCigaretteCost, editQuitDate, editDebutDate;
    Button buttonNext;
    private database db;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smokingquestions);

        // Initialize Room database
        db = Room.databaseBuilder(getApplicationContext(), database.class, "health-db")
                .allowMainThreadQueries() // Quick testing only! Replace with async tasks for production
                .fallbackToDestructiveMigration()
                .build();

        // Initialize views
        radioGroupDoYouSmoke = findViewById(R.id.radioGroupDoYouSmoke);
        radioYes = findViewById(R.id.radioYes);
        radioNo = findViewById(R.id.radioNo);
        smokingQuestionsLayout = findViewById(R.id.smokingQuestionsLayout);
        numberPickerPacksPerDay = findViewById(R.id.numberPickerPacksPerDay);
        numberPickerCigarettesPerPack = findViewById(R.id.numberPickerCigarettesPerPack);
        editCigaretteCost = findViewById(R.id.editCigaretteCost);
        editQuitDate = findViewById(R.id.editQuitDate);
        editDebutDate = findViewById(R.id.editDebutDate);  // New debut date EditText
        buttonNext = findViewById(R.id.buttonNext);

        // Set up number pickers
        numberPickerPacksPerDay.setMinValue(1);
        numberPickerPacksPerDay.setMaxValue(50);
        numberPickerCigarettesPerPack.setMinValue(1);
        numberPickerCigarettesPerPack.setMaxValue(50);

        // Initially hide the smoking questions layout
        smokingQuestionsLayout.setVisibility(View.GONE);

        // Set up DatePicker for quit date
        editQuitDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(SmokingQuestionsActivity.this,
                    (view, year1, month1, dayOfMonth1) -> {
                        String selectedDate = year1 + "-" + (month1 + 1) + "-" + dayOfMonth1;
                        editQuitDate.setText(selectedDate);
                    }, year, month, dayOfMonth);
            datePickerDialog.show();
        });

        // Set up DatePicker for debut date (new field)
        editDebutDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(SmokingQuestionsActivity.this,
                    (view, year1, month1, dayOfMonth1) -> {
                        String selectedDate = year1 + "-" + (month1 + 1) + "-" + dayOfMonth1;
                        editDebutDate.setText(selectedDate);
                    }, year, month, dayOfMonth);
            datePickerDialog.show();
        });

        // Get the User object passed from UserInfoActivity
        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        // Handle "Do you smoke?" radio button clicks
        radioGroupDoYouSmoke.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == radioYes.getId()) {
                smokingQuestionsLayout.setVisibility(View.VISIBLE);
            } else {
                smokingQuestionsLayout.setVisibility(View.GONE);
            }
        });

        // Handle the "Next" button click
        buttonNext.setOnClickListener(v -> {
            if (radioYes.isChecked()) {
                // Collect and validate smoking-related information
                boolean smoking = true;
                int packsPerDay = numberPickerPacksPerDay.getValue();
                int cigarettesPerPack = numberPickerCigarettesPerPack.getValue();
                String cigaretteCostStr = editCigaretteCost.getText().toString().trim();
                String quitDate = editQuitDate.getText().toString().trim();
                String debutDate = editDebutDate.getText().toString().trim();  // Collect debut date

                if (cigaretteCostStr.isEmpty() || quitDate.isEmpty() || debutDate.isEmpty()) {
                    Toast.makeText(SmokingQuestionsActivity.this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double cigaretteCost = Double.parseDouble(cigaretteCostStr);

                // Update currentUser object with smoking-related data
                currentUser.setSmoking(smoking);
                currentUser.setPacksPerDay(packsPerDay);
                currentUser.setCigarettesPerPack(cigarettesPerPack);
                currentUser.setCigaretteCost(cigaretteCost);
                currentUser.setQuitDate(quitDate);
                currentUser.setDebutDate(debutDate);  // Set debut date


                // Move to the next activity (e.g., DrinkingQuestionsActivity)
                Intent intent = new Intent(SmokingQuestionsActivity.this, DrinkingQuestionsActivity.class);
                intent.putExtra("currentUser", currentUser);

                startActivity(intent);

            } else {
                // If "No" is selected, skip questions and move to the next activity
                Intent intent = new Intent(SmokingQuestionsActivity.this, DrinkingQuestionsActivity.class);
                intent.putExtra("currentUser", currentUser);

                startActivity(intent);
            }
        });
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            // Go back to SmokingQuestionsActivity and pass the currentUser
            Intent intent = new Intent(this, DrinkingQuestionsActivity.class);
            intent.putExtra("currentUser", currentUser);
            startActivity(intent);
            finish(); // Close the current activity (DrinkingQuestionsActivity)
        });
    }
}
