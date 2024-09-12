package com.fooddelivery.service;

import java.util.List;

import com.fooddelivery.Exception.DuplicateOrderIdException;
import com.fooddelivery.Exception.InvalidOrderIdException;
import com.fooddelivery.Exception.OrdersNotFoundException;
import com.fooddelivery.entity.Orders;
	 
public interface OrdersService {
	List<Orders> getOrderByCustomerId(int customer_id);
	List<Orders> getOrdersByDriverId(int driverId);
	public Orders placeOrder(Orders orders)throws DuplicateOrderIdException;
	public Orders getOrders(int orderId) throws OrdersNotFoundException, InvalidOrderIdException;
	public List<Orders> getAllOrders();
	public Orders updateOrderStatus(int orderId,String newStatus) throws InvalidOrderIdException, OrdersNotFoundException;
	public boolean cancelOrder(int orderId) throws InvalidOrderIdException;
	public Orders UpdateDeliveryDriver(int orderId,int drievrId) throws InvalidOrderIdException, OrdersNotFoundException;
}
