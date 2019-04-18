package com.eazydineapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class GroupUserAdapter extends RecyclerView.Adapter<GroupUserAdapter.MyViewHolder>{
    private Context context;
    private List<User> dataList;
    TextView groupMemberNumber;
    boolean showContent = true;
    boolean adminFlag = true;
    ImageView removeBtnimageView;

    public  GroupUserAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.dataList.add(new User("User 1", "", "889776554"));
        this.dataList.add(new User("User 2", "", "8705395678"));
    }
    @Override
    public GroupUserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GroupUserAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group_member, parent, false));
    }


    @Override
    public void onBindViewHolder( GroupUserAdapter.MyViewHolder holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setGroupMemebers(ArrayList<User> members){
        dataList = members;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView groupName, groupAdmin;
        private LinearLayout groupBasicInfo;
        private ImageView deleteGroupIcon;


        public MyViewHolder(View itemView) {
            super(itemView);
            groupMemberNumber = itemView.findViewById(R.id.groupMemberNumber);
            removeBtnimageView = itemView.findViewById(R.id.removeBtnimageView);
//            if(!adminFlag){
//                removeBtnimageView.setVisibility(View.GONE);
//            }
        }

        public void setData(final User member) {
            groupMemberNumber.setText(member.getPhoneNumber());
            removeBtnimageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Remove the member from this group in the database
                }
            });
        }
    }
}
