package com.example.moocmock;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends Fragment {

    Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        Button skipButton = view.findViewById(R.id.button_skip);
        skipButton.setOnClickListener(view1 -> navigateToHome(view1));
//
//        final int[] count = {5};
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                skipButton.setText(R.string.skip + --count[0]);
//            }
//        }, 0, 1000);
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                timer.cancel();
//            }
//        }, 5000);

        new Handler().postDelayed(() -> navigateToHome(view), 2000);

        return view;
    }

    private void navigateToHome(View view) {
        Navigation.findNavController(view).navigate(R.id.action_splash_to_navigation_home);
    }
}