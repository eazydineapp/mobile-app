package com.eazydineapp.backend.service.api;

import com.eazydineapp.backend.ui.api.UIUserService;
import com.eazydineapp.backend.vo.User;

public interface UserService {

    public void getUserStatus(String userId, UIUserService uiUserService);
    public void updateUser(User user);
}
