package com.eazydineapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eazydineapp.R;
import com.eazydineapp.activity.GroupDetailActivity;
import com.eazydineapp.adapter.GroupsAdapter;
import com.eazydineapp.backend.service.api.GroupService;
import com.eazydineapp.backend.service.impl.GroupServiceImpl;
import com.eazydineapp.backend.ui.api.UIGroupService;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.model.Group;

import java.util.List;

/**
 * Created by Harini on 12-04-2019.
 */
public class GroupsFragment extends Fragment {
    private RecyclerView recyclerGroups;
    private GroupsAdapter groupsAdapter;
    boolean adminFlag;

    public GroupsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        recyclerGroups = view.findViewById(R.id.recyclerGroups);
        groupsAdapter = new GroupsAdapter(getContext());

        loadUserGroupHistory();
        setHasOptionsMenu(true);
        return view;
    }

    private void loadUserGroupHistory() {
        AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();
        String userId = storagePrefUtil.getRegisteredUser(this);
        GroupService groupService = new GroupServiceImpl();
        groupService.getGroupByUser(userId, new UIGroupService() {
            @Override
            public void displayAllGroups(List<Group> groups) {
                groupsAdapter.setGroups(groups);
            }

            @Override
            public void displayGroup(Group group) {
                groupsAdapter.setGroupMemebers(group.getMembers());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupGroupsRecycler();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_add_group, menu);
        View addGroup = menu.findItem(R.id.addGroupIcon).getActionView();

        // By default the user is the admin of the group when he clicks new group icon
        adminFlag = true;
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GroupDetailActivity.class);
                intent.putExtra("Admin Flag", adminFlag);
                intent.putExtra("Group Name", "");
                startActivity(intent);
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void setupGroupsRecycler() {
        recyclerGroups.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerGroups.setAdapter(groupsAdapter);
    }
}
