package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.InvalidItemIdException;
import com.fooddelivery.Exception.InvalidMenuItemException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Repository.MenuItemsRepository;
import com.fooddelivery.Repository.RestaurantsRepository;
import com.fooddelivery.entity.MenuItems;
import com.fooddelivery.entity.Restaurants;

import jakarta.transaction.Transactional;
@Service
public class MenuItemsServiceImpl implements MenuItemsService {
	@Autowired
	private RestaurantsRepository restaurantsRepository;
	
	@Autowired
	private MenuItemsRepository menuItemsRepository;
	
	/**
     * Retrieves a menu item by its ID.
     * 
     * @param menuItem_id The ID of the menu item to retrieve.
     * @return The menu item if found.
     * @throws InvalidItemIdException if the menu item with the given ID does not exist.
     */
	@Override
	public MenuItems getMenuByItemId(int menuItem_id) throws InvalidItemIdException {
		Optional<MenuItems> menuItem = menuItemsRepository.findById(menuItem_id);
		
		if(menuItem.isEmpty()) {
			throw new InvalidItemIdException("Menu Item with ID: "+menuItem_id+" does not exist!");
		}
		return menuItem.get();
	}
	
	/**
     * Retrieves all menu items for a given restaurant.
     * 
     * @param restaurant_id The ID of the restaurant whose menu items are to be retrieved.
     * @return A list of menu items for the given restaurant.
     * @throws InvalidRestaurantIdException if the restaurant with the given ID does not exist.
     */
	@Override
	public List<MenuItems> getMenuItemsByRestaurantId(int restaurant_id) throws InvalidRestaurantIdException{
		if(menuItemsRepository.findByRestaurantId(restaurant_id).isEmpty()) {
			throw new InvalidRestaurantIdException("Restaurant with ID: "+restaurant_id+" does not exist!");
		}
		return menuItemsRepository.findByRestaurantId(restaurant_id);
	}
	
	/**
     * Adds a new menu item to a restaurant.
     * 
     * @param restaurant_id The ID of the restaurant to which the menu item will be added.
     * @param menuItems The menu item to add.
     * @return The added menu item.
     * @throws InvalidRestaurantIdException if the restaurant with the given ID does not exist.
     */
	@Override
	public MenuItems addMenuItems(int restaurant_id, MenuItems menuItems) throws InvalidRestaurantIdException {
		Optional<Restaurants> restaurant = restaurantsRepository.findById(restaurant_id);
		
		if(!(restaurant.isPresent())) {
			throw new InvalidRestaurantIdException("Restaurant Id is not found with Id: "+restaurant_id);
		}
		
		menuItems.setRestaurants(restaurant.get());
		return menuItemsRepository.save(menuItems);
	}

	/**
     * Updates an existing menu item.
     * 
     * @param restaurant_id The ID of the restaurant that owns the menu item.
     * @param item_id The ID of the menu item to update.
     * @param menuItems The updated menu item details.
     * @return The updated menu item.
     * @throws InvalidMenuItemException if the menu item details are invalid.
     */
	@Override
	public MenuItems updateMenuItems(int restaurant_id, int item_id , MenuItems menuItems) throws InvalidMenuItemException {
		if(menuItems.getItem_name() == null || menuItems.getItem_name().isEmpty()) {
			throw new InvalidMenuItemException("Item name cannot be empty");
		}
		if(menuItems.getItem_price() <= 0) {
			throw new InvalidMenuItemException("Price must be greater than 0");
		}
		
		MenuItems updatedItems = menuItemsRepository.findByIdAndRestaurantId(item_id, restaurant_id);
		updatedItems.setItem_name(menuItems.getItem_name());
		updatedItems.setItem_description(menuItems.getItem_description());
		updatedItems.setItem_price((float) menuItems.getItem_price());
		
		return menuItemsRepository.save(updatedItems);
	}
 
	/**
     * Deletes a menu item from a restaurant.
     * 
     * @param restaurant_id The ID of the restaurant that owns the menu item.
     * @param item_id The ID of the menu item to delete.
     * @throws InvalidItemIdException if the menu item or restaurant with the given ID does not exist.
     */
	@Override
	@Transactional
	public void deleteMenuItems(int restaurant_id, int item_id) throws InvalidItemIdException {
		 Optional<Restaurants> restaurants = restaurantsRepository.findById(restaurant_id);
		 Optional<MenuItems> existingMenuItem = menuItemsRepository.findById(item_id);
		 
		 if(!existingMenuItem.isPresent()) {
			 throw new InvalidItemIdException("Item id is not found");
		 }
		 if(!(restaurants.isPresent())) {
			 throw new InvalidItemIdException("Restaurant Id is not found with Id: "+restaurant_id);
		 }
		 
		 menuItemsRepository.deleteByRestaurantIdAndItemId(restaurant_id, item_id);	
	}
 
}
	
