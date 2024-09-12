package com.fooddelivery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.InvalidDriverIDException;
import com.fooddelivery.Exception.NoSuchDriverIDException;
import com.fooddelivery.Repository.DeliveryDriversRepository;
import com.fooddelivery.entity.DeliveryDrivers;

@Service
public class DeliveryDriversServiceImpl implements DeliveryDriversService {
	@Autowired
	private DeliveryDriversRepository deliverydriversRepository;
	
	/**
     * Retrieves all delivery drivers from the database.
     * 
     * @return A list of all delivery drivers.
     * @throws NoSuchDriverIDException if no drivers are found in the database.
     */
	@Override
	public List<DeliveryDrivers> getAllDeliveryDrivers() throws NoSuchDriverIDException {
        List<DeliveryDrivers> drivers = deliverydriversRepository.findAll();
        
        if (drivers.isEmpty()) {
            throw new NoSuchDriverIDException("No drivers found.");
        }
        return drivers;
    }
 
	/**
     * Retrieves a delivery driver by their ID.
     * 
     * @param driverId The ID of the driver to retrieve.
     * @return The delivery driver entity if found.
     * @throws InvalidDriverIDException if the driver ID is invalid or the driver is not found.
     */
	@Override
	public DeliveryDrivers getDeliveryDriversById(int driverId) throws InvalidDriverIDException {
        String driverIdString = String.valueOf(driverId);
        
        try {
            Optional<DeliveryDrivers> driverOptional = deliverydriversRepository.findById(driverId);
            
            if(driverOptional.isPresent()) {
                return driverOptional.get();
            } else {
                throw new InvalidDriverIDException("Driver with ID " + driverId + " not found.");
            }
        } catch(NumberFormatException e) {
        	throw new InvalidDriverIDException("Invalid driver ID format: " + driverIdString);
        }
	}
}
