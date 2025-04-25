package com.example.projet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projet.fragments.DrinkingFragment;
import com.example.projet.fragments.FeedFragment;
import com.example.projet.fragments.HomeFragment;
import com.example.projet.fragments.MessageFragment;
import com.example.projet.fragments.ProfileFragment;
import com.example.projet.entities.UserInfoActivity;
import com.example.projet.fragments.SmokingFragment;
import com.example.projet.fragments.WeightFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener {

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

        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Set up the BottomNavigationView listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                viewPager.setCurrentItem(0, false);
                return true;
            } else if (item.getItemId() == R.id.nav_feed) {
                viewPager.setCurrentItem(1, false);
                return true;
            } else if (item.getItemId() == R.id.nav_message) {
                viewPager.setCurrentItem(2, false);
                return true;
            } else if (item.getItemId() == R.id.nav_profile) {
                viewPager.setCurrentItem(3, false);
                return true;
            }
            return false;
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

    // Implement the interface method to handle fragment switch
    @Override
    public void onSmokingFragmentSelected() {
        // Switch to the SmokingFragment (assuming it's at position 1)
        Log.wtf("e", "heree");
        viewPager.setCurrentItem(4, false);  // Adjust this according to the actual position of the SmokingFragment
    }


    public void onDrinkingFragmentSelected() {
        Log.wtf("e", "heree");
        viewPager.setCurrentItem(5, false);  // Adjust this according to the actual position of the SmokingFragment
    }

    @Override
    public void onWeightFragmentSelected() {
        viewPager.setCurrentItem(6, false);
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
                case 4:
                    return new SmokingFragment();
                case 5:
                    return new DrinkingFragment();
                case 6:
                    return new WeightFragment();
                default:
                    return new HomeFragment(); // Default case
            }
        }

        @Override
        public int getItemCount() {
            return 7; // Total number of sections (4)
        }
    }
}

