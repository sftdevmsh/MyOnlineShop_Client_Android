package msh.myonlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import msh.myonlineshop.handlers.UserDbHandler;
import msh.myonlineshop.models.CurrentUserHandler;
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
            openErrorActivity("Please check the Internet connection ...");
        }
    }

    private void getClientUserInfo()
    {
        UserDbHandler userDbHandler = new UserDbHandler(this);
        //build table, if does not exist
        userDbHandler.checkAndCreateTable();
        User user = userDbHandler.getLatestUser();
        if(user!=null)
        {
            String token = user.getToken();
            //get the information from server as client, and set CurrentUser
            UserService.getUserInfoFromServer(new Callback<ServiceResponse<User>>()
            {
                @Override
                public void onResponse(Call<ServiceResponse<User>> call, Response<ServiceResponse<User>> response)
                {
                    if (response.isSuccessful() && response.body() != null)
                    {
                        CurrentUserHandler currentUserHandler = new CurrentUserHandler();
                        if (!response.body().isHasError())
                        {
                            User userInfo = response.body().getDataList().get(0);
                            user.setCustomerId(userInfo.getCustomerId());
                            currentUserHandler.setCurrentUser(user);
                            openMainActivity();
                        }
                        else if (response.body().isHasError() && response.body().getMessage().toLowerCase().startsWith("jwt expired"))
                        {
                            nullifyUser(currentUserHandler, userDbHandler);
                            openMainActivity();
                        }
                        else
                            openErrorActivity("incorrect server response, please try again ...");
                    }
                    else
                    {
                        openErrorActivity("incorrect server response, please try again ...");
                    }
                }
                @Override
                public void onFailure(Call<ServiceResponse<User>> call, Throwable t) {
                    openErrorActivity("server failed to response, please try again ...");
                }
            },token);
        }
        else
        {
            openErrorActivity("Null User Error");
        }
    }

    private void nullifyUser(CurrentUserHandler currentUserHandler, UserDbHandler userDbHandler) {
        currentUserHandler.setCurrentUser(null);
        userDbHandler.deleteAllUsers();
    }


    void openMainActivity()
    {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }

    void openErrorActivity(String msg)
    {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra("msg", msg);
        startActivity(intent);
    }
}