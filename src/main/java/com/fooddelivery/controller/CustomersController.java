package com.fooddelivery.controller;
import java.util.List;
import java.util.Set;

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

import com.fooddelivery.Exception.CustomerNotFoundException;
import com.fooddelivery.Exception.DuplicateCustomerIDException;
import com.fooddelivery.Exception.InvalidRestaurantIdException;
import com.fooddelivery.entity.Customers;
import com.fooddelivery.entity.Orders;
import com.fooddelivery.entity.Restaurants;
import com.fooddelivery.service.CustomersService;
import com.fooddelivery.service.RestaurantsService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:4200/")
public class CustomersController {
	@Autowired
	private CustomersService customersService;
	
	@Autowired
	private RestaurantsService restaurantsService;

	/**
     * Endpoint to retrieve all customers.
     *
     * @return A ResponseEntity containing a list of all customers.
     */
	@GetMapping("/")
	public ResponseEntity<List<Customers>> getAllCustomers() {
		List<Customers> customers = customersService.getAllCustomers();
		return new ResponseEntity<List<Customers>>(customers, HttpStatus.OK);
	}

	/**
     * Endpoint to retrieve a customer by their ID.
     *
     * @param customer_id The ID of the customer to retrieve.
     * @return A ResponseEntity containing the customer if found.
     * @throws CustomerNotFoundException If the customer with the specified ID does not exist.
     */
	@GetMapping("/{customer_id}")
	public ResponseEntity<Customers> getCustomerById(@PathVariable("customer_id") int customer_id) {
		Customers customer = customersService.getCustomerById(customer_id);
		
		if(customer == null) {
			throw new CustomerNotFoundException(" with ID: " + customer_id);
		}
		return new ResponseEntity<Customers>(customer, HttpStatus.OK);
	}
	
	/**
     * Endpoint to retrieve a customer by their email address.
     *
     * @param customer_email The email of the customer to retrieve.
     * @return A ResponseEntity containing the customer if found.
     */
	@GetMapping("/email/{customer_email}")
	public ResponseEntity<Customers> getCustomerByEmail(@PathVariable("customer_email") String customer_email) {
		return new ResponseEntity<Customers>(customersService.getCustomerByEmail(customer_email), HttpStatus.OK);
	}

	 /**
     * Endpoint to add a new customer.
     *
     * @param customer The customer to add.
     * @return A ResponseEntity indicating the result of the operation.
     * @throws DuplicateCustomerIDException If a customer with the same ID already exists.
     */
	@PostMapping("/")
	public ResponseEntity<Customers> addCustomer(@RequestBody @Valid Customers customer) {
		try {
			if (customersService.getCustomerById(customer.getCustomer_id()) != null) {
				throw new DuplicateCustomerIDException("Customer with ID " + customer.getCustomer_id() + " already exists");
			}
			return new ResponseEntity<Customers>(customersService.addCustomer(customer), HttpStatus.OK);
		} catch (DuplicateCustomerIDException e) {
			return new ResponseEntity<Customers>(HttpStatus.CONFLICT);
		}
	}

	/**
     * Endpoint to update an existing customer by their ID.
     *
     * @param customer_id The ID of the customer to update.
     * @param updatedCustomer The updated customer details.
     * @return A ResponseEntity containing the updated customer if successful.
     */
	@PutMapping("/{customer_id}")
	public ResponseEntity<Customers> updateCustomerById(@PathVariable("customer_id") int customer_id, @RequestBody @Valid Customers updatedCustomer) {
		Customers customer = customersService.getCustomerById(customer_id);
		
		if (customer == null) {
			return new ResponseEntity<Customers>(HttpStatus.NOT_FOUND);
		}
		
		customer.setCustomer_name(updatedCustomer.getCustomer_name());
		customer.setCustomer_email(updatedCustomer.getCustomer_email());
		customer.setCustomer_phone(updatedCustomer.getCustomer_phone());
		Customers savedCustomer = customersService.updateCustomer(customer);
		
		return new ResponseEntity<Customers>(savedCustomer, HttpStatus.CREATED);
	}

	/**
     * Endpoint to delete a customer by their ID.
     *
     * @param customer_id The ID of the customer to delete.
     * @return A ResponseEntity indicating the result of the operation.
     */
	@DeleteMapping("/{customer_id}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable("customer_id") int customer_id) {
		boolean deleted = customersService.deleteCustomerById(customer_id);
		
		if(!deleted) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Customer deleted successfully", HttpStatus.NO_CONTENT);
	}

