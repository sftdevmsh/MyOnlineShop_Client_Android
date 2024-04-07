package msh.myonlineshop.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import msh.myonlineshop.R;

public class BasketFragment extends Fragment {
    Activity activity;
    public BasketFragment(Activity activity) {
        this.activity = activity;
    }
}