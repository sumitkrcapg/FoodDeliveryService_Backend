package com.fooddelivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Repository.AuthRepository;
import com.fooddelivery.Repository.CustomersRepository;
import com.fooddelivery.entity.Customers;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private CustomersRepository customersRepository;
	
	/**
     * Logs in a customer using their email and password.
     * 
     * @param customer_email The email of the customer attempting to log in.
     * @param customer_password The password of the customer attempting to log in.
     * @return The Customers entity if login is successful; null if the credentials are invalid.
     */
	@Override
	public Customers login(String customer_email, String customer_password) {
		return authRepository.getCustomerByEmailAndPassword(customer_email, customer_password);
	}

	/**
     * Resets the password for a customer.
     * 
     * @param customer_email The email of the customer whose password is being reset.
     * @param customer_password The new password for the customer.
     * @return True if the password reset is successful; False if the email does not exist.
     */
	@Override
	public boolean resetPassword(String customer_email, String customer_password) {
		Customers c = customersRepository.getCustomerByEmail(customer_email);
		
		if(c == null) {
			return false;
		}
		authRepository.resetPassword(customer_email, customer_password);
		return true;
	}

}
