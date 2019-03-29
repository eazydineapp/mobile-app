package com.eazydineapp.backend.dao.api;

import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.ui.api.UIItemService;
import com.eazydineapp.backend.vo.Item;



/**
 * Created by ravisha on 4/26/18.
 */

public interface ItemDAO {
    void add(Item item) throws ItemException;
    void read(String itemId, UIItemService uiItemService) throws ItemException;
    void delete(String id) throws Exception;
    void update(String id, Item item) throws ItemException;
    void readAllforCook(String cookId, UIItemService uiItemService) throws ItemException;
    void readAll(final UIItemService uiItemService) throws ItemException;
    void searchItems(String searchKey, String searchValue, final UIItemService uiItemService) throws ItemException;

}
