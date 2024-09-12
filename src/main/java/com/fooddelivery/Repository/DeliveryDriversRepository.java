package com.fooddelivery.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.entity.DeliveryDrivers;

@Repository
public interface DeliveryDriversRepository extends JpaRepository<DeliveryDrivers,Integer> { 

}
 