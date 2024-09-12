package com.fooddelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.entity.Customers;
import com.fooddelivery.service.AuthService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200/")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	/**
     * Endpoint for customer login.
     *
     * @param customer_email    The email of the customer trying to log in.
     * @param customer_password The password of the customer trying to log in.
     * @return A ResponseEntity containing the customer details if login is successful.
     */
	@GetMapping("/login/{customer_email}/{customer_password}")
	private ResponseEntity<Customers> loginCustomer(@PathVariable("customer_email") String customer_email, @PathVariable("customer_password") String customer_password) {
		return new ResponseEntity<Customers>(authService.login(customer_email, customer_password), HttpStatus.OK);
	}
	
	/**
     * Endpoint for resetting a customer's password.
     *
     * @param customer_email    The email of the customer whose password is to be reset.
     * @param customer_password The new password for the customer.
     * @return A ResponseEntity indicating whether the password reset was successful.
     */
	@PutMapping("/resetpassword/{customer_email}/{customer_password}")
	public ResponseEntity<Boolean> resetPassword(@PathVariable("customer_email") String customer_email, @PathVariable("customer_password") String customer_password) {
		return new ResponseEntity<Boolean>(authService.resetPassword(customer_email, customer_password), HttpStatus.CREATED);
	}
}
