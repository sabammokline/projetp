package com.example.projet.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.projet.R;
import com.example.projet.database.WeightLog;
import com.example.projet.database.database;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class WeightFragment extends Fragment {

    private LineChart lineChart;
    private Button button;
    private TextView weightInput;
    private long userId;
    private database db;


    public WeightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragweight, container, false);

        lineChart = view.findViewById(R.id.weightChar);
        button = view.findViewById(R.id.saveButton);
        weightInput = view.findViewById(R.id.WeightInput);
        db = Room.databaseBuilder(
                requireContext().getApplicationContext(),
                database.class,
                "user_database"
        ).allowMainThreadQueries().build();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", -1);

        // Insert fake data for testing
        //insertFakeData(userId);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeightLog log = db.weightLogDao().getTodaysLog(userId, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
                String inputText = weightInput.getText().toString();

                if (isNumeric(inputText)) {
                    float weight = Float.parseFloat(inputText);

                    if (log == null) {
                        log = new WeightLog(0, userId, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()), weight);
                        db.weightLogDao().insert(log);
                    } else {
                        log.setWeight(weight);
                        db.weightLogDao().update(log);
                    }
                } else {
                    Toast.makeText(getContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
                }
            }
        });



        // Load weight data from the database
        loadWeightData();

        return view;
    }

    public static boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


    private void loadWeightData() {
        // Get the user's weight log data from the database


        // Get all weight logs for the user
        List<WeightLog> weightLogs = db.weightLogDao().getAllWeightLogsForUser(userId);

        // Convert the weight log data to chart entries
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < weightLogs.size(); i++) {
            WeightLog weightLog = weightLogs.get(i);
            // Add weight data to the chart, using index for x-value (time)
            entries.add(new Entry(i, weightLog.getWeight()));
        }

        // Create a LineDataSet from the entries
        LineDataSet dataSet = new LineDataSet(entries, "Weight Over Time");
        dataSet.setColor(getResources().getColor(R.color.purple_200));  // Set line color
        dataSet.setValueTextColor(getResources().getColor(R.color.purple_700));  // Set text color
        dataSet.setLineWidth(2f);  // Set line width
        dataSet.setDrawCircles(true);  // Show data points

        // Create LineData from the dataset and set it to the chart
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        // Refresh the chart
        lineChart.invalidate();
    }

    public void insertFakeData(long userId) {
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Initialize the database instance
        database db = Room.databaseBuilder(
                requireContext().getApplicationContext(),
                database.class,
                "user_database"
        ).allowMainThreadQueries().build();

        // Insert fake data for the last 7 days
        for (int i = 0; i < 7; i++) {
            // Generate a fake date (decreasing by one day each time)
            Date date = new Date(System.currentTimeMillis() - (i * 24L * 60 * 60 * 1000)); // subtract i days
            String dateStr = sdf.format(date);

            // Generate a random weight between 50 and 60
            float randomWeight = 50 + (random.nextFloat() * 10); // Random weight between 50 and 60

            // Create the WeightLog entry
            WeightLog weightLog = new WeightLog();
            weightLog.setUserId(userId);
            weightLog.setDate(dateStr);
            weightLog.setWeight(randomWeight);

            // Insert the fake weight log into the database
            db.weightLogDao().insert(weightLog);
        }
    }
}
