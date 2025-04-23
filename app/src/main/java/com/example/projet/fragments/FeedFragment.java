package com.example.projet.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import com.example.projet.R;
import com.example.projet.adapters.PostAdapter;
import com.example.projet.database.database;
import com.example.projet.database.Post;
import com.example.projet.utils.Dateutil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {
    private ExtendedFloatingActionButton addPostButton;
    private ConstraintLayout bottomSheet;
    private EditText postContent;
    private Button cancelButton;
    private Button postButton;
    private BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior;

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList = new ArrayList<>();

    private database db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        db = Room.databaseBuilder(
                requireContext().getApplicationContext(),
                database.class,
                "health-app-db"
        ).allowMainThreadQueries().build();

        initViews(view);
        setupRecyclerView(view);
        setupBottomSheet();
        setupClickListeners();
        loadPosts();
    }

    private void initViews(View view) {
        addPostButton = view.findViewById(R.id.addPostButton);
        bottomSheet = view.findViewById(R.id.bottomSheet);
        postContent = view.findViewById(R.id.postContent);
        cancelButton = view.findViewById(R.id.cancelButton);
        postButton = view.findViewById(R.id.postButton);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void setupRecyclerView(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postAdapter = new PostAdapter(postList);
        recyclerView.setAdapter(postAdapter);
    }

    private void setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setHideable(true);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    showAddPostButton();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
        });
    }

    private void setupClickListeners() {
        addPostButton.setOnClickListener(v -> {
            showBottomSheet();
            hideAddPostButton();
        });

        cancelButton.setOnClickListener(v -> {
            hideBottomSheet();
            showAddPostButton();
        });

        postButton.setOnClickListener(v -> {
            createPost();
            hideBottomSheet();
            showAddPostButton();
        });
    }

    private void showBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void hideBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void hideAddPostButton() {
        addPostButton.hide();
    }

    private void showAddPostButton() {
        if (!addPostButton.isShown()) {
            addPostButton.show();
        }
    }

    private void createPost() {
        String content = postContent.getText().toString().trim();
        if (content.isEmpty()) return;

        Post post = new Post();
        post.setUsername("Guest"); // Replace with actual username if available
        post.setContent(content);
        post.setDate(Dateutil.getCurrentDate());

        db.postDao().insert(post);
        postContent.setText("");

        loadPosts(); // Refresh feed
    }

    private void loadPosts() {
        List<Post> posts = db.postDao().getAllPosts();
        postAdapter.setPostList(posts);
    }
}
