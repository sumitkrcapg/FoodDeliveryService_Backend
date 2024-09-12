package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fooddelivery.Exception.CustomerNotFoundException;
import com.fooddelivery.Exception.DuplicateCustomerIDException;
import com.fooddelivery.Repository.CustomersRepository;
import com.fooddelivery.Repository.OrdersRepository;
import com.fooddelivery.Repository.RatingsRepository;
import com.fooddelivery.Repository.RestaurantsRepository;
import com.fooddelivery.entity.Customers;
import com.fooddelivery.entity.Orders;
import com.fooddelivery.entity.Restaurants;

import jakarta.transaction.Transactional;
 
@Service
public class CustomersServiceImpl implements CustomersService {
	@Autowired
	private CustomersRepository customersRepository;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private RestaurantsRepository restaurantsRepository;
	
	@Autowired
	RatingsRepository ratingsRepository;
 
	/**
     * Retrieves all customers from the database.
     * 
     * @return A list of all customers.
     * @throws CustomerNotFoundException if no customers are found.
     */
	@Override
	public List<Customers> getAllCustomers() throws CustomerNotFoundException {
		List<Customers> customers = customersRepository.findAll();
		if(customers.isEmpty()) {
			throw new CustomerNotFoundException("No customers found");
		}
		return customers;
	}
 
	/**
     * Adds a new customer to the database.
     * 
     * @param customer The customer entity to be added.
     * @return The added customer entity.
     * @throws DuplicateCustomerIDException if a customer with the same ID already exists.
     */
	@Override
	public Customers addCustomer(Customers customer) throws DuplicateCustomerIDException {
	    Optional<Customers> existingCustomer = customersRepository.findById(customer.getCustomer_id());
	    
	    if(existingCustomer.isPresent()) {
	    	throw new DuplicateCustomerIDException("Customer with Id " + customer.getCustomer_id() + " already exists");
	    } else {
			return customersRepository.save(customer);
		}
	}
	
	/**
     * Retrieves a customer by their ID.
     * 
     * @param customer_id The ID of the customer to retrieve.
     * @return The customer entity if found, otherwise null.
     */
	@Override
	public Customers getCustomerById(int customer_id) {
		Optional<Customers> optionalCustomer = customersRepository.findById(customer_id);
		return optionalCustomer.orElse(null) ;
	}
 
	/**
     * Updates an existing customer's details.
     * 
     * @param customer The customer entity with updated details.
     * @return The updated customer entity.
     */
	@Override
	public Customers updateCustomer(Customers customer) {
		return customersRepository.save(customer);
	}
 
	/**
     * Deletes a customer by their ID.
     * 
     * @param customer_id The ID of the customer to delete.
     * @return True if the customer was successfully deleted, otherwise false.
     */
	@Transactional
	@Override
	public boolean deleteCustomerById(int customer_id) {
		Optional<Customers> optionalCustomer = customersRepository.findById(customer_id);
		
		if(optionalCustomer.isPresent()) {
			customersRepository.delete(optionalCustomer.get());
			return true;
		}
		return false;
	}
	
	/**
     * Retrieves all orders placed by a customer.
     * 
     * @param customer_id The ID of the customer whose orders are to be retrieved.
     * @return A list of orders placed by the customer.
     */
	@Override
	public List<Orders> getOrdersByCustomerId(int customer_id) {
		return ordersRepository.findOrdersByCustomerId(customer_id);
	}
	
	/**
     * Adds a restaurant to a customer's list of favorite restaurants.
     * 
     * @param customerId The ID of the customer.
     * @param restaurantId The ID of the restaurant to add to favorites.
     */
	@Override
	public void addFavoriteRestaurant(int customerId, int restaurantId) {
		Optional<Customers> customerOpt = customersRepository.findById(customerId);
		Optional<Restaurants> restaurantOpt = restaurantsRepository.findById(restaurantId);
		
		if(customerOpt.isPresent() && restaurantOpt.isPresent()) {
			Customers customer = customerOpt.get();
			Restaurants restaurant = restaurantOpt.get();
			
			customer.getRestaurantsFavorite().add(restaurant);
			customersRepository.save(customer);
		}
	}

	/**
     * Removes a restaurant from a customer's list of favorite restaurants.
     * 
     * @param customerId The ID of the customer.
     * @param restaurantId The ID of the restaurant to remove from favorites.
     */
	@Override
	public void deleteFavoriteRestaurant(int customerId, int restaurantId) {
		Optional<Customers> customerOpt = customersRepository.findById(customerId);
		Optional<Restaurants> restaurantOpt = restaurantsRepository.findById(restaurantId);
		
		if(customerOpt.isPresent() && restaurantOpt.isPresent()) {
			Customers customer = customerOpt.get();
			Restaurants restaurant = restaurantOpt.get();
			
			customer.getRestaurantsFavorite().remove(restaurant);
			customersRepository.save(customer);
		}
	}

	/**
     * Retrieves all favorite restaurants of a customer.
     * 
     * @param customerId The ID of the customer whose favorite restaurants are to be retrieved.
     * @return A set of favorite restaurants.
     */
	@Override
	public Set<Restaurants> getAllFavoriteRestaurants(int customerId) {
		return customersRepository.getAllFavotiteRestaurants(customerId);
	}

	/**
     * Retrieves a customer by their email address.
     * 
     * @param customer_email The email address of the customer.
     * @return The customer entity if found, otherwise null.
     */
	@Override
	public Customers getCustomerByEmail(String customer_email) {
		return customersRepository.getCustomerByEmail(customer_email);
	}
}