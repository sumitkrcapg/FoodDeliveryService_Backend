package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.DuplicateRestaurantIDException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Exception.NoSuchRestaurantIDException;
import com.fooddelivery.Repository.MenuItemsRepository;
import com.fooddelivery.Repository.RestaurantsRepository;
import com.fooddelivery.entity.Restaurants;

import jakarta.transaction.Transactional;

@Service
public class RestaurantsServiceImpl implements RestaurantsService {
	
	@Autowired
    private RestaurantsRepository restaurantsRepository;
	
	@Autowired
	private MenuItemsRepository menuItemsRepository;
	
	/**
     * Retrieves all restaurants from the repository.
     * 
     * @return List of all restaurants.
     */
	@Override
	public List<Restaurants> getAllRestaurants() {
		return restaurantsRepository.findAll();
	}

	/**
     * Updates the details of an existing restaurant.
     * 
     * @param restaurant The restaurant entity with updated details.
     * @return The updated restaurant entity.
     * @throws InvalidRestaurantIdException If the restaurant ID does not exist.
     */
	@Override
	@Transactional
	public Restaurants updateRestaurant(Restaurants restaurant) throws InvalidRestaurantIdException {
		Optional<Restaurants> previousRest = restaurantsRepository.findById(restaurant.getRestaurant_id());
		
		if(!(previousRest.isPresent())) {
			throw new InvalidRestaurantIdException("Invalid restaurant ID: "+restaurant.getRestaurant_id());
		}
		
		Restaurants restaurantsUpdated = previousRest.get();
		restaurantsUpdated.setRestaurant_name(restaurant.getRestaurant_name());
		restaurantsUpdated.setRestaurant_address(restaurant.getRestaurant_address());
		restaurantsUpdated.setRestaurant_phone(restaurant.getRestaurant_phone());
		Restaurants savedRestaurant = restaurantsRepository.save(restaurantsUpdated);
		
		return savedRestaurant ;
	}
	
	/**
     * Retrieves a restaurant by its ID.
     * 
     * @param restaurantId The ID of the restaurant.
     * @return The restaurant entity.
     * @throws NoSuchRestaurantIDException If the restaurant ID does not exist.
     */
	@Override
	public Restaurants getRestaurantById(int restaurantId) throws NoSuchRestaurantIDException {
		return restaurantsRepository.findById(restaurantId).orElseThrow(()-> 
		new NoSuchRestaurantIDException("Restaurant ID: "+ restaurantId +" not found"));
	}

	/**
     * Deletes a restaurant by its ID.
     * 
     * @param restaurantId The ID of the restaurant to delete.
     * @return True if the restaurant was successfully deleted, false otherwise.
     */
	@Transactional
	@Override
	public boolean deleteRestaurantById(int restaurantId) {

		Optional<Restaurants> optionalRestaurant = restaurantsRepository.findById(restaurantId);
		
		if(optionalRestaurant.isPresent()) {
			menuItemsRepository.deleteAllMenuItemsByResturantId(restaurantId);
			restaurantsRepository.delete(optionalRestaurant.get());
			return true;
		}
		return false;
	}
 
	/**
     * Adds a new restaurant to the repository.
     * 
     * @param restaurant The restaurant entity to add.
     * @return The added restaurant entity.
     * @throws DuplicateRestaurantIDException If the restaurant ID already exists.
     */
	@Override
	public Restaurants addRestaurant(Restaurants restaurant) {
		if(restaurantsRepository.existsById(restaurant.getRestaurant_id())) {
			throw new DuplicateRestaurantIDException("Restaurant ID "+ restaurant.getRestaurant_id() +" already exists");
		}
		return restaurantsRepository.save(restaurant);
	}
}