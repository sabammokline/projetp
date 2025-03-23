package com.example.projet.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.room.Room;

import com.example.projet.MainActivity;
import com.example.projet.R;
import com.example.projet.database.User;
import com.example.projet.database.database;

public class WeightQuestionsActivity extends Activity {

    private RadioGroup radioGroupDoYouTrackWeight, radioGroupWeightType, radioGroupGender;
    private RadioButton radioTrackYes, radioTrackNo;
    private LinearLayout weightQuestionsLayout;
    private EditText editAge, editHeight, editWeight, editExerciseFrequency;
    private Button buttonFinish;

    private User currentUser;
    private database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weightquestions);

        radioGroupDoYouTrackWeight = findViewById(R.id.radioGroupDoYouTrackWeight);
        radioTrackYes = findViewById(R.id.radioTrackYes);
        radioTrackNo = findViewById(R.id.radioTrackNo);
        weightQuestionsLayout = findViewById(R.id.weightQuestionsLayout);
        radioGroupWeightType = findViewById(R.id.radioGroupWeightType);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        editAge = findViewById(R.id.editAge);
        editHeight = findViewById(R.id.editHeight);
        editWeight = findViewById(R.id.editWeight);
        editExerciseFrequency = findViewById(R.id.editExerciseFrequency);
        buttonFinish = findViewById(R.id.buttonFinish);

        db = Room.databaseBuilder(getApplicationContext(), database.class, "health-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        radioGroupDoYouTrackWeight.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioTrackYes) {
                weightQuestionsLayout.setVisibility(View.VISIBLE);
            } else {
                weightQuestionsLayout.setVisibility(View.GONE);
            }
        });

        buttonFinish.setOnClickListener(v -> {
            int selectedId = radioGroupDoYouTrackWeight.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Please select if you want to track your weight.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedId == R.id.radioTrackYes) {
                String ageStr = editAge.getText().toString().trim();
                String heightStr = editHeight.getText().toString().trim();
                String weightStr = editWeight.getText().toString().trim();
                String exerciseStr = editExerciseFrequency.getText().toString().trim();

                if (ageStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() || exerciseStr.isEmpty()
                        || radioGroupWeightType.getCheckedRadioButtonId() == -1
                        || radioGroupGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                currentUser.setTrackWeight(true);
                currentUser.setAge(Integer.parseInt(ageStr));
                currentUser.setHeight(Double.parseDouble(heightStr));
                currentUser.setWeight(Double.parseDouble(weightStr));
                currentUser.setWeeklyActivities(Integer.parseInt(exerciseStr));

                int selectedWeightTypeId = radioGroupWeightType.getCheckedRadioButtonId();
                currentUser.setStatus(selectedWeightTypeId == R.id.radioOverweight ? "Overweight" : "Underweight");

                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                currentUser.setGender(selectedGenderId == R.id.radioMale ? "Male" : "Female");

            } else {
                currentUser.setTrackWeight(false);
            }

            db.userDao().update(currentUser);

            Intent intent = new Intent(WeightQuestionsActivity.this, MainActivity.class); // or SummaryActivity etc.
            intent.putExtra("currentUser", currentUser);
            startActivity(intent);
        });
    }
}
