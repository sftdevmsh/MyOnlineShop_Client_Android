package msh.myonlineshop.fragments;


import android.app.Activity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

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

}