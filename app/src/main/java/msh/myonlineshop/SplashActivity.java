package msh.myonlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import msh.myonlineshop.handlers.UserDbHandler;
import msh.myonlineshop.handlers.CurrentUserHandler;
import msh.myonlineshop.models.User;
import msh.myonlineshop.models.base.ServiceResponse;
import msh.myonlineshop.services.UserService;
import msh.myonlineshop.utlities.InternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Boolean isNetworkAvailable = InternetConnection.isNetworkAvailable(getApplication());
        if(isNetworkAvailable)
        {
            getClientUserInfo();
        }
        else
        {
//            openErrorActivity("Please check the Internet connection ...");
            openMainActivity();
        }
    }

    private void getClientUserInfo()
    {
        UserDbHandler userDbHandler = new UserDbHandler(this);
        //build table, if does not exist
        userDbHandler.checkAndCreateTable();

//        userDbHandler.deleteAllUsers();
//        CurrentUserHandler.setCurrentUser(null);
        CurrentUserHandler.nullifyUser(this);

        User user = userDbHandler.getLatestUser();
        if(user!=null)
        {
            String token = user.getToken();
            if(token != null && !token.isEmpty()) {
                //get the information from server as client, and set CurrentUser
                UserService.getUserInfoFromServer(new Callback<ServiceResponse<User>>() {
                    @Override
                    public void onResponse(Call<ServiceResponse<User>> call, Response<ServiceResponse<User>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (!response.body().isHasError()) {
                                User userInfo = response.body().getDataList().get(0);
                                user.setCustomerId(userInfo.getCustomerId());
                                userDbHandler.addData(user.getContentValues());
                                CurrentUserHandler.setCurrentUser(user);
                            }
                            else if (response.body().isHasError() && response.body().getMessage().toLowerCase().startsWith("jwt expired"))
                            {
                                //
                            }
                            else
                            {
                                //
                            }
                            openMainActivity();
                        }
                        else
                        {
                            openErrorActivity("incorrect server response, please try again ...");
                        }
                    }

                    @Override
                    public void onFailure(Call<ServiceResponse<User>> call, Throwable t)
                    {
                        openErrorActivity("server failed to response, please try again ...");
                    }
                }, token);
            }
            else
                openMainActivity();
        }
        else
        {
            openMainActivity();
        }
    }


    void openMainActivity()
    {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }

    void openErrorActivity(String msg)
    {
        Intent intent = new Intent(SplashActivity.this, ErrorActivity.class);
        intent.putExtra("msg", msg);
        startActivity(intent);
    }
}


//                                    User user = response.body().getDataList().get(0);
//                                    UserDbHandler dbHandler = new UserDbHandler(LoginActivity.this);
//                                    dbHandler.deleteAllUsers();
//                                    dbHandler.addData(user.getContentValues());
//                                    CurrentUserHandler currentUserHandler = new CurrentUserHandler();
//                                    currentUserHandler.setCurrentUser(user);
//                                    MainActivity mainActivity = (MainActivity) getActivity();
////                                    mainActivity.syncMenu();
////
////                                    FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
////                                    transaction.replace(R.id.mainFrame, new HomeFragment(activity));
////                                    transaction.commit();

//    private void openLoginActivity() {
//        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
//        startActivity(intent);
//    }
//    private void openLoginFragment() {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.mainFrame, new LoginFragment(this));
//    }

