package com.example.moocmock.ui.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moocmock.R;
import com.example.moocmock.ui.home.HomeCollection.BattleCourseFragment;
import com.example.moocmock.ui.home.HomeCollection.ColumnFragment;
import com.example.moocmock.ui.home.HomeCollection.FreeCourseFragment;
import com.example.moocmock.ui.home.HomeCollection.GoldPositionFragment;
import com.example.moocmock.ui.home.HomeCollection.RecommendationFragment;
import com.example.moocmock.ui.home.HomeCollection.TutorialFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ViewPager2 viewPager;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Resources res = getResources();
        String[] tab_names = res.getStringArray(R.array.tab_layout);

        SearchView searchView = view.findViewById(R.id.search_bar);
        searchView.setQueryHint(res.getString(R.string.search_placeholder));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getContext(), "搜索中", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        AppCompatImageButton imageButton = view.findViewById(R.id.imageButton_mail);
        imageButton.setOnClickListener(view1 -> Toast.makeText(getContext(), "邮件", Toast.LENGTH_SHORT).show());

        initFragmentCollection();
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(new HomeCollectionAdapter(this, fragmentArrayList));

        tabLayout = view.findViewById(R.id.pager_tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tab_names[position])).attach();
    }

    private void initFragmentCollection() {
        fragmentArrayList.add(new RecommendationFragment());
        fragmentArrayList.add(new FreeCourseFragment());
        fragmentArrayList.add(new BattleCourseFragment());
        fragmentArrayList.add(new GoldPositionFragment());
        fragmentArrayList.add(new ColumnFragment());
        fragmentArrayList.add(new TutorialFragment());
    }
}

