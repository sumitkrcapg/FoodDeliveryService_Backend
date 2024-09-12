package com.fooddelivery.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="ORDERS")
public class Orders {
	@Id
	@Column(name="ORDER_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int order_id;
	
	@Column(name="ORDER_DATE")
	@NotNull(message="Order date cannot be null")
    private Date order_date;
	
	@Column(name="ORDER_STATUS")
	@NotEmpty(message="Order status cannot be empty")
    @Size(max=20, message="Order status must be less than or equal to 20 characters")
    private String order_status;
    
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customers customers;
    
    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Restaurants restaurants;
    
    @ManyToOne
    @JoinColumn(name="driver_id")
    private DeliveryDrivers deliveryDrivers;
    
    public Orders(int order_id, Date order_date, String order_status) {
		super();
		this.order_id = order_id;
		this.order_date = order_date;
		this.order_status = order_status;
	}
    
	public Orders(Date order_date, String order_status) {
		super();
		this.order_date = order_date;
		this.order_status = order_status;
	}
	
	public Orders() {}
	
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	
	public Customers getCustomers() {
		return customers;
	}
	public void setCustomers(Customers customers) {
		this.customers = customers;
	}
	public Restaurants getRestaurants() {
		return restaurants;
	}
	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}
	public DeliveryDrivers getDeliveryDrivers() {
		return deliveryDrivers;
	}
	public void setDeliveryDrivers(DeliveryDrivers deliveryDrivers) {
		this.deliveryDrivers = deliveryDrivers;
	}
	@Override
	public String toString() {
		return "Orders [order_id=" + order_id + ", order_date=" + order_date + ", order_status="
				+ order_status + "]";
	}
}
