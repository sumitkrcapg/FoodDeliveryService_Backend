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

import com.fooddelivery.Exception.CouponNotFoundException;
import com.fooddelivery.entity.Coupons;
import com.fooddelivery.service.CouponsService;


@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = "http://localhost:4200/")
public class CouponsController {
	
	@Autowired
	private CouponsService couponsService;
	
	/**
     * Endpoint to retrieve all available coupons.
     *
     * @return A ResponseEntity containing a list of all coupons.
     * @throws CouponNotFoundException If no coupons are found.
     */
	@GetMapping("/")
	public ResponseEntity<List<Coupons>> getAllCoupons() throws CouponNotFoundException {
		List<Coupons> coupons = couponsService.getAllCoupons();
		return new ResponseEntity<List<Coupons>>(coupons, HttpStatus.OK);
	}
	
	/**
     * Endpoint to retrieve a specific coupon by its code.
     *
     * @param coupon_code The code of the coupon to retrieve.
     * @return A ResponseEntity containing the coupon if found.
     */
	@GetMapping("/{coupon_code}")
	public ResponseEntity<Coupons> getCouponByCouponCode(@PathVariable("coupon_code") String coupon_code) {
		return new ResponseEntity<Coupons>(couponsService.getCouponByCouponCode(coupon_code), HttpStatus.OK);
	}
}