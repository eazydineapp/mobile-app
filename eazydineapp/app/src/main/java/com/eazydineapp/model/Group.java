package com.eazydineapp.model;

import java.util.ArrayList;


public class Group {
    private String name;
    private int adminId;



    private ArrayList<User> members;

    public Group(String name, int adminId, ArrayList<User> members) {
        this.name = name;
        this.adminId = adminId;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public ArrayList <User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList <User> members) {
        this.members = members;
    }
}
