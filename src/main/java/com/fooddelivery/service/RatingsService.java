package com.fooddelivery.service;

import java.util.List;

import com.fooddelivery.Exception.CustomerNotFoundException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.entity.Ratings;


public interface RatingsService {
	List<Ratings> getAllRatingsByRestaurantId(int restaurant_id) throws InvalidRestaurantIdException;
	List<Ratings> getAllRatingsByCustomerId(int customer_id) throws CustomerNotFoundException;
}