package com.eazydineapp.backend.ui.api;


import com.eazydineapp.model.Group;

import java.util.List;

public interface UIGroupService {
    void displayAllGroups(List<Group> groups);
    void displayGroup(Group group);
}
