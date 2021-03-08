package com.example.moocmock.ui.category;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CategoryFragmentCollection extends FragmentStateAdapter {

    private final String[][] details;
    public CategoryFragmentCollection(@NonNull Fragment fragment, String[][] details) {
        super(fragment);
        this.details = details;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new CategoryDetailFragment(details[position]);
    }

    @Override
    public int getItemCount() {
        return details.length;
    }
}
