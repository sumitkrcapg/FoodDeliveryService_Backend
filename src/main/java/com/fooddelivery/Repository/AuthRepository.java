package com.fooddelivery.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.entity.Customers;

import jakarta.transaction.Transactional;

@Repository
public interface AuthRepository extends JpaRepository<Customers, Integer> {
	
	/**
     * Finds a customer by their email and password.
     * 
     * @param customer_email The email of the customer.
     * @param customer_password The password of the customer.
     * @return The customer with the given email and password.
     */
	@Query("select c from Customers c where c.customer_email=:customer_email AND c.customer_password=:customer_password")
	Customers getCustomerByEmailAndPassword(@Param("customer_email") String customer_email, @Param("customer_password") String customer_password);
	
	/**
     * Updates the password of a customer identified by their email.
     * 
     * @param customer_email The email of the customer.
     * @param customer_password The new password of the customer.
     */
	@Modifying
	@Transactional
	@Query("UPDATE Customers SET customer_password=:customer_password where customer_email=:customer_email")
	void resetPassword(@Param("customer_email") String customer_email, @Param("customer_password") String customer_password);
}
