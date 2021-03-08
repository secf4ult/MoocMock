package com.example.moocmock.ui.category;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CategoryFragmentCollection extends FragmentStateAdapter {

    private final String[][] details;
    private final String[] categoryNames = new String[]{"前端开发", "后端开发", "移动开发", "计算机基础", "前沿技术", "云计算&大数据", "运维&测试", "数据库", "UI设计&多媒体"};

    public CategoryFragmentCollection(Fragment fragment, String[][] details) {
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
