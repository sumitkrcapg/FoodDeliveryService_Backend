package com.fooddelivery.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.entity.MenuItems;

@Repository
public interface MenuItemsRepository extends JpaRepository<MenuItems,Integer> {
	
	/**
     * Finds all menu items associated with a specific restaurant.
     * 
     * @param restaurant_id The ID of the restaurant.
     * @return A list of menu items for the specified restaurant.
     */
    @Query("SELECT mi FROM MenuItems mi WHERE mi.restaurants.restaurant_id=:restaurant_id")
	List<MenuItems> findByRestaurantId(@Param("restaurant_id")int restaurant_id);
    
    /**
     * Finds a menu item by its ID and associated restaurant ID.
     * 
     * @param item_id The ID of the menu item.
     * @param restaurant_id The ID of the restaurant to which the menu item belongs.
     * @return The menu item with the specified ID and restaurant ID, or null if not found.
     */
    @Query("SELECT mi FROM MenuItems mi WHERE mi.id = :item_id AND mi.restaurants.restaurant_id = :restaurant_id")
	MenuItems findByIdAndRestaurantId(int item_id, int restaurant_id);
 
    /**
     * Deletes a menu item associated with a specific restaurant by item ID and restaurant ID.
     * 
     * @param restaurant_id The ID of the restaurant.
     * @param item_id The ID of the menu item to delete.
     */
	@Modifying
	@Query("DELETE FROM MenuItems mi WHERE mi.restaurants.restaurant_id =:restaurant_id AND mi.id=:item_id")
	void deleteByRestaurantIdAndItemId(int restaurant_id, int item_id);
	
	/**
     * Deletes all menu items associated with a specific restaurant.
     * 
     * @param restaurant_id The ID of the restaurant.
     */
	@Modifying
	@Query("delete from MenuItems mi where mi.restaurants.restaurant_id=:restaurant_id")
	void deleteAllMenuItemsByResturantId(@Param("restaurant_id") int restaurant_id);
}