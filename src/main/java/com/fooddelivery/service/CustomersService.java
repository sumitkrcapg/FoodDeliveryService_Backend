package com.fooddelivery.service;

import java.util.List;
import java.util.Set;

import com.fooddelivery.Exception.DuplicateCustomerIDException;
import com.fooddelivery.entity.Customers;
import com.fooddelivery.entity.Orders;
import com.fooddelivery.entity.Restaurants;


public interface CustomersService {
  List<Customers> getAllCustomers();
  Customers addCustomer(Customers customer) throws DuplicateCustomerIDException;
  Customers getCustomerById(int customer_id);
  Customers getCustomerByEmail(String customer_email);
  Customers updateCustomer(Customers customer);
  boolean deleteCustomerById(int customer_id);
  List<Orders> getOrdersByCustomerId(int customer_id);
  void addFavoriteRestaurant(int customerId, int restaurantId);
  void deleteFavoriteRestaurant(int customerId, int restaurantId);
  Set<Restaurants> getAllFavoriteRestaurants(int customerId);
}