package com.fooddelivery.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fooddelivery.entity.Restaurants;

@Repository
public interface RestaurantsRepository extends JpaRepository<Restaurants,Integer> {
}