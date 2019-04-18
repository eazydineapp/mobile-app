package com.eazydineapp.backend.service.api;

import com.eazydineapp.backend.ui.api.UIGroupService;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.model.Group;

public interface GroupService {
    void getGroupByUser(String userId, UIGroupService uiGroupService);
    void updateGroup(Group group);
}
