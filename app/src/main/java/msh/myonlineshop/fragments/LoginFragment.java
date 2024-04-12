package msh.myonlineshop.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import msh.myonlineshop.R;

public class LoginFragment extends Fragment {

    private Activity activity;
    private LinearLayout mainView;
    private TextInputLayout txtUsername, txtPassword;
    private Button btnLogin;
    private String strUser, strPass;
    private ProgressBar progressBar;


    public LoginFragment(Activity activity) {
        this.activity = activity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        return vg;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}