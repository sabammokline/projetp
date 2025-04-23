package com.example.projet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projet.fragments.FeedFragment;
import com.example.projet.fragments.HomeFragment;
import com.example.projet.fragments.MessageFragment;
import com.example.projet.fragments.ProfileFragment;
import com.example.projet.entities.UserInfoActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);

        if (userId == -1) {
            // If the profile is not created, go to UserInfoActivity
            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
            startActivity(intent);
            finish();

        }

        setContentView(R.layout.activity_main);

        // Initialize the ViewPager and BottomNavigationView
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set up the ViewPager adapter with the fragments
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Set up the BottomNavigationView listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    viewPager.setCurrentItem(0, false); // Set the current page to Home
                    return true;
                } else if (item.getItemId() == R.id.nav_feed) {
                    viewPager.setCurrentItem(1, false); // Set the current page to Feed
                    return true;
                } else if (item.getItemId() == R.id.nav_message) {
                    viewPager.setCurrentItem(2, false); // Set the current page to Message
                    return true;
                } else if (item.getItemId() == R.id.nav_profile) {
                    viewPager.setCurrentItem(3, false); // Set the current page to Profile
                    return true;
                }
                return false;
            }
        });

        // Synchronize ViewPager with BottomNavigationView
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.nav_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.nav_feed);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.nav_message);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
                        break;
                }
            }
        });

    }

    // ViewPager Adapter class to manage fragments
    private static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new FeedFragment();
                case 2:
                    return new MessageFragment();
                case 3:
                    return new ProfileFragment();
                default:
                    return new HomeFragment(); // Default case
            }
        }

        @Override
        public int getItemCount() {
            return 4; // Total number of sections (4)
        }
    }
}
