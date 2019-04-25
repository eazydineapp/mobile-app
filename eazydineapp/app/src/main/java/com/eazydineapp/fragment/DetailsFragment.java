package com.eazydineapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eazydineapp.R;
import com.eazydineapp.adapter.AddressAdapter;
import com.eazydineapp.backend.service.api.UserService;
import com.eazydineapp.backend.service.impl.UserServiceImpl;
import com.eazydineapp.backend.ui.api.UIUserService;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.backend.vo.User;

public class DetailsFragment extends Fragment {
    private User user;
    private EditText name, email;
    private TextView phoneNumber;
    private UserService userService = new UserServiceImpl();

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        loadUser(view);
        name = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);


        ImageView saveUser = view.findViewById(R.id.saveUser);
        saveUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString());
                userService.updateUser(user);
            }
        });
        return view;
    }

    private void loadUser(final View view) {
        AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();
        String userId = storagePrefUtil.getRegisteredUser(this);
        userService.getUserStatus(userId, new UIUserService() {
            @Override
            public void displayUserInfo(User dbUser) {
                user = dbUser;
                name = view.findViewById(R.id.username);
                name.setText(user.getName());

                email = view.findViewById(R.id.email);
                email.setText(user.getEmail());

                phoneNumber = view.findViewById(R.id.phone);
                phoneNumber.setText(user.getPhoneNumber());
            }
        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
