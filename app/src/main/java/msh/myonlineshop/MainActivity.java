package msh.myonlineshop;


import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

//import androidx.appcompat.R;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


import msh.myonlineshop.fragments.AboutFragment;
import msh.myonlineshop.fragments.BasketFragment;
import msh.myonlineshop.fragments.BlogFragment;
import msh.myonlineshop.fragments.HomeFragment;
import msh.myonlineshop.fragments.LoginFragment;
import msh.myonlineshop.fragments.ProductsFragment;
import msh.myonlineshop.handlers.CurrentUserHandler;
import msh.myonlineshop.models.User;
import msh.myonlineshop.models.base.ServiceResponse;
import msh.myonlineshop.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle actionBarDrawerToggle ;
    private DrawerLayout mainDrawer;
    private MaterialToolbar topAppBar;
    private BottomNavigationView bottomNav;
    private FrameLayout mainFrameLayout;
    private NavigationView sideNav;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        init();

////temp _ to test connection::::
//        User user = new User("ab","Pass1234");
//        System.out.println("uuuuu");
//        //
//        UserService.login(new Callback<ServiceResponse<User>>() {
//            @Override
//            public void onResponse(Call<ServiceResponse<User>> call, Response<ServiceResponse<User>> response) {
//                System.out.println("rrrrrrrrrrrrr");
//                if(response.isSuccessful() && response.body() != null)
//                    if(!response.body().isHasError())
//                    {
//                        System.out.println("yohoooooooooooo");
//                        User userLogined = response.body().getDataList().get(0);
//                        //
////                        getUserLoginedProfile(userLogined);
//                        System.out.println("tokennnn: "+userLogined.getToken());
//                    }
//                    else
//                    {
//                        System.out.println("Server Response has and error: "
//                                + response.body().getMessage());
//                    }
//                else
//                    System.out.println("Server Response is empty perhaps ");
//            }
//
//            @Override
//            public void onFailure(Call<ServiceResponse<User>> call, Throwable t) {
//                System.out.println("Server Failure: "+ t.getMessage());
//            }
//        }, user);

    }

    void init()
    {
        bindViews();
        syncMenu();
        linkTopAppBarWithSideNavigationView();
        sideNavItemSelectedListener();
        bottomNavItemSelectedListener();
        bottomNav.setSelectedItemId(R.id.products);
    }


    void bindViews()
    {
        mainDrawer = findViewById(R.id.mainDrawer);
        topAppBar = findViewById(R.id.topAppBar);
        bottomNav = findViewById(R.id.bottomNav);
        mainFrameLayout = findViewById(R.id.mainFrameLayout);
        sideNav = findViewById(R.id.sideNav);
    }


    public void syncMenu() {
        View header = sideNav.getHeaderView(0);
        TextView fullName = header.findViewById(R.id.full_name);
        TextView username = header.findViewById(R.id.username);
        if (CurrentUserHandler.IsLoggedIn()) {
            sideNav.getMenu().clear();
            sideNav.inflateMenu(R.menu.drawer_loggined_menu);
            fullName.setText(CurrentUserHandler.getCurrentUser().getFullName());
            username.setText("(" + CurrentUserHandler.getCurrentUser().getUsername() + ")");
        } else {
            sideNav.getMenu().clear();
            sideNav.inflateMenu(R.menu.drawer_menu);
            fullName.setText("Full Name ...");
            username.setText("(username ...)");
        }
    }

    private void linkTopAppBarWithSideNavigationView() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mainDrawer,
                topAppBar,
                R.string.open,
                R.string.close
        );
        mainDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void sideNavItemSelectedListener() {
        sideNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                System.out.println("side navvvvv");
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                if(item.getItemId() == R.id.login)
                    fragmentTransaction.replace(R.id.mainFrameLayout, new LoginFragment(MainActivity.this));
                else if(item.getItemId() == R.id.about)
                    fragmentTransaction.replace(R.id.mainFrameLayout, new AboutFragment());

                fragmentTransaction.commit();
                mainDrawer.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
    }

    private void bottomNavItemSelectedListener() {
        NavigationBarView.OnItemSelectedListener lsnr = new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                System.out.println("id is :::::::::::" + id);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if(id == R.id.home) {
                    fragmentTransaction.replace(R.id.mainFrameLayout, new HomeFragment(MainActivity.this));
                }
                else if(id == R.id.products) {
                    fragmentTransaction.replace(R.id.mainFrameLayout, new ProductsFragment(MainActivity.this));
                }
                else if(id == R.id.blog)
                    fragmentTransaction.replace(R.id.mainFrameLayout, new BlogFragment(MainActivity.this));
                else if(id == R.id.basket)
                    fragmentTransaction.replace(R.id.mainFrameLayout, new BasketFragment(MainActivity.this));

                fragmentTransaction.commit();
                return true;
            }
        };
        bottomNav.setOnItemSelectedListener(lsnr);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
//        Snackbar snackbar = Snackbar.make(mainFrame, R.string.twice_back, Snackbar.LENGTH_LONG);
//        snackbar.getView().setTranslationY(-130);
//        snackbar.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }



//    public void syncBadges() {
//        CardDBHandler cardDBHandler = new CardDBHandler(this);
//        int basketCount = cardDBHandler.getAllBasketDataCount();
//        BadgeDrawable badge = bottomNav.getOrCreateBadge(R.id.basket);
//        if (basketCount > 0) {
//            badge.setVisible(true);
//            badge.setNumber(basketCount);
//        } else {
//            badge.setVisible(false);
//            badge.setNumber(0);
//        }
//    }







}