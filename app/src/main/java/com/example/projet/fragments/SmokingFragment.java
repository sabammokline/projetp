package com.example.projet.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.projet.R;
import com.example.projet.dao.CigaretteLogDao;
import com.example.projet.dao.UserDao;
import com.example.projet.database.*;
import com.example.projet.database.database;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SmokingFragment extends Fragment {
    private EditText cigaretteInput;
    private TextView logDateText;
    private Button saveButton;

    private database db;
    private CigaretteLogDao cigaretteLogDao;
    private UserDao userDao;

    private User currentUser;
    BarChart barChart;
    private CigaretteLog todayLog;
    private String todayDate;
    long userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragsmoke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cigaretteInput = view.findViewById(R.id.cigaretteInput);
        logDateText = view.findViewById(R.id.logDateText);
        saveButton = view.findViewById(R.id.saveButton);
        barChart = view.findViewById(R.id.smokeChart);

        db = Room.databaseBuilder(
                requireContext().getApplicationContext(),
                database.class,
                "user_database"
        ).allowMainThreadQueries().build();

        cigaretteLogDao = db.cigaretteLogDao();
        userDao = db.userDao();


        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", -1);
        //insertFakeData(userId);
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        logDateText.setText("Log for: " + todayDate);

        loadBarChart(userId);

        todayLog = cigaretteLogDao.getLogForDate(userId, todayDate);

        if (todayLog != null) {
            cigaretteInput.setText(String.valueOf(todayLog.getCigarettesSmoked()));
        }

        saveButton.setOnClickListener(v -> saveOrUpdateLog());
    }

    private void saveOrUpdateLog() {
        int cigarettes = Integer.parseInt(cigaretteInput.getText().toString().trim());

        if (todayLog != null) {
            todayLog.setCigarettesSmoked(cigarettes);
            todayLog.setTime(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
            cigaretteLogDao.update(todayLog);
            loadBarChart(userId);
            Toast.makeText(getContext(), "Log updated!", Toast.LENGTH_SHORT).show();
        } else {
            CigaretteLog newLog = new CigaretteLog();
            newLog.setUserId(currentUser.getId());
            newLog.setDate(todayDate);
            newLog.setCigarettesSmoked(cigarettes);
            newLog.setTime(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
            cigaretteLogDao.insert(newLog);
            loadBarChart(userId);
            Toast.makeText(getContext(), "Log saved!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadBarChart(long userId){

        // Fetch logs from the database
        List<CigaretteLog> logs = cigaretteLogDao.getLast7DaysLog(userId);

        for (CigaretteLog log : logs){
            Log.wtf("bb", "log=" + log);
        }


        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (int i = 0; i < logs.size(); i++) {
            CigaretteLog log = logs.get(i);
            entries.add(new BarEntry(i, log.getCigarettesSmoked()));  // Add data for the bar chart
            labels.add(log.getDate());  // Use the date as the label
        }

        BarDataSet dataSet = new BarDataSet(entries, "Cigarettes Smoked");
        dataSet.setColor(ContextCompat.getColor(getContext(), R.color.a7mer));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        // Set the data into the chart
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Customizing chart
        barChart.invalidate();  // Refresh the chart
    }


    public void insertFakeData(long userId) {
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Insert fake data for the last 7 days
        for (int i = 0; i < 7; i++) {
            // Generate a fake date (decreasing by one day each time)
            Date date = new Date(System.currentTimeMillis() - (i * 24L * 60 * 60 * 1000)); // subtract i days
            String dateStr = sdf.format(date);

            // Generate a random number of cigarettes smoked (between 5 and 20)
            int cigarettesSmoked = random.nextInt(16) + 5;

            // Create the CigaretteLog entry
            CigaretteLog log = new CigaretteLog();
            log.setUserId(userId);
            log.setDate(dateStr);
            log.setCigarettesSmoked(cigarettesSmoked);

            cigaretteLogDao.insert(log);
        }
    }
}

