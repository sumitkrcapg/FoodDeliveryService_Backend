package com.fooddelivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.Exception.CouponNotFoundException;
import com.fooddelivery.Repository.CouponsRepository;
import com.fooddelivery.entity.Coupons;

@Service
public class CouponsServiceImpl implements CouponsService{

	@Autowired
	private CouponsRepository couponsRepository;
	
	/**
     * Retrieves all coupons from the database.
     * 
     * @return A list of all available coupons.
     * @throws CouponNotFoundException if no coupons are found in the database.
     */
	@Override
	public List<Coupons> getAllCoupons() throws CouponNotFoundException {
		List<Coupons> coupons=couponsRepository.findAll();
		if(coupons.isEmpty()) {
			throw new CouponNotFoundException("No coupons found");
		}
		return coupons;
	}

	/**
     * Retrieves a coupon by its coupon code.
     * 
     * @param coupon_code The code of the coupon to retrieve.
     * @return The coupon entity if found, or null if the coupon code does not exist.
     */
	@Override
	public Coupons getCouponByCouponCode(String coupon_code) {
		return couponsRepository.getCouponByCouponCode(coupon_code);
	}
}
