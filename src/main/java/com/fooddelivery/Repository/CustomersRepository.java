package com.fooddelivery.Repository;


import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.entity.Customers;
import com.fooddelivery.entity.Restaurants;
 
@Repository
public interface CustomersRepository extends JpaRepository<Customers,Integer>{
	
	/**
     * Finds all favorite restaurants of a customer by their ID.
     * 
     * @param customerId The ID of the customer whose favorite restaurants are to be found.
     * @return A set of favorite restaurants for the specified customer.
     */
	@Query("select c.restaurantsFavorite from Customers c where c.customer_id=:customerId")
	Set<Restaurants> getAllFavotiteRestaurants(@Param("customerId") int customerId);
	
	/**
     * Finds a customer by their email.
     * 
     * @param customer_email The email of the customer to be found.
     * @return The customer with the specified email, or null if no customer is found.
     */
	@Query("select c from Customers c where c.customer_email=:customer_email")
	Customers getCustomerByEmail(@Param("customer_email") String customer_email);
}
