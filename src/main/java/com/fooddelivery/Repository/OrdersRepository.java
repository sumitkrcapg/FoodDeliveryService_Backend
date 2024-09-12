package com.fooddelivery.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer>{
	
	/**
     * Retrieves all orders associated with a given customer.
     * 
     * @param customer_id The ID of the customer.
     * @return A list of orders for the specified customer.
     */
	@Query("SELECT o FROM Orders o Where o.customers.customer_id=:customer_id")
	List<Orders> findOrdersByCustomerId(@Param("customer_id")int customer_id);
	
	/**
     * Retrieves all orders assigned to a specific delivery driver.
     * 
     * @param driverId The ID of the delivery driver.
     * @return A list of orders for the specified delivery driver.
     */
	@Query("SELECT o FROM Orders o Where o.deliveryDrivers.driver_id=:driverId")
	List<Orders> findOrdersByDriverId(@Param("driverId")int driverId);
	
	/**
     * Deletes all orders associated with a specific customer.
     * 
     * @param customer_id The ID of the customer whose orders are to be deleted.
     */
	@Modifying
	@Query("delete from Orders o where o.customers.customer_id=:customer_id")
	void deleteAllOrdersByCustomerId(@Param("customer_id") int customer_id);
	
	/**
     * Retrieves all orders, sorted by order ID in descending order.
     * 
     * @return A list of all orders, sorted by order ID.
     */
	@Query("SELECT o FROM Orders o ORDER BY o.order_id DESC")
    List<Orders> findAllOrdersSortedByOrderId();
	
	/**
     * Retrieves all orders for a specific customer, sorted by order ID in descending order.
     * 
     * @param customer_id The ID of the customer whose orders are to be retrieved.
     * @return A list of orders for the specified customer, sorted by order ID.
     */
	@Query("SELECT o FROM Orders o where o.customers.customer_id=:customer_id ORDER BY o.order_id DESC")
    List<Orders> findAllOrdersByCustomerIdSortedByOrderId(@Param("customer_id") int customer_id);
}
