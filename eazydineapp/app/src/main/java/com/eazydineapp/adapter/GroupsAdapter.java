package com.eazydineapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.activity.GroupDetailActivity;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.model.Group;
import com.eazydineapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.MyViewHolder>{
    private Context context;
    private List<Group> dataList;
    boolean showContent = true;
    boolean adminFlag = true;
    private ArrayList<User> dataList1;

    public  GroupsAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.dataList1 = new ArrayList<>();
        this.dataList1.add(new User("User 1", "", "889776554"));
        this.dataList1.add(new User("User 2", "", "8705395678"));
        this.dataList.add(new Group("Group 1", 1, this.dataList1));
        this.dataList.add(new Group("Group 2", 2, this.dataList1));
    }
    @Override
    public GroupsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GroupsAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group, parent, false));
    }


    @Override
    public void onBindViewHolder(final GroupsAdapter.MyViewHolder holder, int position) {
        holder.setData(dataList.get(position));
        holder.groupBasicInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, GroupDetailActivity.class );
                intent.putExtra("Group Name", holder.groupName.getText());

                // AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();
                // int userId = storagePrefUtil.getRegisteredUser();
//                if(holder.groupAdmin.getText().toString().equals("1")){
//                    adminFlag = true;
//                }
                intent.putExtra("Admin Flag", adminFlag);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setGroups(List<Group> groups) {
        dataList = groups;
        notifyDataSetChanged();
    }

    public void setGroupMemebers(ArrayList<User> members){
        dataList1 = members;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView groupName, groupAdmin;
        private LinearLayout groupBasicInfo;
        private ImageView deleteGroupIcon;


        public MyViewHolder(View itemView) {
            super(itemView);
            groupBasicInfo = itemView.findViewById(R.id.groupBasicInfo);
            groupName = itemView.findViewById(R.id.groupName);
            groupAdmin = itemView.findViewById(R.id.groupAdmin);
            deleteGroupIcon = itemView.findViewById(R.id.deleteGroupIcon);
//            if(!adminFlag){
//                deleteGroupIcon.setVisibility(View.GONE);
//            }
        }

        public void setData(final Group group) {
            groupName.setText(group.getName());
            groupAdmin.setText(String.valueOf(group.getAdminId()));
        }
    }

}
