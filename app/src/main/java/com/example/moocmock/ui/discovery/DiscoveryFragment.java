package com.example.moocmock.ui.discovery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.moocmock.R;
import com.example.moocmock.ui.discovery.DiscoveryCollection.LiveFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class DiscoveryFragment extends Fragment {

    ViewPager2 viewPager;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discovery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String[] tab_names = new String[] { "直播", "推荐", "关注"};

        initFragmentCollection();
        viewPager = view.findViewById(R.id.discovery_pager);
        viewPager.setAdapter(new DiscoveryCollectionAdapter(this, fragmentArrayList));

        TabLayout tabLayout = view.findViewById(R.id.discovery_tablayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tab_names[position])).attach();
    }

    private void initFragmentCollection() {
        fragmentArrayList.add(new LiveFragment());
        fragmentArrayList.add(new LiveFragment());
        fragmentArrayList.add(new LiveFragment());
    }
}

