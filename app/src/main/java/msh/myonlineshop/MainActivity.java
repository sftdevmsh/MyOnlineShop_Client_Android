package msh.myonlineshop;


import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

//import androidx.appcompat.R;
//import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle actionBarDrawerToggle ;
    private DrawerLayout mainDrawer;
    private MaterialToolbar topAppBar;
    private BottomNavigationView bottomNav;
//    private FrameLayout mainFrame;
    private NavigationView sideNav;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init()
    {
        bindViews();
        syncMenu();
        linkTopAppBarWithSideNavigationView();
        sideNavItemSelectedListener();
        bottomNavItemSelectedListener();
    }

    void bindViews()
    {
        mainDrawer = findViewById(R.id.mainDrawer);
        topAppBar = findViewById(R.id.topAppBar);
//        bottomNav = findViewById(R.id.bottomNav);
//        mainFrame = findViewById(R.id.mainFrame);
        sideNav = findViewById(R.id.sideNav);
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
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home:
                        fragmentTransaction.replace(R.id.mainFrameLayout, new HomeFragment(MainActivity.this));
                        break;
                    case R.id.products:
                        fragmentTransaction.replace(R.id.mainFrameLayout, new ProductsFragment(MainActivity.this));
                        break;
                    case R.id.blog:
                        fragmentTransaction.replace(R.id.mainFrameLayout, new BlogFragment(MainActivity.this));
                        break;
                    case R.id.basket:
                        fragmentTransaction.replace(R.id.mainFrameLayout, new BasketFragment(MainActivity.this));
                        break;
                }
                fragmentTransaction.commit();
                return true;
            }
        });
    }




    @Override
    protected void onResume() {
        super.onResume();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Pass the event to ActionBarDrawerToggle, if it returns
//        // true, then it has handled the app icon touch event
//        System.out.println("onOptionsItemSelected");
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            System.out.println("trueeeeeeeeeeeeee");
//            return true;
//        }
//        // Handle your other action bar items...
//
//        return super.onOptionsItemSelected(item);
//    }


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