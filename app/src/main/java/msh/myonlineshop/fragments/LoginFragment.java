package msh.myonlineshop.fragments;


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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

import msh.myonlineshop.MainActivity;
import msh.myonlineshop.R;
import msh.myonlineshop.localDbHandler.BasketItemDbHandler;
import msh.myonlineshop.localDbHandler.CurrentUserHandler;
import msh.myonlineshop.localDbHandler.UserDbHandler;
import msh.myonlineshop.models.User;
import msh.myonlineshop.models.base.ServiceResponse;
import msh.myonlineshop.services.UserService;
import msh.myonlineshop.utlities.MsgUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private MainActivity mainActivity;
    private LinearLayout loginLinearLayout;
    private TextInputLayout txtUsername, txtPassword;
    private Button btnLogin;
    private String strUser, strPass;
    private ProgressBar progressBar;


    public LoginFragment(MainActivity activity) {
        this.mainActivity = activity;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        init(vg);
        return vg;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void init(ViewGroup vg)
    {
        bindView(vg);
        btnLoginListener();
    }

    private void bindView(ViewGroup vg)
    {
        txtUsername = vg.findViewById(R.id.txtUsername);
        txtPassword = vg.findViewById(R.id.txtPassword);
        progressBar = vg.findViewById(R.id.progressbar);
        btnLogin = vg.findViewById(R.id.btnLogin);
        loginLinearLayout = vg.findViewById(R.id.loginLinearLayout);
    }

    private void btnLoginListener()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("btnLogin.setOnClickListener");
                String userName = txtUsername.getEditText().getText().toString().trim();
                String password = txtPassword.getEditText().getText().toString().trim();

                if(userName.equals("") || password.equals(""))
                {
                    showMsgBySnackbar("Please fill in all the required fields.");
                    return;
                }
                else
                {
                    showBtnLogin(false);
                    User user = new User(userName,password);
                    //
                    System.out.println("LoginFragment_to UserService.login");
                    //
                    UserService.login(new Callback<ServiceResponse<User>>() {
                        @Override
                        public void onResponse(Call<ServiceResponse<User>> call, Response<ServiceResponse<User>> response) {
                            if(response.isSuccessful() && response.body() != null)
                                if(!response.body().isHasError())
                                {
                                    User userLogined = response.body().getDataList().get(0);
                                    //
                                    getUserLoginedProfile(userLogined);
                                }
                                else
                                {
                                    showMsgBySnackbar("Server Response has and error: "
                                                    + response.body().getMessage());
                                    showBtnLogin(true);
                                }
                            else
                                showBtnLogin(true);
                        }

                        @Override
                        public void onFailure(Call<ServiceResponse<User>> call, Throwable t) {
                            showMsgBySnackbar("Server Failure: "+ t.getMessage());
                            showBtnLogin(true);
                        }
                    }, user);
                }
            }
        });
    }

    private void getUserLoginedProfile(User userLogined) {
        System.out.println("getUserLoginedProfile");
        //
        UserDbHandler usrDbHandler = new UserDbHandler(mainActivity);
        try {
            usrDbHandler.deleteAllUsers();
        }
        catch (Exception e){}
        usrDbHandler.addData(userLogined.getContentValues());
        //
        UserService.getUserInfoFromServer(new Callback<ServiceResponse<User>>() {
            @Override
            public void onResponse(Call<ServiceResponse<User>> call, Response<ServiceResponse<User>> response) {
                if(response.isSuccessful() && response.body()!=null)
                    if(!response.body().isHasError())
                    {
                        User userWithProfile = response.body().getDataList().get(0);
                        Long cid = userWithProfile.getCustomerId();
                        //
                        userLogined.setCustomerId(cid);
                        CurrentUserHandler.setCurrentUser(userLogined);
                        //
                        //MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.syncMenu();
                        ////
                        BasketItemDbHandler basketItemDbHandler = new BasketItemDbHandler(mainActivity);
                        int count = basketItemDbHandler.getAllBasketDataCount();
                        //
                        FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                        if(count > 0) {
                            //go to basket
                            transaction.replace(R.id.mainFrameLayout, new BasketFragment(mainActivity));
                        }
                        else {
                            //go to home page
                            transaction.replace(R.id.mainFrameLayout, new HomeFragment(mainActivity));
                        }
                        transaction.commit();
                        ////
                    }
                //
                showBtnLogin(true);
            }

            @Override
            public void onFailure(Call<ServiceResponse<User>> call, Throwable t) {
                showMsgBySnackbar("Server Failure getUserLoginedProfile: "
                                    + t.getMessage());
                showBtnLogin(true);
            }
        }
        , userLogined.getToken());
    }


    private void showMsgBySnackbar(String msg)
    {
        MsgUtility.showMsgLong(loginLinearLayout, msg, -130);
    }

    private void showBtnLogin(boolean show)
    {
        if(show)
        {
            btnLogin.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
        else
        {
            btnLogin.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}