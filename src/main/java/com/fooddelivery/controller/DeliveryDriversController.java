package com.fooddelivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.Exception.InvalidDriverIDException;
import com.fooddelivery.entity.DeliveryDrivers;
import com.fooddelivery.entity.Orders;
import com.fooddelivery.service.DeliveryDriversService;
import com.fooddelivery.service.OrdersService;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = "http://localhost:4200/")
public class DeliveryDriversController {
	@Autowired
	private DeliveryDriversService deliverydriversService;
	
	@Autowired
	private OrdersService ordersService;
	
	/**
     * Endpoint to retrieve a list of all delivery drivers.
     * 
     * @return ResponseEntity containing a list of DeliveryDrivers and HTTP status 200 OK.
     */
	@GetMapping("/")
	public ResponseEntity<List<DeliveryDrivers>> getAllDrivers() {
        List<DeliveryDrivers> drivers = deliverydriversService.getAllDeliveryDrivers();
        return new ResponseEntity<List<DeliveryDrivers>>(drivers, HttpStatus.OK);
    }
	
	/**
     * Endpoint to retrieve a specific delivery driver by their ID.
     * 
     * @param driverId The ID of the delivery driver to retrieve.
     * @return ResponseEntity containing the DeliveryDriver object or an error message if not found.
     */
	@GetMapping("/{driverId}")
	 public ResponseEntity<?> getDriverById(@PathVariable("driverId") int driverId) {
        try {
            DeliveryDrivers driver = deliverydriversService.getDeliveryDriversById(driverId);
            return new ResponseEntity<>(driver, HttpStatus.OK);
        } catch (InvalidDriverIDException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
	/**
     * Endpoint to retrieve a list of orders assigned to a specific delivery driver.
     * 
     * @param driverId The ID of the delivery driver whose orders are to be retrieved.
     * @return ResponseEntity containing a list of Orders associated with the driver and HTTP status 200 OK.
     */
    @GetMapping("/{driverId}/orders")
	public ResponseEntity<List<Orders>> getOrdersByDriverId(@PathVariable("driverId") int driverId) {
		return new ResponseEntity<List<Orders>>(ordersService.getOrdersByDriverId(driverId), HttpStatus.OK);
	}
}

