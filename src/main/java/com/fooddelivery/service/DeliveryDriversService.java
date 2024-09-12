package com.fooddelivery.service;

import java.util.List;

import com.fooddelivery.entity.DeliveryDrivers;

public interface DeliveryDriversService {
	public List<DeliveryDrivers> getAllDeliveryDrivers();
	public DeliveryDrivers getDeliveryDriversById(int driverId);
}
