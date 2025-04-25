package com.example.projet.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.projet.R;
import com.example.projet.database.User;
import com.example.projet.database.database;
import com.example.projet.dao.UserDao;
import com.google.android.material.slider.Slider;

public class DrinkingQuestionsActivity extends AppCompatActivity {

    private RadioGroup radioGroupDoYouDrink;
    private RadioButton radioDrinkYes, radioDrinkNo;
    private CardView drinkingQuestionsLayout;
    private Slider numberPickerBottlesPerDay;
    private EditText editBottleCost;
    private Button buttonNextDrink;

    private database db;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drinkingquestions);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }




        // Get the User object from the intent
        Log.wtf("ee", "created");
        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        // Initialize views
        radioGroupDoYouDrink = findViewById(R.id.radioGroupDoYouDrink);
        radioDrinkYes = findViewById(R.id.radioDrinkYes);
        radioDrinkNo = findViewById(R.id.radioDrinkNo);
        drinkingQuestionsLayout = findViewById(R.id.drinkingQuestionsLayout);
        numberPickerBottlesPerDay = findViewById(R.id.sliderBottlesPerDay);
        editBottleCost = findViewById(R.id.editBottleCost);
        buttonNextDrink = findViewById(R.id.buttonNextDrink);

        numberPickerBottlesPerDay.setValueFrom(1);
        numberPickerBottlesPerDay.setValueTo(20);
        numberPickerBottlesPerDay.setValue(1);



        radioGroupDoYouDrink.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioDrinkYes) {
                drinkingQuestionsLayout.setVisibility(View.VISIBLE);
            } else {
                drinkingQuestionsLayout.setVisibility(View.GONE);
            }
        });

        buttonNextDrink.setOnClickListener(v -> {
            int selectedId = radioGroupDoYouDrink.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Please answer if you drink.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedId == R.id.radioDrinkYes) {
                boolean drinking = true;
                int bottlesPerWeek = (int)numberPickerBottlesPerDay.getValue();
                String costStr = editBottleCost.getText().toString().trim();

                if (costStr.isEmpty()) {
                    Toast.makeText(this, "Please fill in all drinking-related questions.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double bottleCost = Double.parseDouble(costStr);

                // Update user object
                currentUser.setDrinking(drinking);
                currentUser.setBottlesPerWeek(bottlesPerWeek);
                currentUser.setBottleCost(bottleCost);

            }

            // Proceed to WeightQuestionsActivity
            Intent intent = new Intent(this, WeightQuestionsActivity.class);
            intent.putExtra("currentUser", currentUser);
            startActivity(intent);
        });

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            // Go back to SmokingQuestionsActivity and pass the currentUser
            Intent intent = new Intent(this, SmokingQuestionsActivity.class);
            intent.putExtra("currentUser", currentUser);
            startActivity(intent);
            finish(); // Close the current activity (DrinkingQuestionsActivity)
        });

}}
