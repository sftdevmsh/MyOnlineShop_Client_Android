package msh.myonlineshop.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import msh.myonlineshop.R;

public class HomeFragment extends Fragment {

    Activity activity;
    RecyclerView rvProductCategories, rvNewProducts, rvPopularProducts;

    public HomeFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup vgRootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        init(vgRootView);
        return vgRootView;
    }

    private void init(ViewGroup vg) {
        bindViews(vg);
        fillRvProductCategories();
        fillRvNewProducts();
        fillRvPopularProducts();
    }


    private void fillRvProductCategories() {



    }

    private void fillRvNewProducts() {
        
    }

    private void fillRvPopularProducts() {
    }


    private void bindViews(ViewGroup vg) {
        rvProductCategories = vg.findViewById(R.id.rvProductCategories);
        rvNewProducts = vg.findViewById(R.id.rvNewProducts);
        rvPopularProducts = vg.findViewById(R.id.rvPopularProducts);
    }


}