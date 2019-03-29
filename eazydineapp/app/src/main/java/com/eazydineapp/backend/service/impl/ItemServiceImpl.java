package com.eazydineapp.backend.service.impl;


import com.eazydineapp.backend.dao.api.ItemDAO;
import com.eazydineapp.backend.dao.impl.ItemDAOImpl;
import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.service.api.ItemService;
import com.eazydineapp.backend.ui.api.UIItemService;
import com.eazydineapp.backend.vo.Item;

/**
 * Created by ravisha on 4/26/18.
 */

public class ItemServiceImpl implements ItemService {

    ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public void add(Item item) {
        try {
            itemDAO.add(item);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            itemDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read(String itemId, UIItemService uiItemService) {
        try {
            itemDAO.read(itemId,uiItemService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readAllforCook(String cookId, UIItemService uiItemService) {
        try {
            itemDAO.readAllforCook(cookId,uiItemService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readAll(UIItemService uiItemService) {
        try {
            itemDAO.readAll(uiItemService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String itemId, Item item) {
        try {
            itemDAO.update(itemId,item);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchItems(String searchKey, String searchValue, UIItemService uiItemService){
        try {
            itemDAO.searchItems(searchKey,searchValue,uiItemService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }
}
