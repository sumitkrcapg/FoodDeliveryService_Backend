package com.fooddelivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="ORDER_ITEMS")
public class OrderItems {
    @Id
    @Column(name="ORDER_ITEM_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int order_item_id;
    
    @Column(name="QUANTITY")
    @NotNull(message="Quantity cannot be null")
    @Min(value=1, message="Quantity must be at least 1")
    private int quantity;
    
    @ManyToOne
    @JoinColumn(name="order_id")
    private Orders orders;
    
    @ManyToOne
    @JoinColumn(name="item_id")
    private MenuItems menuitems;
    
	public OrderItems(int order_item_id, int quantity) {
		super();
		this.order_item_id = order_item_id;
		this.quantity = quantity;
	}
	
	public OrderItems() {}
	
	public int getOrder_item_id() {
		return order_item_id;
	}
	public void setOrder_item_id(int order_item_id) {
		this.order_item_id = order_item_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "OrderItems [order_item_id=" + order_item_id + ", quantity=" + quantity + "]";
	}
}
