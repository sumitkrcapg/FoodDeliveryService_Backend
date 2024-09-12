package com.fooddelivery.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.entity.Ratings;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings,Integer> {
	/**
     * Retrieves all ratings for a specific restaurant.
     * 
     * @param restaurant_id The ID of the restaurant for which ratings are to be retrieved.
     * @return A list of ratings for the specified restaurant.
     */
	@Query("SELECT r FROM Ratings r WHERE r.restaurants.restaurant_id=:restaurant_id")
	List<Ratings> findByRestaurantId(@Param("restaurant_id")int restaurant_id);
	
	/**
     * Retrieves all ratings made by a specific customer.
     * 
     * @param customer_id The ID of the customer whose ratings are to be retrieved.
     * @return A list of ratings made by the specified customer.
     */
	@Query("SELECT r FROM Ratings r WHERE r.orders.customers.customer_id=:customer_id")
	List<Ratings> findByCustomerId(@Param("customer_id")int customer_id);
	
	/**
     * Deletes all ratings associated with a specific customer.
     * 
     * @param customer_id The ID of the customer whose ratings are to be deleted.
     */
	@Modifying
	@Query("delete from Ratings r where r.orders.customers.customer_id=:customer_id")
	void deleteAllRatingsByCustomerId(@Param("customer_id") int customer_id);
}