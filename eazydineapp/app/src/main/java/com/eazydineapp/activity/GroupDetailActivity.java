package com.eazydineapp.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.adapter.GroupUserAdapter;
import com.eazydineapp.model.User;

import java.util.ArrayList;

public class GroupDetailActivity extends AppCompatActivity {

    private RecyclerView groupUsersRecycler;
    private TextView tv;
    private EditText editGroupName;
    private ImageView imageViewEditIcon;
    boolean adminFlag;
    String groupName;
    GroupUserAdapter groupUserAdapter;
    private ArrayList<User> members = new ArrayList <>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        adminFlag = getIntent().getExtras().getBoolean("Admin Flag");
        groupName = getIntent().getExtras().getString("Group Name");
        groupUserAdapter = new GroupUserAdapter(this);

        Toolbar toolbar = findViewById(R.id.toolbarGroup);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        imageViewEditIcon =  findViewById(R.id.imageViewEditIcon);
        editGroupName = findViewById(R.id.editGroupName);
        editGroupName.setText(groupName);
        editGroupName.setEnabled(false);
        groupUsersRecycler =  findViewById(R.id.groupUsersListRecycler);

        // Hide edit icon for new group
        // Display edit icon by default.
        if(groupName.equals("")){
            imageViewEditIcon.setVisibility(View.GONE);
            editGroupName.setEnabled(true);
        }

        imageViewEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editGroupName.setEnabled(true);
            }
        });

        setupGroupUserRecycler();
        tv = (TextView)findViewById(R.id.saveTextView);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("Save".equals(tv.getText())) {
                    createOrupdateGroup();
                }else {
                    onBackPressed();
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Show add user icon if the user is the admin
        if(adminFlag){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_add_user, menu);
        }
        return true;
    }

    private void setupGroupUserRecycler() {
        members.add(new User("User 1", "", "889776554"));
        members.add(new User("User 2", "", "8705395678"));
        groupUserAdapter.setGroupMemebers(members);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflator.inflate(R.layout.item_group_member, null);
        groupUserAdapter.notifyDataSetChanged();
        groupUsersRecycler.setLayoutManager(new LinearLayoutManager(this));
        groupUsersRecycler.setAdapter(groupUserAdapter);
    }

    private void  createOrupdateGroup(){

    }

}
