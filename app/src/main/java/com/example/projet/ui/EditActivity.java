package com.example.projet.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projet.R;

public class EditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // If you want to remove the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Your setup logic here
    }
}
