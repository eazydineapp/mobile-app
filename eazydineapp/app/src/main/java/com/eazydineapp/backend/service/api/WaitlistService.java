package com.eazydineapp.backend.service.api;

import com.eazydineapp.backend.ui.api.UIWaitlistService;
import com.eazydineapp.backend.vo.Waitlist;

public interface WaitlistService {

    void addUserToWaitList(Waitlist waitlist);
    void getWaitStatus(String restaurantId, String userId, UIWaitlistService uiWaitlistService);
}
