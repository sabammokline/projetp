package com.example.projet.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projet.R;
import com.example.projet.database.User;
import com.example.projet.database.database;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private User user;
    private boolean smokingSectionExpanded = true;
    private boolean drinkingSectionExpanded = false;
    private boolean weightSectionExpanded = false;
    private View view;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);

        database db = database.getInstance(getContext());

        executor.execute(() -> {
            // Background thread work
            Log.wtf("ee", "id = " + userId);
            user = db.userDao().getUserById(userId);


            requireActivity().runOnUiThread(() -> {
                Log.wtf("ee", "user info: " + user.toString());
                loadUi();
            });

        });

        // Initialize section toggles



        return view;
    }

    private void initSectionToggles(View view) {
        // Smoking section toggle
        ImageButton btnToggleSmoking = view.findViewById(R.id.btn_toggle_smoking);
        LinearLayout smokingContent = view.findViewById(R.id.smoking_section_content);

        btnToggleSmoking.setOnClickListener(v -> {
            smokingSectionExpanded = !smokingSectionExpanded;
            smokingContent.setVisibility(smokingSectionExpanded ? View.VISIBLE : View.GONE);
            btnToggleSmoking.setImageResource(smokingSectionExpanded ?
                    android.R.drawable.arrow_down_float : android.R.drawable.arrow_up_float);
        });

        // Drinking section toggle
        ImageButton btnToggleDrinking = view.findViewById(R.id.btn_toggle_drinking);
        LinearLayout drinkingContent = view.findViewById(R.id.drinking_section_content);

        btnToggleDrinking.setOnClickListener(v -> {
            drinkingSectionExpanded = !drinkingSectionExpanded;
            drinkingContent.setVisibility(drinkingSectionExpanded ? View.VISIBLE : View.GONE);
            btnToggleDrinking.setImageResource(drinkingSectionExpanded ?
                    android.R.drawable.arrow_down_float : android.R.drawable.arrow_up_float);
        });

        // Weight section toggle
        ImageButton btnToggleWeight = view.findViewById(R.id.btn_toggle_weight);
        LinearLayout weightContent = view.findViewById(R.id.weight_section_content);

        btnToggleWeight.setOnClickListener(v -> {
            weightSectionExpanded = !weightSectionExpanded;
            weightContent.setVisibility(weightSectionExpanded ? View.VISIBLE : View.GONE);
            btnToggleWeight.setImageResource(weightSectionExpanded ?
                    android.R.drawable.arrow_down_float : android.R.drawable.arrow_up_float);
        });
    }

    private void setupSmokingSection(View view) {
        ProgressBar lungProgressBar = view.findViewById(R.id.lung_damage_bar);
        TextView daysLeft = view.findViewById(R.id.days_left);
        TextView hoursLeft = view.findViewById(R.id.hours_left);
        TextView minutesLeft = view.findViewById(R.id.minutes_left);
        TextView statDaysTracked = view.findViewById(R.id.stat_days_tracked);
        TextView statCigsAvoided = view.findViewById(R.id.stat_cigs_avoided);
        TextView statMoneySaved = view.findViewById(R.id.stat_money_saved);
        TextView statTimeWon = view.findViewById(R.id.stat_time_won);
        Button btnDailyCheck = view.findViewById(R.id.btn_smoking_daily_check);

        // Set lung damage progress
        lungProgressBar.setProgress(user.getLungDamagePercent());

        // Set countdown timer
        long remainingMillis = user.getQuitTime() - System.currentTimeMillis();
        if (remainingMillis > 0) {
            new CountDownTimer(remainingMillis, 60000) {
                public void onTick(long millisUntilFinished) {
                    daysLeft.setText(String.valueOf(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)));
                    hoursLeft.setText(String.valueOf(TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24));
                    minutesLeft.setText(String.valueOf(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60));
                }
                public void onFinish() {
                    daysLeft.setText("0");
                    hoursLeft.setText("0");
                    minutesLeft.setText("0");
                }
            }.start();
        }

        // Set stats
        statDaysTracked.setText(String.valueOf(user.getDebutDate()));
        statCigsAvoided.setText(String.valueOf(user.getCigarettesAvoided()));
        statMoneySaved.setText("$" + user.getMoneySaved());
        statTimeWon.setText(user.getTimeWon() + "h");

        // Set daily check button click listener
        btnDailyCheck.setOnClickListener(v -> {
            // Handle daily check-in
        });
    }

    private void setupDrinkingSection(View view) {
        ProgressBar kidneyHealthBar = view.findViewById(R.id.kidney_health_bar);
        TextView drinkDaysTracked = view.findViewById(R.id.drink_days_tracked);
        TextView drinksAvoided = view.findViewById(R.id.drinks_avoided);
        TextView drinkMoneySaved = view.findViewById(R.id.drink_money_saved);
        Button btnDailyCheck = view.findViewById(R.id.btn_drinking_daily_check);

        // Set kidney health progress (example - adjust according to your User class)
        kidneyHealthBar.setProgress(user.getKidneyHealthPercent());

        // Set stats (example - adjust according to your User class)
        drinkDaysTracked.setText(String.valueOf(user.getDrinkingDaysTracked()));
        drinksAvoided.setText(String.valueOf(user.getDrinksAvoided()));
        drinkMoneySaved.setText("$" + user.getDrinkingMoneySaved());

        // Set daily check button click listener
        btnDailyCheck.setOnClickListener(v -> {
            // Handle daily check-in
        });
    }

    private void setupWeightSection(View view) {
        TextView caloriesNeeded = view.findViewById(R.id.calories_needed);
        TextView startingWeight = view.findViewById(R.id.starting_weight);
        TextView currentWeight = view.findViewById(R.id.current_weight);
        TextView goalWeight = view.findViewById(R.id.goal_weight);
        Button btnDailyCheck = view.findViewById(R.id.btn_weight_daily_check);

        // Set weight stats
        caloriesNeeded.setText(user.getDailyCalories() + " Calories/Day");
        startingWeight.setText(String.valueOf(user.getWeight()));
        currentWeight.setText(String.valueOf(user.getWeight()));
        goalWeight.setText(String.valueOf(user.getGoalweight()));

        // Set daily check button click listener
        btnDailyCheck.setOnClickListener(v -> {
            // Handle daily check-in
        });
    }


    private void loadUi(){
        try {
            initSectionToggles(view);

            // Setup smoking section if user tracks smoking
            if (user != null && user.getSmoking()) {
                setupSmokingSection(view);
            } else {
                view.findViewById(R.id.smoking_section_header).setVisibility(View.GONE);
                view.findViewById(R.id.smoking_section_content).setVisibility(View.GONE);
            }

            // Setup drinking section if user tracks drinking
            if (user != null && user.getDrinking()) {
                setupDrinkingSection(view);
            } else {
                view.findViewById(R.id.drinking_section_header).setVisibility(View.GONE);
                view.findViewById(R.id.drinking_section_content).setVisibility(View.GONE);
            }

            // Setup weight section if user tracks weight
            if (user != null && user.getTrackWeight()) {
                setupWeightSection(view);
            } else {
                view.findViewById(R.id.weight_section_header).setVisibility(View.GONE);
                view.findViewById(R.id.weight_section_content).setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.wtf("ee", "error" + e.getMessage());
        }


    }
}