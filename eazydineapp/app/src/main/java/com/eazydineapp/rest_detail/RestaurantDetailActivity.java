package com.eazydineapp.rest_detail;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eazydineapp.R;
import com.eazydineapp.activity.CartActivity;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.service.api.RestaurantService;
import com.eazydineapp.backend.service.impl.OrderServiceImpl;
import com.eazydineapp.backend.service.impl.RestaurantServiceImpl;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.ui.api.UIRestaurantService;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.backend.vo.Category;
import com.eazydineapp.backend.vo.Item;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.adapter.ViewPagerStateAdapter;
import com.eazydineapp.backend.vo.Restaurant;
import com.eazydineapp.model.CuisineCategory;
import com.eazydineapp.model.RestaurantMenu;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RestaurantDetailActivity extends AppCompatActivity {

    private static String EXTRA_REST_NAME = "restaurant_name";

    private RestaurantService restaurantService = new RestaurantServiceImpl();
    private Gson gson = new Gson();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private String title;
    private TextView cartNotificationCount;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        initUi();
        setupViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rest_detail, menu);
        View cartActionView = menu.findItem(R.id.action_cart).getActionView();
        cartNotificationCount = ((TextView) cartActionView.findViewById(R.id.notification_count));
        cartActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });
        setCartCount();
        return super.onCreateOptionsMenu(menu);
    }

    private void setCartCount() {
        AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();
        String userId = storagePrefUtil.getRegisteredUser(this);

        OrderService orderService = new OrderServiceImpl();
        orderService.getCartByUser(userId, new UIOrderService() {
            @Override
            public void displayAllOrders(List<Order> orders) {
            }

            @Override
            public void displayOrder(Order dbOrder) {
                int NOTIFICATION_COUNT = 0;
                if(null != dbOrder && dbOrder.getRestaurantId().equals(String.valueOf(restaurant.getId()))) {
                    NOTIFICATION_COUNT = dbOrder.getItemList().size();
                }
                if (cartNotificationCount != null) {
                    if (NOTIFICATION_COUNT <= 0) {
                        cartNotificationCount.setVisibility(View.GONE);
                    } else {
                        cartNotificationCount.setVisibility(View.VISIBLE);
                        cartNotificationCount.setText(String.valueOf(NOTIFICATION_COUNT));
                    }
                }
            }
        });
    }

    private void setupViewPager() {
        CuisineFragment cuisineFragment = new CuisineFragment();
        getCategories(restaurant.getId(), cuisineFragment);
        getMenu(restaurant.getId(), cuisineFragment);

        ViewPagerStateAdapter adapter = new ViewPagerStateAdapter(getSupportFragmentManager());
        adapter.addFrag(cuisineFragment, "Cuisine");
        //adapter.addFrag(new ReviewFragment(), "Review");
        adapter.addFrag(new InfoFragment(), "Info");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getMenu(final Long restaurantId, final CuisineFragment cuisineFragment) {
        restaurantService.getMenuByRestaurantId(getApplicationContext(), restaurantId, new UIRestaurantService() {
            @Override
            public void onSuccess(JSONObject jsonResponse) { }

            @Override
            public void onSuccess(JSONArray jsonArray) {try {
                HashMap<String,ArrayList<RestaurantMenu>> menuMap = new HashMap<>();
                for(int index=0; index < jsonArray.length(); index++) {
                    com.eazydineapp.backend.vo.Menu menu = gson.fromJson(jsonArray.get(index).toString(), com.eazydineapp.backend.vo.Menu.class);
                    Set<Item> items = menu.getItems();
                    for(Item item : items) {
                        if(null == menuMap.get(item.getCategory().getName())) {
                            ArrayList<RestaurantMenu> restaurantMenu = new ArrayList<RestaurantMenu>();
                            menuMap.put(item.getCategory().getName(), restaurantMenu);
                        }
                        ArrayList<RestaurantMenu> restaurantMenu = menuMap.get(item.getCategory().getName());
                        restaurantMenu.add(new RestaurantMenu(String.valueOf(item.getId()),item.getName(),item.getDescription(),item.getCategory().getName(), item.getPrice(), item.getImagepath(),
                                String.valueOf(restaurant.getId()), restaurant.getName(), restaurant.getCity()));
                        menuMap.put(item.getCategory().getName(), restaurantMenu);
                    }
                }
                cuisineFragment.setMenu(menuMap);
            }catch (JSONException e) {
                e.printStackTrace();
            } }

            @Override
            public void onError(String message) {
                Log.e("Get Restauarnts", message);
            }
        });
    }

    private void getCategories(Long restaurantId, final CuisineFragment cuisineFragment) {
        restaurantService.getCategoriesByRestaurantId(getApplicationContext(), restaurantId, new UIRestaurantService() {
            @Override
            public void onSuccess(JSONObject jsonResponse) { }

            @Override
            public void onSuccess(JSONArray jsonArray) {try {
                ArrayList<CuisineCategory> categories = new ArrayList<>();
                for(int index=0; index < jsonArray.length(); index++) {
                    Category category = gson.fromJson(jsonArray.get(index).toString(), Category.class);
                    categories.add(new CuisineCategory(category.getName()));
                }
                cuisineFragment.setCategory(categories);
            }catch (JSONException e) {
                e.printStackTrace();
            } }

            @Override
            public void onError(String message) {
                Log.e("Get Restauarnts", message);
            }
        });
    }

    private void initUi() {
        restaurant = (Restaurant) getIntent().getSerializableExtra(EXTRA_REST_NAME);

        final AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();
        storagePrefUtil.putKeyValue(this, "RESTAURANT_ID", String.valueOf(restaurant.getId()));

        TextView restaurantNameView = findViewById(R.id.restName);
        restaurantNameView.setText(restaurant.getName());

        TextView restaurantDescView = findViewById(R.id.restDesc);
        restaurantDescView.setText(restaurant.getTagline());

        ImageView restaurantImageView = findViewById(R.id.restCoverImage);
        Glide.with(getApplicationContext()).load(restaurant.getRestaurantbgimage()).into(restaurantImageView);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        appBarLayout = findViewById(R.id.appBarLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        title = restaurant.getName();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        collapsingToolbarLayout.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //userDetailsSummaryContainer.setVisibility(View.INVISIBLE);
                    collapsingToolbarLayout.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    //userDetailsSummaryContainer.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    public static Intent newIntent(Context context, Restaurant restaurant) {
        Intent intent = new Intent(context, RestaurantDetailActivity.class);
        intent.putExtra(EXTRA_REST_NAME, restaurant);
        return intent;
    }
}
