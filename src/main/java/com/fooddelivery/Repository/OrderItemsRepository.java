package com.fooddelivery.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fooddelivery.entity.OrderItems;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
	
	/**
     * Deletes all order items associated with a specific order ID.
     * 
     * @param order_id The ID of the order whose items are to be deleted.
     */
	@Modifying
	@Query("delete from OrderItems oi where oi.orders.order_id=:order_id")
	void deleteOrderItemByOrderId(@Param("order_id") int order_id);
}
