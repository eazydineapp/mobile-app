package com.eazydineapp.backend.dao.api;

import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.ui.api.UIUserService;
import com.eazydineapp.backend.vo.User;

public interface UserDAO {
    void getUserStatus(String userId, UIUserService uiUserService) throws ItemException;
    void updateUser(User user) throws ItemException;
}
