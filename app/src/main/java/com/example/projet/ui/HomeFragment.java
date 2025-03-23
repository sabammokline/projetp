package com.example.projet.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.projet.R;
import com.example.projet.database.User;

import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Assuming the user object is passed via arguments, not via getIntent()
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("currentUser");
        }

        // Smoking Section
        View smokingSection = view.findViewById(R.id.sm);
        if (user != null && user.getSmoking()) {
            smokingSection.setVisibility(View.VISIBLE);

            TextView timeRemaining = view.findViewById(R.id.timeRemaining);
            ProgressBar lungProgressBar = view.findViewById(R.id.lungProgressBar);
            TextView cigarettesAvoided = view.findViewById(R.id.cigarettesAvoided);
            TextView moneySaved = view.findViewById(R.id.moneySaved);
            TextView timeWon = view.findViewById(R.id.timeWon);
            TextView daysTracked = view.findViewById(R.id.daysTracked);

            // Lung Damage (simple estimate example)
            int damagePercent = user.getLungDamagePercent(); // e.g. a method that returns int 0–100
            lungProgressBar.setProgress(damagePercent);

            // Remaining time to quit
            long remainingMillis = user.getQuitTime() - System.currentTimeMillis();
            if (remainingMillis > 0) {
                new CountDownTimer(remainingMillis, 60000) {
                    public void onTick(long millisUntilFinished) {
                        long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                        long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24;
                        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60;
                        timeRemaining.setText(days + "d " + hours + "h " + minutes + "m");
                    }
                    public void onFinish() {
                        timeRemaining.setText("You made it!");
                    }
                }.start();
            } else {
                timeRemaining.setText("You made it!");
            }

            cigarettesAvoided.setText(String.valueOf(user.getCigarettesAvoided()));
            moneySaved.setText("$" + user.getMoneySaved());
            timeWon.setText(user.getTimeWon() + "h");
            daysTracked.setText(String.valueOf(user.getDaysSinceStart()));
        } else {
            smokingSection.setVisibility(View.GONE);
        }

        // Drinking Section
        View drinkingSection = view.findViewById(R.id.drinkingSection);
        if (user != null && user.getDrinking()) {
            drinkingSection.setVisibility(View.VISIBLE);
            // Add logic to populate this section
        } else {
            drinkingSection.setVisibility(View.GONE);
        }

        // Weight Section
        View weightSection = view.findViewById(R.id.weightSection);
        if (user != null && user.getTrackWeight()) {
            weightSection.setVisibility(View.VISIBLE);
            // Add logic to populate this section
        } else {
            weightSection.setVisibility(View.GONE);
        }

        return view;
    }
}
