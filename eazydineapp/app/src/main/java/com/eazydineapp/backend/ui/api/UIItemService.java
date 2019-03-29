package com.eazydineapp.backend.ui.api;


import com.eazydineapp.backend.vo.Item;

import java.util.List;
import java.util.Map;

/**
 * Created by ravisha on 4/26/18.
 */

public interface UIItemService {
    void displayAllItems(List<Item> items);
    void displayItem(Item item);
    void displayItemsList(Map<String, Item> items);
//    void displayUser(User user);

}
