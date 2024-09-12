package com.fooddelivery.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fooddelivery.Exception.InvalidItemIdException;
import com.fooddelivery.Exception.InvalidMenuItemException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.entity.MenuItems;
import com.fooddelivery.service.MenuItemsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "http://localhost:4200/")
public class MenuItemsController {
	@Autowired
	private MenuItemsService menuItemsService;
	
	/**
     * Endpoint to retrieve a specific menu item by its ID.
     * 
     * @param item_id The ID of the menu item to retrieve.
     * @return ResponseEntity containing the MenuItems object or HTTP status 404 NOT FOUND if the item does not exist.
     * @throws InvalidItemIdException If the item ID is invalid.
     */
	@GetMapping("/menuitem/{item_id}")
	public ResponseEntity<MenuItems> getMenuByItemId(@PathVariable("item_id") int item_id) throws InvalidItemIdException {
		try {
			MenuItems menuItem = menuItemsService.getMenuByItemId(item_id);
			return new ResponseEntity<MenuItems>(menuItem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<MenuItems>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
     * Endpoint to retrieve all menu items for a specific restaurant.
     * 
     * @param restaurant_id The ID of the restaurant whose menu items are to be retrieved.
     * @return ResponseEntity containing a list of MenuItems or HTTP status 404 NOT FOUND if the restaurant does not exist.
     * @throws InvalidRestaurantIdException If the restaurant ID is invalid.
     */
	@GetMapping("/{restaurant_id}/menu")
	public ResponseEntity<?> getMenuItemsByRestaurantId(@PathVariable("restaurant_id") int restaurant_id) throws InvalidRestaurantIdException {
		try {
			List<MenuItems> menuItem = menuItemsService.getMenuItemsByRestaurantId(restaurant_id);
			return new ResponseEntity<List<MenuItems>>(menuItem, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage() ,HttpStatus.NOT_FOUND);
		}
	}

	/**
     * Endpoint to add a new menu item to a specific restaurant's menu.
     * 
     * @param restaurant_id The ID of the restaurant where the new menu item will be added.
     * @param menuItems The MenuItems object to be added.
     * @return ResponseEntity containing the added MenuItems object and HTTP status 201 CREATED.
     * @throws InvalidRestaurantIdException If the restaurant ID is invalid.
     */
	@PostMapping("/{restaurant_id}/menu")
	public ResponseEntity<MenuItems> addmenuItemsByRestaurantId(@PathVariable("restaurant_id") int restaurant_id, @RequestBody @Valid MenuItems menuItems) throws InvalidRestaurantIdException {
		MenuItems savedItems = menuItemsService.addMenuItems(restaurant_id, menuItems);
		if (savedItems == null) {
			throw new InvalidRestaurantIdException("Restaurant Id is not found with Id: " + restaurant_id);
		}
		return new ResponseEntity<MenuItems>(savedItems, HttpStatus.CREATED);

	}

	/**
     * Endpoint to update an existing menu item for a specific restaurant.
     * 
     * @param restaurant_id The ID of the restaurant to which the menu item belongs.
     * @param item_id The ID of the menu item to update.
     * @param menuItems The MenuItems object with updated information.
     * @return ResponseEntity containing the updated MenuItems object and HTTP status 200 OK.
     * @throws InvalidMenuItemException If the menu item data is invalid.
     */
	@PutMapping("/{restaurant_id}/menu/{item_id}")
	public ResponseEntity<MenuItems> updatemenuItems(@PathVariable("restaurant_id") int restaurant_id, @PathVariable("item_id") int item_id, @RequestBody @Valid MenuItems menuItems) throws InvalidMenuItemException {
		if (menuItems.getItem_name() == null || menuItems.getItem_name().isEmpty()) {
			throw new InvalidMenuItemException("Item Name cannot be empty");
		}

		if (menuItems.getItem_price() <= 0) {
			throw new InvalidMenuItemException("Price must be greater than 0");
		}

		MenuItems updatedItems = menuItemsService.updateMenuItems(restaurant_id, item_id, menuItems);
		return new ResponseEntity<MenuItems>(updatedItems, HttpStatus.OK);
	}

	/**
     * Endpoint to delete a menu item from a specific restaurant's menu.
     * 
     * @param restaurant_id The ID of the restaurant from which the menu item will be deleted.
     * @param item_id The ID of the menu item to delete.
     * @return ResponseEntity with HTTP status 204 NO CONTENT if the deletion is successful.
     * @throws InvalidItemIdException If the item ID is invalid or not found.
     */
	@DeleteMapping("/{restaurant_id}/menu/{item_id}")
	public ResponseEntity<MenuItems> deletemenuItems(@PathVariable("restaurant_id") int restaurant_id, @PathVariable("item_id") int item_id) throws InvalidItemIdException {
		try {
			menuItemsService.deleteMenuItems(restaurant_id, item_id);
			return new ResponseEntity<MenuItems>(HttpStatus.NO_CONTENT);
		} catch (InvalidItemIdException e) {
			throw new InvalidItemIdException("Item Id is not found with Id: " + item_id);
		}
	}
}
