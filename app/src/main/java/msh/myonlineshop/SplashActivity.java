package msh.myonlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import msh.myonlineshop.handlers.UserHandler;
import msh.myonlineshop.models.User;
import msh.myonlineshop.utlities.InternetConnection;

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
            checkLocalUserAndGetServerUserInfo();
        }
        else
        {
            Intent intent = new Intent(SplashActivity.this, ConnectionError.class);
            startActivity(intent);
        }
    }

    private void checkLocalUserAndGetServerUserInfo() {
        UserHandler userHandler = new UserHandler(this);
        //build table, if does not exist
        userHandler.checkAndCreateTable();
        User user = userHandler.getLatestUser();
        if(user!=null)
        {
            //get the information from server as client
            userHandler
        }
        else
        {
            Intent intent = new Intent(SplashActivity.this, ConnectionError.class);
            startActivity(intent);
        }
    }

}