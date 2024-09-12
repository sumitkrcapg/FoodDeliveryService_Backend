package com.fooddelivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fooddelivery.Exception.DuplicateRestaurantIDException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.Exception.NoSuchRestaurantIDException;
import com.fooddelivery.entity.Restaurants;
import com.fooddelivery.service.RestaurantsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "http://localhost:4200/")
public class RestaurantsController {
	@Autowired
	private RestaurantsService restaurantsService;
	
	/**
     * Endpoint to retrieve all restaurants.
     * 
     * @return ResponseEntity containing a list of all restaurants and HTTP status 200 OK.
     */
	@GetMapping("/")
	public ResponseEntity<List<Restaurants>> getAllRestaurants() {
		List<Restaurants> restaurants = restaurantsService.getAllRestaurants();
		return new ResponseEntity<List<Restaurants>>(restaurants, HttpStatus.OK);
	}
	
	/**
     * Endpoint to update details of a specific restaurant.
     * 
     * @param restaurant_id The ID of the restaurant to be updated.
     * @param rest The updated restaurant information.
     * @return ResponseEntity with the updated restaurant and HTTP status 202 ACCEPTED, or HTTP status 404 NOT FOUND if the restaurant does not exist.
     * @throws InvalidRestaurantIdException If the restaurant ID is invalid or not found.
     */
	@PutMapping("/{restaurant_id}")
	ResponseEntity<Restaurants> updateRestaurants(@PathVariable("restaurant_id") int restaurant_id, @RequestBody @Valid Restaurants rest) throws InvalidRestaurantIdException {
		System.out.println(restaurant_id);
		Restaurants restaurant = restaurantsService.getRestaurantById(restaurant_id);
		
		if (restaurant == null) {
			return new ResponseEntity<Restaurants>(HttpStatus.NOT_FOUND);
		}
		
		restaurant.setRestaurant_name(rest.getRestaurant_name());
		restaurant.setRestaurant_address(rest.getRestaurant_address());
		restaurant.setRestaurant_phone(rest.getRestaurant_phone());
		Restaurants savedRestaurant = restaurantsService.updateRestaurant(restaurant);
		
		return new ResponseEntity<Restaurants>(savedRestaurant, HttpStatus.ACCEPTED);
	}
	
	/**
     * Endpoint to retrieve a specific restaurant by its ID.
     * 
     * @param restaurantId The ID of the restaurant to be retrieved.
     * @return ResponseEntity with the restaurant information and HTTP status 200 OK, or an error message with HTTP status 404 NOT FOUND if the restaurant does not exist.
     */
	@GetMapping("/{restaurantId}")
	public ResponseEntity<?> getRestaurantsById(@PathVariable("restaurantId") int restaurantId) {
		try {
			Restaurants restaurant = restaurantsService.getRestaurantById(restaurantId);
			return new ResponseEntity<>(restaurant, HttpStatus.OK);
		} catch(NoSuchRestaurantIDException e) {
			return new ResponseEntity<>("Restaurant with ID "+ restaurantId +" not found", HttpStatus.NOT_FOUND);
		}
	}
 
	/**
     * Endpoint to add a new restaurant.
     * 
     * @param restaurant The restaurant information to be added.
     * @return ResponseEntity with the added restaurant and HTTP status 200 OK, or an error message with HTTP status 400 BAD REQUEST if a restaurant with the same ID already exists.
     * @throws DuplicateRestaurantIDException If a restaurant with the same ID already exists.
     */
	@PostMapping("/")
	public ResponseEntity<?> addRestaurant(@RequestBody @Valid Restaurants restaurant) throws DuplicateRestaurantIDException {
			try {
				Restaurants addedRestaurant = restaurantsService.addRestaurant(restaurant);
				return new ResponseEntity<>(addedRestaurant, HttpStatus.OK);
			} catch(DuplicateRestaurantIDException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
	}
	
	/**
     * Endpoint to delete a specific restaurant by its ID.
     * 
     * @param restaurantId The ID of the restaurant to be deleted.
     * @return ResponseEntity with a success message and HTTP status 204 NO CONTENT if the restaurant was successfully deleted, or HTTP status 404 NOT FOUND if the restaurant does not exist.
     */
	@DeleteMapping("/{restaurantId}")
	public ResponseEntity<String> deleteRestaurantsById(@PathVariable("restaurantId") int restaurantId) {

		boolean deleted = restaurantsService.deleteRestaurantById(restaurantId);
		
		if(!deleted) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Restaurant deleted successfully", HttpStatus.NO_CONTENT);
	}
}
