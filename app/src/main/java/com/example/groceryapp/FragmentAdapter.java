package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {

    private String owned_store_id;

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String owned_store_id) {
        super(fragmentManager, lifecycle);
        this.owned_store_id = owned_store_id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1 :
                return new MyStoreOrdersFragment();
            case 2 :
                return new MyStoreHistoryFragment();
        }

        return MyStoreProductsFragment.newInstance(owned_store_id);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
