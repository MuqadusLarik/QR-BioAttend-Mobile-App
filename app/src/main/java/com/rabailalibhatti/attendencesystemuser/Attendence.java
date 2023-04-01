package com.rabailalibhatti.attendencesystemuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class Attendence extends AppCompatActivity {
    TabLayout tabLayout;
    public static String subjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
//        tabLayout = findViewById(R.id.tablayout);
        subjectName = getIntent().getStringExtra("Subject");

        changeFragment(new AttendenceFragment());

//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()){
//                    case 0:
//                        changeFragment(new AttendenceFragment());
//                        break;
//                    case 1:
//                        changeFragment(new Reports());
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

    }

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layoutframgnet,fragment)
                .commit();
    }

}