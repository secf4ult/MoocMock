package com.example.moocmock.ui.discovery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.moocmock.CollectionAdapter;
import com.example.moocmock.R;
import com.example.moocmock.ui.discovery.DiscoveryCollection.LiveFragment;

import java.util.ArrayList;

public class DiscoveryCollectionAdapter extends CollectionAdapter {

    public DiscoveryCollectionAdapter(@NonNull Fragment fragment, ArrayList<Fragment> fragmentList) {
        super(fragment, fragmentList);
    }
}
