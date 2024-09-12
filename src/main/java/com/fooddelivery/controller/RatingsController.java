package com.fooddelivery.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.entity.Ratings;
import com.fooddelivery.service.RatingsService;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "http://localhost:4200/")
public class RatingsController {
	
	@Autowired
    private RatingsService ratingsService;
    
	/**
     * Endpoint to retrieve all ratings for a specific restaurant.
     * 
     * @param restaurant_id The ID of the restaurant whose ratings are to be retrieved.
     * @return ResponseEntity containing a list of Ratings for the specified restaurant and HTTP status 200 OK.
     * @throws InvalidRestaurantIdException If no ratings are found for the given restaurant ID.
     */
    @GetMapping("/restaurant/{restaurant_id}")
    public ResponseEntity<List<Ratings>> getAllRatingsByRestaurantId(@PathVariable("restaurant_id") int restaurant_id) throws InvalidRestaurantIdException{
    	
    	List<Ratings> ratings = ratingsService.getAllRatingsByRestaurantId(restaurant_id);
    	
    	if(ratings.isEmpty()) {
    		throw new InvalidRestaurantIdException("Invalid restaurant ID");
    	}
    	return new ResponseEntity<List<Ratings>>(ratings, HttpStatus.OK);

    }
    
    /**
     * Endpoint to retrieve all ratings given by a specific customer.
     * 
     * @param customer_id The ID of the customer whose ratings are to be retrieved.
     * @return ResponseEntity containing a list of Ratings given by the specified customer and HTTP status 200 OK.
     */
    @GetMapping("customer/{customer_id}")
	public ResponseEntity<List<Ratings>> getAllRatingsByCustomerId(@PathVariable("customer_id") int customerId) {
		return new ResponseEntity<List<Ratings>>(ratingsService.getAllRatingsByCustomerId(customerId), HttpStatus.OK); 
	}
}
