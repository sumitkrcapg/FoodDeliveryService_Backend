package com.fooddelivery.controller;

import java.util.Date;
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

import com.fooddelivery.Exception.DuplicateOrderIdException;
import com.fooddelivery.Exception.InvalidOrderIdException;
import com.fooddelivery.Exception.OrdersNotFoundException;
import com.fooddelivery.entity.Customers;
import com.fooddelivery.entity.DeliveryDrivers;
import com.fooddelivery.entity.Orders;
import com.fooddelivery.entity.Restaurants;
import com.fooddelivery.service.CustomersService;
import com.fooddelivery.service.DeliveryDriversService;
import com.fooddelivery.service.OrdersService;
import com.fooddelivery.service.RestaurantsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200/")
public class OrdersController {
	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private DeliveryDriversService deliveryDriversService;
	
	@Autowired
	private RestaurantsService restaurantsService;
	
	@Autowired
	private CustomersService customersService;
	
	/**
	 * Endpoint to retrieve all orders for a specific customer.
	 * 
	 * @param customer_id The ID of the customer whose orders are to be retrieved.
	 * @return List of Orders associated with the specified customer.
	 */
	@GetMapping("/{customer_id}/orders")
	public List<Orders> getOrdersByCustomerId(@PathVariable("customer_id") int customer_id){
		return ordersService.getOrderByCustomerId(customer_id);
	}
	
	/**
	 * Endpoint to place a new order for a customer from a restaurant, assigned to a driver.
	 * 
	 * @param customerId The ID of the customer placing the order.
	 * @param restaurantId The ID of the restaurant from which the order is placed.
	 * @param driverId The ID of the delivery driver assigned to the order.
	 * @return ResponseEntity containing the placed Order object and HTTP status 201 CREATED.
	 * @throws DuplicateOrderIdException If an order with the same ID already exists.
	 */
	@PostMapping("/{customerId}/{restaurantId}/{driverId}")
	public ResponseEntity<Orders> placeOrder(@PathVariable("customerId") int customerId, @PathVariable("restaurantId") int restaurantId, @PathVariable("driverId") int driverId)
			throws DuplicateOrderIdException{
		try {
			Customers customer = customersService.getCustomerById(customerId);
			Restaurants restaurants = restaurantsService.getRestaurantById(restaurantId);
			DeliveryDrivers driver = deliveryDriversService.getDeliveryDriversById(driverId);
			
			Orders orders = new Orders(new Date(), "Pending");
			orders.setCustomers(customer);
			orders.setRestaurants(restaurants);
			orders.setDeliveryDrivers(driver);
			
			Orders savedOrder = ordersService.placeOrder(orders);

			System.out.println("Order placed successfully");
			return new ResponseEntity<Orders>(savedOrder, HttpStatus.CREATED);
		}
		catch(Exception e) {
			throw new DuplicateOrderIdException("Order can not be placed");
		}
	}
	
	/**
	 * Endpoint to retrieve a specific order by its ID.
	 * 
	 * @param orderId The ID of the order to retrieve.
	 * @return ResponseEntity containing the Order object and HTTP status 200 OK.
	 * @throws OrdersNotFoundException If the order is not found.
	 * @throws InvalidOrderIdException If the order ID is invalid.
	 */
	@GetMapping("/{orderId}")
	public ResponseEntity<Orders> getOrder(@PathVariable("orderId") int orderId) throws OrdersNotFoundException, InvalidOrderIdException {
		Orders order = ordersService.getOrders(orderId);
		
		if(order == null ) {
			throw new OrdersNotFoundException("Orders not found with Id : "+orderId);
		}
		if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid");
		}
		return new ResponseEntity<Orders>(order, HttpStatus.OK);
	}
	
	/**
	 * Endpoint to retrieve all orders in the system.
	 * 
	 * @return ResponseEntity containing a list of all Orders and HTTP status 200 OK.
	 */
	@GetMapping("/")
	public ResponseEntity<List<Orders>> getAllOrders() {
		List<Orders> orders = ordersService.getAllOrders();
		System.out.println("Order details fetched successfully");
		return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
	}
	
	/**
	 * Endpoint to update the status of a specific order.
	 * 
	 * @param orderId The ID of the order whose status is to be updated.
	 * @param newStatus The new status to set for the order.
	 * @return ResponseEntity containing the updated status of the order and HTTP status 202 ACCEPTED.
	 * @throws InvalidOrderIdException If the order ID is invalid.
	 * @throws OrdersNotFoundException If the order is not found.
	 */
	@PutMapping("/{orderId}/status")
	public ResponseEntity<String> updateOrderStatus(@PathVariable int orderId, @RequestBody @Valid String newStatus) throws InvalidOrderIdException, OrdersNotFoundException {
		if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid to update");
		}
		
		Orders order = ordersService.getOrders(orderId);
		
		if(order == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		order.setOrder_status(newStatus);
		ordersService.updateOrderStatus(orderId, newStatus);
		
		return new ResponseEntity<String>(order.getOrder_status(), HttpStatus.ACCEPTED);
	}
	
	/**
	 * Endpoint to cancel a specific order.
	 * 
	 * @param orderId The ID of the order to cancel.
	 * @return ResponseEntity with a message indicating the cancellation status and HTTP status 204 NO CONTENT if successful, or 404 NOT FOUND if the order does not exist.
	 * @throws InvalidOrderIdException If the order ID is invalid or not present.
	 */
	@DeleteMapping("/{orderId}")
	public ResponseEntity<String> cancelOrder(@PathVariable("orderId") int orderId) throws InvalidOrderIdException{
		if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id with "+orderId+" is not present in the database");
		}
		
		boolean deleted = ordersService.cancelOrder(orderId);
		
		if(deleted) {
			return new ResponseEntity<String>("Order canceled successfully", HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<String>("order not found", HttpStatus.NOT_FOUND);
		}
	}
 
	/**
	 * Endpoint to assign a delivery driver to a specific order.
	 * 
	 * @param orderId The ID of the order to which the driver will be assigned.
	 * @param driverId The ID of the delivery driver to assign.
	 * @return ResponseEntity containing the updated status of the order and HTTP status 202 ACCEPTED.
	 * @throws InvalidOrderIdException If the order ID is invalid.
	 * @throws OrdersNotFoundException If the order is not found.
	 */
	@PutMapping("/{orderId}/assignDriver/{driverId}")
	public ResponseEntity<String> UpdateDeliveryDriver(@PathVariable("orderId") int orderId, @PathVariable("driverId") int driverId) throws InvalidOrderIdException, OrdersNotFoundException {		
		if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid to update");
		}
		
		Orders orders = ordersService.getOrders(orderId);
		
		if(orders == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		
		DeliveryDrivers driver = deliveryDriversService.getDeliveryDriversById(driverId);
		orders.setDeliveryDrivers(driver);
		ordersService.UpdateDeliveryDriver(orderId, driverId);
		return new ResponseEntity<String>(orders.getOrder_status(), HttpStatus.ACCEPTED);
	}
}


