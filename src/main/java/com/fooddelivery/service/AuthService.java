package com.fooddelivery.service;

import com.fooddelivery.entity.Customers;

public interface AuthService {
	Customers login(String customer_email, String customer_password);
	boolean resetPassword(String customer_email, String customer_password);
}
