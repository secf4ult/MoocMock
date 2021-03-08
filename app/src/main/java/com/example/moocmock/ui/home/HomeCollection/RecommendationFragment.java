package com.example.moocmock.ui.home.HomeCollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moocmock.R;
import com.synnapps.carouselview.CarouselView;

public class RecommendationFragment extends Fragment {

    int[] carouselImages = new int[]{R.drawable.carousel_1, R.drawable.carousel_2, R.drawable.carousel_3};
    CarouselView carouselView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_recommendation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        carouselView = view.findViewById(R.id.home_carouselView);
        carouselView.setImageListener((position, imageView) -> imageView.setImageResource(carouselImages[position]));
        carouselView.setPageCount(carouselImages.length);
        carouselView.setImageClickListener(position -> Toast.makeText(getContext(), "Image" + position, Toast.LENGTH_SHORT).show());
    }
}
