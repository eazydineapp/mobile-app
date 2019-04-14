package com.eazydineapp.backend.service.impl;

import com.eazydineapp.backend.dao.api.WaitlistDAO;
import com.eazydineapp.backend.dao.impl.WaitlistDAOImpl;
import com.eazydineapp.backend.service.api.WaitlistService;
import com.eazydineapp.backend.ui.api.UIWaitlistService;
import com.eazydineapp.backend.vo.Waitlist;

public class WaitlistServiceImpl implements WaitlistService {

    WaitlistDAO waitlistDAO = new WaitlistDAOImpl();

    @Override
    public void addUserToWaitList(Waitlist waitlist) {
        try {
            waitlistDAO.addUserToWaitList(waitlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getWaitStatus(String restaurantId, String userId, UIWaitlistService UIWaitlistService) {
        try {
            waitlistDAO.getUsers(restaurantId, userId, UIWaitlistService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
