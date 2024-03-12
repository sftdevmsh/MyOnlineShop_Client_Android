package msh.myonlineshop;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import msh.myonlineshop.handlers.UserDbHandler;
import msh.myonlineshop.handlers.CurrentUserHandler;
import msh.myonlineshop.models.User;
import msh.myonlineshop.models.base.ServiceResponse;
import msh.myonlineshop.services.UserService;
import msh.myonlineshop.utlities.CommonUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText txtName;
    TextInputEditText txtPass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CurrentUserHandler.nullifyUser(LoginActivity.this);
    }

    void init()
    {
        bindViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = txtName.getText().toString();
                String strPass = txtPass.getText().toString();

                if(strName.trim().isEmpty() || strPass.trim().isEmpty())
                    CommonUtility.showMsg(btnLogin, getString(R.string.fill_required_fields));

                else
                {
                    User user = new User();
                    user.setUsername(strName);
                    user.setPassword(strPass);
                    UserService.login(new Callback<ServiceResponse<User>>() {
                        @Override
                        public void onResponse(Call<ServiceResponse<User>> call, Response<ServiceResponse<User>> response) {
                            System.out.println("responseeeeeeeeeeeeee");
                            System.out.println(response);
                            if(response.body() != null && response.isSuccessful())
                            {
                                if(!response.body().isHasError())
                                {
//                                    User userInfo = response.body().getDataList().get(0);
////                                    user.setCustomerId(userInfo.getCustomerId());
//                                    CurrentUserHandler currentUserHandler = new CurrentUserHandler();
//                                    currentUserHandler.setCurrentUser(user);
                                    CommonUtility.showMsg(btnLogin, getString(R.string.logged_in_successfully));
//                                    openMainActivity();

//
                                    User user = response.body().getDataList().get(0);
                                    UserDbHandler dbHandler = new UserDbHandler(LoginActivity.this);
                                    dbHandler.deleteAllUsers();
                                    dbHandler.addData(user.getContentValues());
                                    CurrentUserHandler currentUserHandler = new CurrentUserHandler();
                                    currentUserHandler.setCurrentUser(user);
//                                    MainActivity mainActivity = (MainActivity) getActivity();
//                                    mainActivity.syncMenu();
//
//                                    FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
//                                    transaction.replace(R.id.mainFrame, new HomeFragment(activity));
//                                    transaction.commit();
//
//                                    showError("Welcome back " + user.getFullName());
//                                    getUserInfo(user.getToken());
                                }
                                else {
//                                    CurrentUserHandler.nullifyUser(LoginActivity.this);
                                    CommonUtility.showMsg(btnLogin, getString(R.string.server_response_body_has_error));
                                }
                            }
                            else {
                                CommonUtility.showMsg(btnLogin, getString(R.string.server_response_failed));
                            }
                        }

                        @Override
                        public void onFailure(Call<ServiceResponse<User>> call, Throwable t) {
                            CommonUtility.showMsg(btnLogin, getString(R.string.server_failed_to_respond));
                            System.out.println(getString(R.string.server_failed_to_respond));
                            System.out.println(t.getMessage());
                        }
                    }, user);
                }

            }
        });
    }

    void bindViews()
    {
        txtName = findViewById(R.id.txtName);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
    }

    void openMainActivity()
    {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

}