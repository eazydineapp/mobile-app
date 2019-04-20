package com.eazydineapp.activity;


import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.service.api.UserService;
import com.eazydineapp.backend.service.impl.OrderServiceImpl;
import com.eazydineapp.backend.service.impl.UserServiceImpl;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.ui.api.UIUserService;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.OrderStatus;
import com.eazydineapp.backend.vo.User;
import com.eazydineapp.backend.vo.UserStatus;
import com.eazydineapp.fragment.DetailsFragment;
import com.eazydineapp.fragment.GroupsFragment;
import com.eazydineapp.fragment.HistoryFragment;
import com.eazydineapp.fragment.HomeFragment;
import com.eazydineapp.fragment.OrdersFragment;
import com.eazydineapp.R;
import com.eazydineapp.adapter.DrawerListAdapter;
import com.eazydineapp.model.NavItem;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private NavigationView navigationView;
    private Toolbar toolbar;
    private LinearLayout toolbarLayout;

    private Handler mHandler;
    private final String FRAG_TAG_HOME = "EazyDine";
    private final String FRAG_TAG_ORDERS = "My Orders";
    private final String FRAG_TAG_HISTORY = "History";
    private final String FRAG_TAG_DETAILS = "My Details";
    private final String FRAG_TAG_GROUPS = "Groups";

//    private final String FRAG_TAG_FAVORITE = "Recommendations";
//    private final String FRAG_TAG_REVIEWS = "My Reviews";
//    private final String FRAG_TAG_SUPPORT = "Contact us";

    private String fragTagCurrent = null;
    private int REQUEST_CODE_LOCATION = 99;
    AndroidStoragePrefUtil storagePrefUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        storagePrefUtil = new AndroidStoragePrefUtil();
        //readFromIntent(getIntent());

        ImageView background = findViewById(R.id.background);
        Glide.with(this).load(R.drawable.background).into(background);

        mNavItems.add(new NavItem("Home", "List of restaurants", R.drawable.ic_store_white_24dp));
        mNavItems.add(new NavItem("My orders", "Current orders", R.drawable.ic_shopping_cart_white_24dp));
        mNavItems.add(new NavItem("History", "Past orders", R.drawable.ic_restaurant_menu_white_24dp));
        mNavItems.add(new NavItem("My details", "Profile, address, payment info", R.drawable.ic_person_pin_white_24dp));
        mNavItems.add(new NavItem("Groups", "List of Groups", R.drawable.ic_group_white_24dp));
        /*mNavItems.add(new NavItem("Recommendations", "Recommendations", R.drawable.ic_favorite_white_24dp));
        mNavItems.add(new NavItem("Reviews", "List of reviews", R.drawable.ic_local_activity_white_24dp));
        mNavItems.add(new NavItem("Support", "Have a chat with us", R.drawable.ic_chat_white_24dp));
        mNavItems.add(new NavItem("Rate us", "Rate us on playstore", R.drawable.ic_thumb_up_white_24dp));*/

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        mDrawerList = (ListView) findViewById(R.id.navList);
        toolbarLayout = findViewById(R.id.locationContainer);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragTagCurrent = null;
                switch (position) {
                    case 0:
                        fragTagCurrent = FRAG_TAG_HOME;
                        break;
                    case 1:
                        fragTagCurrent = FRAG_TAG_ORDERS;
                        break;
                    case 2:
                        fragTagCurrent = FRAG_TAG_HISTORY;
                        break;
                    case 3:
                        fragTagCurrent = FRAG_TAG_DETAILS;
                        break;
                    case 4:
                        fragTagCurrent = FRAG_TAG_GROUPS;
//                    case 4:
//                        fragTagCurrent = FRAG_TAG_FAVORITE;
//                        break;
//                    case 5:
//                        fragTagCurrent = FRAG_TAG_REVIEWS;
//                        break;
//                    case 6:
//                        fragTagCurrent = FRAG_TAG_SUPPORT;
//                        break;
                    case 5:
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
                if (fragTagCurrent != null) {
                    loadFragment(fragTagCurrent);
                    fragTagCurrent = null;
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

       /* toolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, LocationActivity.class), REQUEST_CODE_LOCATION);
            }
        });*/

        loadFragment(FRAG_TAG_HOME);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRestaurantMenu();
    }

    private void loadRestaurantMenu() {
        final String userId = storagePrefUtil.getRegisteredUser(this);

        final UserService userService = new UserServiceImpl();
        userService.getUserStatus(userId, new UIUserService() {
            @Override
            public void displayUserInfo(User user) {
                if(user == null) {
                    userService.updateUser(new User(userId, UserStatus.START, ""));
                }
                else if (UserStatus.IN.equals(user.getStatus())) {
                    startRestaurantActivity();
                }else if(UserStatus.OUT.equals(user.getStatus())) {
                    clearSharedPref();
                }
            }
        });
    }

    private void startRestaurantActivity() {
        String restaurantId = storagePrefUtil.getValue(this, "RESTAURANT_ID");
        if (null != restaurantId || !restaurantId.isEmpty()) {
            Intent newIntent = new Intent(this, RestaurantActivity.class);
            newIntent.putExtra("eazydine-restaurantId", restaurantId);
            startActivity(newIntent);
        }
    }

    private void clearSharedPref() {
        String userId = storagePrefUtil.getRegisteredUser(this);
        storagePrefUtil.putKeyValue(this, "RESTAURANT_ID", null);
        UserService userService = new UserServiceImpl();
        userService.updateUser(new User(userId, UserStatus.START, ""));
        startActivity(new Intent(this, MainActivity.class));
        //onRestart();
    }

    private void loadFragment(final String fragTag) {
        Fragment fragment = null;
        toolbarLayout.setVisibility(View.GONE);
        switch (fragTag) {
            case FRAG_TAG_HOME:
                toolbarLayout.setVisibility(View.VISIBLE);
                fragment = new HomeFragment();
                break;
            case FRAG_TAG_DETAILS:
                fragment = new DetailsFragment();
                break;
            case FRAG_TAG_ORDERS:
                fragment = new OrdersFragment();
                break;
            case FRAG_TAG_HISTORY:
                fragment = new HistoryFragment();
                break;
            case FRAG_TAG_GROUPS:
                fragment = new GroupsFragment();
                break;
//            case FRAG_TAG_SUPPORT:
//                fragment = new SupportFragment();
//                break;
//            case FRAG_TAG_FAVORITE:
//                fragment = new FavoriteFragment();
//                break;
//            case FRAG_TAG_REVIEWS:
//                fragment = new MyReviewsFragment();
//                break;

        }

        getSupportActionBar().setTitle(fragTag);
        getSupportActionBar().setDisplayShowTitleEnabled(!fragTag.equals(FRAG_TAG_HOME));

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        final Fragment finalFragment = fragment;
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.mainFrame, finalFragment, fragTag);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mHandler == null)
            mHandler = new Handler();
        if (getSupportFragmentManager().findFragmentByTag(fragTag) == null)
            mHandler.post(mPendingRunnable);

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().findFragmentByTag(FRAG_TAG_HOME) == null)
            loadFragment(FRAG_TAG_HOME);
        else
            super.onBackPressed();
    }
}