    /**
     * Endpoint to retrieve all orders for a specific customer.
     *
     * @param customerId The ID of the customer whose orders are to be retrieved.
     * @return A ResponseEntity containing a list of orders for the specified customer.
     */
	@GetMapping("/{customer_id}/orders")
	public ResponseEntity<List<Orders>> getOrdersByCustomerId(@PathVariable("customer_id") int customerId) {
		return new ResponseEntity<List<Orders>>(customersService.getOrdersByCustomerId(customerId), HttpStatus.OK); 
	}
	
	/**
     * Endpoint to retrieve all favorite restaurants for a specific customer.
     *
     * @param customerId The ID of the customer whose favorite restaurants are to be retrieved.
     * @return A ResponseEntity containing a set of favorite restaurants or a message if none are found.
     */
	@GetMapping("/{customer_id}/favouriteRestaurant")
	public ResponseEntity<?> getAllFavoriteRestaurants(@PathVariable("customer_id") int customerId){
		Customers customer = customersService.getCustomerById(customerId);
		
		if (customer == null) {
			throw new CustomerNotFoundException(" with ID: " + customerId);
		}
		if(customersService.getAllFavoriteRestaurants(customerId).isEmpty()) {
			return new ResponseEntity<String>("no favorite restauranst for "+customerId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Set<Restaurants>>(customersService.getAllFavoriteRestaurants(customerId), HttpStatus.OK);
	}
	
	/**
     * Endpoint to add a restaurant to a customer's list of favorite restaurants.
     *
     * @param customerId The ID of the customer.
     * @param restaurantId The ID of the restaurant to add as a favorite.
     * @return A ResponseEntity indicating the result of the operation.
     * @throws InvalidRestaurantIdException If the restaurant with the specified ID does not exist.
     */
	@Transactional
	@PostMapping("/{customer_id}/favouriteRestaurant/{restaurant_id}")
	public ResponseEntity<String> addFavoriteRestaurant(@PathVariable("customer_id") int customerId, @PathVariable("restaurant_id") int restaurantId)
			throws InvalidRestaurantIdException{
		Customers customer = customersService.getCustomerById(customerId);
		if (customer == null) {
			throw new CustomerNotFoundException(" with ID: " + customerId);
		}
		
		Restaurants restaurant = restaurantsService.getRestaurantById(restaurantId);
		if (restaurant == null) {
			throw new InvalidRestaurantIdException(" with ID: " + restaurantId);
		}
		customersService.addFavoriteRestaurant(customerId, restaurantId);
		
		return new ResponseEntity<String>("restaurant "+restaurantId+" successfully added favorite restaurant for "+customerId, HttpStatus.OK);
	}
	
	/**
	 * Handles the request to remove a restaurant from a customer's list of favorite restaurants.
	 *
	 * @param customerId The ID of the customer from whose favorites the restaurant will be removed.
	 * @param restaurantId The ID of the restaurant to be removed from the customer's favorites.
	 * @return A ResponseEntity with a success message and HTTP status 200 OK if the operation is successful.
	 * @throws CustomerNotFoundException If the customer with the specified ID is not found.
	 * @throws InvalidRestaurantIdException If the restaurant with the specified ID does not exist.
	 *
	 */
	@Transactional
	@DeleteMapping("/{customer_id}/favouriteRestaurant/{restaurant_id}")
	public ResponseEntity<String> deleteFavoriteRestaurant(@PathVariable("customer_id") int customerId,@PathVariable("restaurant_id") int restaurantId)
			throws InvalidRestaurantIdException{
		Customers customer = customersService.getCustomerById(customerId);
		if (customer == null) {
			throw new CustomerNotFoundException(" with ID: " + customerId);
		}
		
		Restaurants restaurant = restaurantsService.getRestaurantById(restaurantId);
		if (restaurant == null) {
			throw new InvalidRestaurantIdException(" with ID: " + restaurantId);
		}
		customersService.deleteFavoriteRestaurant(customerId, restaurantId);
		
		return new ResponseEntity<String>("restaurant "+restaurantId+" successfully removed favorite restaurant for "+customerId, HttpStatus.OK);
	}
}
