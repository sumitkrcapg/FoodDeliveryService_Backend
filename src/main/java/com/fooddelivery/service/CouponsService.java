package com.fooddelivery.service;

import java.util.List;

import com.fooddelivery.Exception.CouponNotFoundException;
import com.fooddelivery.entity.Coupons;

public interface CouponsService {
	List<Coupons> getAllCoupons() throws CouponNotFoundException;
	Coupons getCouponByCouponCode(String coupon_code);
}
