package com.fooddelivery.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fooddelivery.entity.Coupons;

@Repository
public interface CouponsRepository extends JpaRepository<Coupons, Integer>{

	/**
     * Finds a coupon by its coupon code.
     * 
     * @param coupon_code The code of the coupon to find.
     * @return The coupon with the given code, or null if no coupon is found.
     */
	@Query("select c from Coupons c where c.coupon_code=:coupon_code")
	Coupons getCouponByCouponCode(@Param("coupon_code") String coupon_code);
}
