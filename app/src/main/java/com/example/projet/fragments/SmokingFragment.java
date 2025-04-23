package com.example.projet.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.projet.R;
import com.example.projet.dao.CigaretteLogDao;
import com.example.projet.dao.UserDao;
import com.example.projet.database.*;
import com.example.projet.database.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SmokingFragment extends Fragment {
    private EditText cigaretteInput;
    private TextView logDateText;
    private Button saveButton;

    private database db;
    private CigaretteLogDao cigaretteLogDao;
    private UserDao userDao;

    private User currentUser;
    private CigaretteLog todayLog;
    private String todayDate;

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

        db = Room.databaseBuilder(
                requireContext().getApplicationContext(),
                database.class,
                "health-app-db"
        ).allowMainThreadQueries().build();

        cigaretteLogDao = db.cigaretteLogDao();
        userDao = db.userDao();

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);

        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        logDateText.setText("Log for: " + todayDate);

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
            cigaretteLogDao.update(todayLog);
            Toast.makeText(getContext(), "Log updated!", Toast.LENGTH_SHORT).show();
        } else {
            CigaretteLog newLog = new CigaretteLog();
            newLog.setUserId(currentUser.getId());
            newLog.setDate(todayDate);
            newLog.setCigarettesSmoked(cigarettes);
            cigaretteLogDao.insert(newLog);
            Toast.makeText(getContext(), "Log saved!", Toast.LENGTH_SHORT).show();
        }
    }
}
