package com.example.projet.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
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
import com.example.projet.database.CigaretteLog;
import com.example.projet.database.DrinkingLog;
import com.example.projet.database.User;
import com.example.projet.database.WeightLog;
import com.example.projet.database.database;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private final String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


    // Define an interface for communication with the MainActivity
    public interface OnFragmentInteractionListener {
        void onSmokingFragmentSelected();
        void onDrinkingFragmentSelected(); // New method to switch to DrinkingFragment
        void onWeightFragmentSelected(); // New method to switch to DrinkingFragment
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private User user;
    private boolean smokingSectionExpanded = true;
    private boolean drinkingSectionExpanded = false;
    private boolean weightSectionExpanded = false;
    private View view;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private database db;
    long userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", -1);

        db = database.getInstance(getContext());

        executor.execute(() -> {
            // Background thread work
            Log.wtf("ee", "id = " + userId);
            user = db.userDao().getUserById(userId);

            if (user.getSmoking() != null && user.getSmoking() ){
                view.findViewById(R.id.smokeSection).setVisibility(View.VISIBLE);
            }
            if (user.getDrinking() != null && user.getDrinking()){
                view.findViewById(R.id.drinkingSection).setVisibility(View.VISIBLE);
            }
            if (user.getTrackWeight() != null && user.getTrackWeight()){
                view.findViewById(R.id.weightSection).setVisibility(View.VISIBLE);
            }


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

        loadSmokingInfo(new SmokingInfoCallback() {
            @Override
            public void onLoadSmoke(CigaretteLog log) {
                // UI updates must run on UI thread, but we're already on it here from loadSmokingInfo()
                Log.wtf("smoke", "lung damdage= " +  (100- ((user.getPacksPerDay() * user.getCigarettesPerPack() * user.getYearsSmoked() * 0.05))));
                lungProgressBar.setProgress((int)(60 - (user.getPacksPerDay() * user.getCigarettesPerPack() * user.getYearsSmoked() * 0.05)) );
                //load time in ui
                executor.execute(() -> {
                    CigaretteLog log1 = db.cigaretteLogDao().getFirstDay(userId);
                    CigaretteLog log2 = db.cigaretteLogDao().getLastSmokedDate(userId);
                    Log.wtf("ee", "last smoked = " + log2);

                    requireActivity().runOnUiThread(() -> {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            SimpleDateFormat sdfWithTime = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

                            // Parse the first log date
                            Date firstDate = sdf.parse(log1.getDate());

                            // Calculate days tracked
                            String todayStr = sdf.format(new Date());
                            Date todayDate = sdf.parse(todayStr);
                            long diffMillis = todayDate.getTime() - firstDate.getTime();
                            long diffDays = TimeUnit.MILLISECONDS.toDays(diffMillis);
                            statDaysTracked.setText(String.valueOf(diffDays));

                            // Only proceed if log2 exists and has valid date/time
                            if (log2 != null && log2.getDate() != null && log2.getTime() != null) {
                                Date lastTimeSmokedDate = sdfWithTime.parse(log2.getDate() + " " + log2.getTime());

                                long diffMillisTime = new Date().getTime() - lastTimeSmokedDate.getTime();
                                long diffDaysTime = TimeUnit.MILLISECONDS.toDays(diffMillisTime);
                                long diffHours = TimeUnit.MILLISECONDS.toHours(diffMillisTime) % 24;
                                long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diffMillisTime) % 60;

                                // Set time since last smoked (as numbers)
                                daysLeft.setText(String.valueOf(diffDaysTime));
                                hoursLeft.setText(String.valueOf(diffHours));
                                minutesLeft.setText(String.valueOf(diffMinutes));
                            } else {
                                // Default values if user hasn't smoked yet
                                daysLeft.setText("0");
                                hoursLeft.setText("0");
                                minutesLeft.setText("0");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    });
                });


                int timeWonMinutes = user.getTimeWonBack(getContext());

                int days = timeWonMinutes / (60 * 24);
                int hours = (timeWonMinutes % (60 * 24)) / 60;

                if (days != 0){
                    statTimeWon.setText(days + "d");
                } else if (hours != 0) {
                    statTimeWon.setText(hours + "h");
                }else {
                    statTimeWon.setText(timeWonMinutes + "min");
                }
                statMoneySaved.setText(String.valueOf(user.getMoneySaved(getContext())));
                statCigsAvoided.setText(String.valueOf(user.getCigarettesAvoidedCount(getContext())));




                btnDailyCheck.setOnClickListener(v -> {
                    switchToSmokingFragment();
                });
            }
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


        loadDrinkingInfo(new DrinkingInfoCallback() {

            @Override
            public void onLoadDrink(DrinkingLog log) {
                executor.execute(() -> {
                    DrinkingLog log1 = db.drinkingLogDao().getFirstDayTracked(userId);
                    List<DrinkingLog> logs = db.drinkingLogDao().getDrunkDays(userId);

                    requireActivity().runOnUiThread(() -> {
                        try {

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            // Parse the first log date
                            Date firstDate = sdf.parse(log1.getDate());

                            // Calculate days tracked
                            String todayStr = sdf.format(new Date());
                            Date todayDate = sdf.parse(todayStr);
                            long diffMillis = todayDate.getTime() - firstDate.getTime();
                            long diffDays = TimeUnit.MILLISECONDS.toDays(diffMillis);
                            drinkDaysTracked.setText(String.valueOf(diffDays));

                            int avoidedDrinks = 0;
                            int weeklyConsumed = 0;


                            int count = Math.min(7, logs.size());
                            for (int i = 0; i < count; i++) {
                                weeklyConsumed += logs.get(i).getBottlesConsumed();
                            }

                            if (user.getBottlesPerWeek() > 0 && weeklyConsumed < user.getBottlesPerWeek()) {
                                avoidedDrinks = user.getBottlesPerWeek() - weeklyConsumed;
                            }

                            drinksAvoided.setText(String.valueOf(avoidedDrinks));
                            drinkMoneySaved.setText((int) (avoidedDrinks * user.getBottleCost()) + " dt");





                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    });
                });


            }
        });

        // Set daily check button click listener
        btnDailyCheck.setOnClickListener(v -> {

            switchToDrinkingFragment();
        });
    }

    private void setupWeightSection(View view) {
        TextView caloriesNeeded = view.findViewById(R.id.calories_needed);
        TextView startingWeight = view.findViewById(R.id.starting_weight);
        TextView currentWeight = view.findViewById(R.id.current_weight);
        TextView goalWeight = view.findViewById(R.id.goal_weight);
        Button btnDailyCheck = view.findViewById(R.id.btn_weight_daily_check);

        executor.execute(() -> {
            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            WeightLog log = db.weightLogDao().getTodaysLog(userId, today);

            if (log == null) {
                WeightLog tempLog = db.weightLogDao().getLastLog(userId);
                if (tempLog != null) {
                    db.weightLogDao().insert(new WeightLog(0, userId, today, tempLog.getWeight()));
                }
                requireActivity().runOnUiThread(() -> setupWeightSection(view)); // recall with new data
                return;
            }

            double BMR;
            if ("Female".equals(user.getGender())) {
                BMR = 10 * log.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161;
            } else {
                BMR = 10 * log.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
            }

            if ("Underweight".equals(user.getStatus())) {
                BMR += 300;
            } else {
                BMR -= 400;
            }

            double finalBMR = BMR;
            requireActivity().runOnUiThread(() -> {
                caloriesNeeded.setText(String.format(Locale.getDefault(), "%.0f Calories/Day", finalBMR));
                startingWeight.setText(String.format(Locale.getDefault(), "%.1f", user.getWeight()));
                goalWeight.setText(String.format(Locale.getDefault(), "%.1f", user.getGoalweight())); // change if goal is different
                currentWeight.setText(String.format(Locale.getDefault(), "%.1f", log.getWeight()));
            });
        });

        btnDailyCheck.setOnClickListener(v -> switchToWeightFragment());
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

    public interface SmokingInfoCallback {
        void onLoadSmoke(CigaretteLog log);
    }

    public interface DrinkingInfoCallback {
        void onLoadDrink(DrinkingLog log);
    }

    private void loadDrinkingInfo(DrinkingInfoCallback callback) {
        executor.execute(() -> {
            DrinkingLog log = db.drinkingLogDao().getLogForDate(userId, todayDate);

            if (log == null) {
                log = new DrinkingLog(0, userId, todayDate, 0);
                db.drinkingLogDao().insert(log);
                DrinkingLog finalLog = log;
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "Drinking log created", Toast.LENGTH_SHORT).show();
                    callback.onLoadDrink(finalLog);
                });
            } else {
                DrinkingLog finalLog = log;
                requireActivity().runOnUiThread(() -> {
                    callback.onLoadDrink(finalLog);
                });
            }
        });
    }



    private void loadSmokingInfo(SmokingInfoCallback callback) {
        executor.execute(() -> {
            CigaretteLog log = db.cigaretteLogDao().getLogForDate(userId, todayDate);

            if (log == null) {
                log = new CigaretteLog(0, userId, 0, todayDate, null);
                db.cigaretteLogDao().insert(log);
                CigaretteLog finalLog = log;
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(), "Log created", Toast.LENGTH_SHORT).show();
                    callback.onLoadSmoke(finalLog);
                });
            } else {
                CigaretteLog finalLog = log;
                requireActivity().runOnUiThread(() -> {
                    callback.onLoadSmoke(finalLog);
                });
            }
        });
    }


    private void switchToSmokingFragment() {
        if (mListener != null) {
            mListener.onSmokingFragmentSelected();  // Notify MainActivity to switch to SmokingFragment
        }
    }

    private void switchToDrinkingFragment() {
        if (mListener != null) {
            mListener.onDrinkingFragmentSelected();  // Notify MainActivity to switch to DrinkingFragment
        }
    }

    private void switchToWeightFragment() {
        if (mListener != null) {
            mListener.onWeightFragmentSelected();  // Notify MainActivity to switch to DrinkingFragment
        }
    }

}