package com.fooddelivery.service;

import java.util.List;

import com.fooddelivery.Exception.InvalidItemIdException;
import com.fooddelivery.Exception.InvalidMenuItemException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.entity.MenuItems;

public interface MenuItemsService {
	MenuItems getMenuByItemId(int menuItem_id) throws InvalidItemIdException;
	List<MenuItems> getMenuItemsByRestaurantId(int restaurant_id) throws InvalidRestaurantIdException;
    public MenuItems addMenuItems(int restaurant_id, MenuItems menuItems)throws InvalidRestaurantIdException;
 	public MenuItems updateMenuItems(int restaurant_id, int item_id, MenuItems menuItems)throws InvalidMenuItemException;
 	public void deleteMenuItems(int restaurant_id, int item_id)throws InvalidItemIdException;
}
