package com.example.moocmock.ui.home;

import androidx.fragment.app.Fragment;
import com.example.moocmock.CollectionAdapter;

import java.util.ArrayList;

public class HomeCollectionAdapter extends CollectionAdapter {

    public HomeCollectionAdapter(Fragment fragment, ArrayList<Fragment> fragmentArrayList) {
        super(fragment, fragmentArrayList);
    }
}
