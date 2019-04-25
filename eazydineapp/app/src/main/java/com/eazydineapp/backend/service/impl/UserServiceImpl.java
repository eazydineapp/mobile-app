package com.eazydineapp.backend.service.impl;

import com.eazydineapp.backend.dao.api.UserDAO;
import com.eazydineapp.backend.dao.impl.UserDAOImpl;
import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.service.api.UserService;
import com.eazydineapp.backend.ui.api.UIUserService;
import com.eazydineapp.backend.vo.User;

public class UserServiceImpl implements UserService {

    UserDAO userDao = new UserDAOImpl();

    @Override
    public void getUserStatus(String userId, UIUserService uiUserService) {
        try {
            userDao.getUserStatus(userId, uiUserService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            userDao.updateUser(user);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }
}
