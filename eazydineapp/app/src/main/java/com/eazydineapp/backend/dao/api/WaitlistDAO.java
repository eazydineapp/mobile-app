package com.eazydineapp.backend.dao.api;

import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.ui.api.UIWaitlistService;
import com.eazydineapp.backend.vo.Waitlist;

public interface WaitlistDAO {

    void addUserToWaitList(Waitlist waitlist);
    void getUsers(String restaurantId, String userId, UIWaitlistService UIWaitlistService) throws ItemException;
}
