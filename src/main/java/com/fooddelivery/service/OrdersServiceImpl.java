package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.DuplicateOrderIdException;
import com.fooddelivery.Exception.InvalidOrderIdException;
import com.fooddelivery.Exception.OrdersNotFoundException;
import com.fooddelivery.Repository.DeliveryDriversRepository;
import com.fooddelivery.Repository.OrderItemsRepository;
import com.fooddelivery.Repository.OrdersRepository;
import com.fooddelivery.entity.DeliveryDrivers;
import com.fooddelivery.entity.Orders;

import jakarta.transaction.Transactional;

 
@Service
public class OrdersServiceImpl implements OrdersService{
 
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private DeliveryDriversRepository deliveryDriversRepository;
	
	@Autowired
	private OrderItemsRepository orderItemsRepository;
	
	/**
     * Retrieves all orders for a specific customer, sorted by order ID.
     * 
     * @param customer_id The ID of the customer.
     * @return List of orders for the customer.
     */
	@Override
	public List<Orders> getOrderByCustomerId(int customer_id) {
		return ordersRepository.findAllOrdersByCustomerIdSortedByOrderId(customer_id);
	}
	
	/**
     * Retrieves all orders assigned to a specific delivery driver.
     * 
     * @param driverId The ID of the delivery driver.
     * @return List of orders for the driver.
     */
	@Override
	public List<Orders> getOrdersByDriverId(int driverId) {
		return ordersRepository.findOrdersByDriverId(driverId);
	}
	
	/**
     * Places a new order. Throws an exception if an order with the same ID already exists.
     * 
     * @param orders The order to place.
     * @return The placed order.
     * @throws DuplicateOrderIdException If an order with the same ID already exists.
     */
	@Override
	@Transactional
	public Orders placeOrder(Orders orders) throws DuplicateOrderIdException{  
		Optional<Orders> existingOrder = ordersRepository.findById(orders.getOrder_id());
		
		if(existingOrder.isPresent()) {
			throw new DuplicateOrderIdException("Order with Id "+orders.getOrder_id()+" already exist");
		}
		else {
			return ordersRepository.save(orders);
		}
	}
 
	/**
     * Retrieves an order by its ID. Throws exceptions if the order is not found or if the ID is invalid.
     * 
     * @param orderId The ID of the order to retrieve.
     * @return The order with the specified ID.
     * @throws OrdersNotFoundException If the order with the given ID does not exist.
     * @throws InvalidOrderIdException If the provided ID is invalid.
     */
	@Override
	@Transactional
	public Orders getOrders(int orderId) throws OrdersNotFoundException ,InvalidOrderIdException{
		Optional<Orders> orders = ordersRepository.findById(orderId);
		
		if(orders.isEmpty()) {
			throw new OrdersNotFoundException("Orders not found with Id: "+orderId);
		}
		if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid ");
		}
		return orders.orElse(null);
	}
 
	/**
     * Retrieves all orders, sorted by order ID.
     * 
     * @return List of all orders.
     */
	@Override
	@Transactional
	public List<Orders> getAllOrders() {
		return ordersRepository.findAllOrdersSortedByOrderId();
	}
 
	/**
     * Updates the status of an existing order. Throws exceptions if the order is not found or if the ID is invalid.
     * 
     * @param orderId The ID of the order to update.
     * @param newStatus The new status to set.
     * @return The updated order.
     * @throws InvalidOrderIdException If the provided ID is invalid.
     * @throws OrdersNotFoundException If the order with the given ID does not exist.
     */
	@Override
	@Transactional
	public Orders updateOrderStatus(int orderId, String newStatus) throws InvalidOrderIdException, OrdersNotFoundException{
		Optional<Orders> OpOrder = ordersRepository.findById(orderId);
		
		if(OpOrder.isPresent()) {
			Orders order = OpOrder.get();
			order.setOrder_status(newStatus);
			ordersRepository.save(order);
			return ordersRepository.save(order);
		}
		else if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid to update");
		}
		else {
			throw new OrdersNotFoundException("Order not found with id: "+orderId);
		}
 
	}
 
	/**
     * Cancels an order and removes associated items. Throws an exception if the order is not found or if the ID is invalid.
     * 
     * @param orderId The ID of the order to cancel.
     * @return True if the order was successfully canceled; otherwise, false.
     * @throws InvalidOrderIdException If the provided ID is invalid or the order is not present.
     */
	@Override
	@Transactional
	public boolean cancelOrder(int orderId) throws InvalidOrderIdException {
		Optional<Orders> optionalOrder = ordersRepository.findById(orderId);
		
		if(optionalOrder.isPresent()) {
			orderItemsRepository.deleteOrderItemByOrderId(orderId);
			ordersRepository.deleteById(orderId);
			return true;
		}
		if(!optionalOrder.isPresent() || orderId <= 0) {
			throw new InvalidOrderIdException("Order Id with "+orderId+" is not present");
		}
		return false;
	}

	/**
     * Updates the delivery driver assigned to an order. Throws exceptions if the order or driver is not found or if the ID is invalid.
     * 
     * @param orderId The ID of the order to update.
     * @param driverId The ID of the delivery driver to assign.
     * @return The updated order.
     * @throws InvalidOrderIdException If the provided order ID is invalid.
     * @throws OrdersNotFoundException If the order with the given ID does not exist.
     */
	@Override
	@Transactional
	public Orders UpdateDeliveryDriver(int orderId, int driverId) throws InvalidOrderIdException, OrdersNotFoundException {
		Optional<Orders> OpOrders = ordersRepository.findById(orderId);
		
		if(OpOrders.isPresent()) {
			Orders order = OpOrders.get();
			DeliveryDrivers driver = deliveryDriversRepository.findById(driverId).get();
			order.setDeliveryDrivers(driver);
			ordersRepository.save(order);
			return ordersRepository.save(order);
		}
		else if(orderId <= 0) {
			throw new InvalidOrderIdException("Order Id "+orderId+" is Invalid to update");
		}
		else {
			throw new OrdersNotFoundException("Order not found with id: "+orderId);
		}
	}
}