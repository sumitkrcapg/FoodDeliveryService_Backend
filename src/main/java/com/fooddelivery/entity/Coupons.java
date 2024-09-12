package com.fooddelivery.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="COUPONS")
public class Coupons {
	@Id
	@Column(name="COUPON_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int coupon_id;
	
	@Column(name="COUPON_CODE")
	@NotEmpty(message="Coupon code cannot be empty")
    @Size(max=20, message="Coupon code must be less than or equal to 20 characters")
	private String coupon_code;
	
	@Column(name="DISCOUNT_AMOUNT")
	@NotEmpty(message="Discount amount cannot be empty")
    @Positive(message="Discount amount must be positive")
	private double discount_amount;
	
	@Column(name="EXPIRY_DATE")
	@NotEmpty(message="Expiry date cannot be empty")
	private Date expiry_date;
	
	public Coupons() {}

	public Coupons(int coupon_id, String coupon_code, double discount_amount, Date expiry_date) {
		super();
		this.coupon_id = coupon_id;
		this.coupon_code = coupon_code;
		this.discount_amount = discount_amount;
		this.expiry_date = expiry_date;
	}

	public int getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(int coupon_id) {
		this.coupon_id = coupon_id;
	}

	public String getCoupon_code() {
		return coupon_code;
	}

	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}

	public double getDiscount_amount() {
		return discount_amount;
	}

	public void setDiscount_amount(double discount_amount) {
		this.discount_amount = discount_amount;
	}

	public Date getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}

	@Override
	public String toString() {
		return "Coupons [coupon_id=" + coupon_id + ", coupon_code=" + coupon_code + ", discount_amount="
				+ discount_amount + ", expiry_date=" + expiry_date + "]";
	}
}
