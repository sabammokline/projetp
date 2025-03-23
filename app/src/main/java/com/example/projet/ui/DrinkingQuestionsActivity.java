package com.example.projet.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.room.Room;

import com.example.projet.R;
import com.example.projet.database.User;
import com.example.projet.database.database;
import com.example.projet.dao.UserDao;

public class DrinkingQuestionsActivity extends Activity {

    private RadioGroup radioGroupDoYouDrink;
    private RadioButton radioDrinkYes, radioDrinkNo;
    private LinearLayout drinkingQuestionsLayout;
    private NumberPicker numberPickerBottlesPerDay;
    private EditText editBottleCost;
    private Button buttonNextDrink;

    private database db;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drinkingquestions);

        // Initialize Room database
        db = Room.databaseBuilder(getApplicationContext(), database.class, "health-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        // Get the User object from the intent
        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        // Initialize views
        radioGroupDoYouDrink = findViewById(R.id.radioGroupDoYouDrink);
        radioDrinkYes = findViewById(R.id.radioDrinkYes);
        radioDrinkNo = findViewById(R.id.radioDrinkNo);
        drinkingQuestionsLayout = findViewById(R.id.drinkingQuestionsLayout);
        numberPickerBottlesPerDay = findViewById(R.id.numberPickerBottlesPerDay);
        editBottleCost = findViewById(R.id.editBottleCost);
        buttonNextDrink = findViewById(R.id.buttonNextDrink);

        numberPickerBottlesPerDay.setMinValue(0);
        numberPickerBottlesPerDay.setMaxValue(20);

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
                int bottlesPerWeek = numberPickerBottlesPerDay.getValue();
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

                // Save to Room DB
                db.userDao().update(currentUser);
            }

            // Proceed to WeightQuestionsActivity
            Intent intent = new Intent(this, WeightQuestionsActivity.class);
            intent.putExtra("currentUser", currentUser);
            startActivity(intent);
        });
    }
}
