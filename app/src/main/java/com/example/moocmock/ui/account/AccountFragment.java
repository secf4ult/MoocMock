package com.example.moocmock.ui.account;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.moocmock.R;

public class AccountFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView profileImageView = view.findViewById(R.id.imageView_profile);
        Drawable profileImage = getResources().getDrawable(R.drawable.profile_image_placeholder);
        profileImageView.setImageDrawable(profileImage);

        ConstraintLayout buttonContainer = view.findViewById(R.id.container_buttons);
        for (int i = 0, l = buttonContainer.getChildCount(); i < l; ++i) {
            Button button = (Button) buttonContainer.getChildAt(i);
            if (button.getText().equals("我的订单")) {
                button.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_navigation_account_to_webview2));
            } else {
                button.setOnClickListener(view1 -> Toast.makeText(getContext(), button.getText(), Toast.LENGTH_SHORT).show());
            }
        }
    }
}
