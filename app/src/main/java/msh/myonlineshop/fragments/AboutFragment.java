package msh.myonlineshop.fragments;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import msh.myonlineshop.R;

public class AboutFragment extends Fragment {

    Activity activity;
    public AboutFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_about, container, false);
        init(vg);
        return vg;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void init(ViewGroup vg) {
        bindView(vg);
    }

    private void bindView(ViewGroup vg) {
    }
}