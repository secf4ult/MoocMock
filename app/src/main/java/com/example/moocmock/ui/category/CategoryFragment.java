package com.example.moocmock.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moocmock.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CategoryFragment extends Fragment {

    String[][] categoryDetails = new String[][]{
            {"JavaScript", "TypeScript", "React.js", "Vue.js"},
            {"Java", "C/C++"},
            {"Android", "iOS"}};
    private final String[] categoryNames = new String[]{"前端开发", "后端开发", "移动开发", "计算机基础", "前沿技术", "云计算&大数据", "运维&测试", "数据库", "UI设计&多媒体"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager2 viewPager = view.findViewById(R.id.category_viewPager);
        viewPager.setAdapter(new CategoryFragmentCollection(this, categoryDetails));

        TabLayout tabLayout = view.findViewById(R.id.category_tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(categoryNames[position])).attach();
    }
}


