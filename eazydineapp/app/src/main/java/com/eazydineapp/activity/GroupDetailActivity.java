package com.eazydineapp.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eazydineapp.R;
import com.eazydineapp.adapter.GroupUserAdapter;
import com.eazydineapp.backend.vo.Item;
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
    private Item addUserIcon;
    private  String newUserNumber  = "";


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
                    onBackPressed();
                }else {
                    onBackPressed();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Show add user icon if the user is the admin
        if(adminFlag){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_add_user, menu);
            View addUser = menu.findItem(R.id.addUserIcon).getActionView();
            addUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getGroupMember();
                }
            });
        }
        return true;
    }

    private void getGroupMember(){
        AlertDialog.Builder builder = new AlertDialog.Builder(GroupDetailActivity.this);
        builder.setMessage("Enter the user number to add to this group");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                         newUserNumber = input.getText().toString();
                         members.add(new User("New User", "", newUserNumber));
                        // Add the new user to the group in firebase
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    private void setupGroupUserRecycler() {
        members.add(new User("User 1", "", "8897765534"));
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
