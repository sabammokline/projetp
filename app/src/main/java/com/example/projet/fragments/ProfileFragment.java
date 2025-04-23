package com.example.projet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.projet.R;
import com.example.projet.database.User;
import com.example.projet.database.database;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileFragment extends Fragment {

    private TextView tvNickname, tvFullName, tvSmoking, tvDrinking, tvWeight;
    private Button btnEditTracking, btnManageProfile;

    private database db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvNickname = view.findViewById(R.id.tvNickname);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvSmoking = view.findViewById(R.id.tvSmoking);
        tvDrinking = view.findViewById(R.id.tvDrinking);
        tvWeight = view.findViewById(R.id.tvWeight);
        btnEditTracking = view.findViewById(R.id.btnEditTracking);
        btnManageProfile = view.findViewById(R.id.btnManageProfile);

        db = database.getInstance(requireContext());

        loadUserData();

        btnEditTracking.setOnClickListener(v -> {

        });

        btnManageProfile.setOnClickListener(v -> {
        });

        return view;
    }

    private void loadUserData() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            User user = db.userDao().getUserById(1);

            if (user != null) {
                requireActivity().runOnUiThread(() -> {
                    tvNickname.setText(user.getUserName());
                    tvFullName.setText(user.getPrenom() + " " + user.getNom());

                    // Smoking
                    if (Boolean.TRUE.equals(user.getSmoking())) {
                        tvSmoking.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                    } else {
                        tvSmoking.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray));
                    }

                    // Drinking
                    if (Boolean.TRUE.equals(user.getDrinking())) {
                        tvDrinking.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                    } else {
                        tvDrinking.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray));
                    }

                    // Weight
                    if (Boolean.TRUE.equals(user.getTrackWeight())) {
                        tvWeight.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                    } else {
                        tvWeight.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray));
                    }
                });
            }
        });
    }
}
