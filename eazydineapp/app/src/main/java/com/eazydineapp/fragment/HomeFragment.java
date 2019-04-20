package com.eazydineapp.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.activity.CartActivity;
import com.eazydineapp.activity.MainActivity;
import com.eazydineapp.activity.RefineActivity;
import com.eazydineapp.activity.RestaurantActivity;
import com.eazydineapp.adapter.FoodCategoryAdapter;
import com.eazydineapp.adapter.RestaurantAdapter;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.service.api.UserService;
import com.eazydineapp.backend.service.api.WaitlistService;
import com.eazydineapp.backend.service.impl.OrderServiceImpl;
import com.eazydineapp.backend.service.impl.UserServiceImpl;
import com.eazydineapp.backend.service.impl.WaitlistServiceImpl;
import com.eazydineapp.backend.ui.api.UIWaitlistService;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.backend.vo.OrderStatus;
import com.eazydineapp.backend.vo.User;
import com.eazydineapp.backend.vo.UserStatus;
import com.eazydineapp.backend.vo.WaitStatus;
import com.eazydineapp.backend.vo.Waitlist;

import java.io.UnsupportedEncodingException;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerFood, recyclerRestaurants;
    private EditText searchTab;
    private ImageView nfc;
    private String userId;
    private WaitlistService waitlistService = new WaitlistServiceImpl();
    private AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       /* inflater.inflate(R.menu.menu_home, menu);
        View cartActionView = menu.findItem(R.id.action_cart).getActionView();
        cartNotificationCount = ((TextView) cartActionView.findViewById(R.id.notification_count));
        cartActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });
        setCartCount();
        super.onCreateOptionsMenu(menu, inflater); */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        searchTab = view.findViewById(R.id.search_tab);
        nfc = view.findViewById(R.id.nfcTag);
        userId = storagePrefUtil.getRegisteredUser(this);
        //recyclerFood = view.findViewById(R.id.recyclerFood);
        // recyclerRestaurants = view.findViewById(R.id.recyclerRestaurants);
        view.findViewById(R.id.refine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RefineActivity.class));
            }
        });

        searchTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchTab.getText().toString();
                Intent newIntent = new Intent(getContext(), RestaurantActivity.class);
                if(null != searchText && !searchText.isEmpty()) {
                    newIntent.putExtra("eazydineapp-searchStr", searchText);
                }
                startActivity(newIntent);
            }
        });

        readFromIntent(getActivity().getIntent());
       /* nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restaurantId = "76";
                storagePrefUtil.putKeyValue(getActivity(), "RESTAURANT_ID", restaurantId);
                addUserToWaitList(userId, restaurantId);
            }
        });*/
        return view;
    }

    private void launchMenu(String restaurantId){
        Intent newIntent = new Intent(getContext(), RestaurantActivity.class);
        newIntent.putExtra("eazydine-restaurantId", restaurantId); //TODO NFC reader to load restaurant id
        startActivity(newIntent);
    }

    private void addUserToWaitList(final String userId, final String restaurantId) {
        waitlistService.getWaitStatus(restaurantId, userId, new UIWaitlistService() {
            @Override
            public void displayWaitStatus(Waitlist user) {
                if (null != user && (WaitStatus.Waiting.equals(user.getStatus()) || WaitStatus.Assigned.equals(user.getStatus()))) {
                    //do nothing
                    launchMenu(restaurantId);
                } else {
                    openDialog(restaurantId);
                }
            }
        });
    }

    private void openDialog(final String restaurantId) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Enter number of Seats");
        alert.setMessage("");

        final EditText input = new EditText(getActivity());
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                Waitlist waitlist = new Waitlist(userId, restaurantId, -1L, Integer.parseInt(value), WaitStatus.Waiting);
                waitlistService.addUserToWaitList(waitlist);
                UserService userService = new UserServiceImpl();
                userService.updateUser(new User(userId, UserStatus.IN, restaurantId));
                launchMenu(restaurantId);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // setupRecyclerFood();
//      setupRecyclerRestaurants();
    }

    private void setupRecyclerRestaurants() {
        recyclerRestaurants.setNestedScrollingEnabled(false);
        recyclerRestaurants.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerRestaurants.setAdapter(new RestaurantAdapter(getContext()));
    }

    private void setupRecyclerFood() {
        recyclerFood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerFood.setAdapter(new FoodCategoryAdapter(getContext()));
    }

    private void readFromIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }
    private void buildTagViews(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) return;

        String restaurantId = "";
//        String tagId = new String(msgs[0].getRecords()[0].getType());
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"
        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

        try {
            // Get the Text
            restaurantId = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }

        storagePrefUtil.putKeyValue(getActivity(), "RESTAURANT_ID", restaurantId);
        addUserToWaitList(userId, restaurantId);
    }
}
