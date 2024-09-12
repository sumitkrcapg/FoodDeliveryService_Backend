package com.fooddelivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="MENU_ITEMS")
public class MenuItems {
	@Id
	@Column(name="ITEM_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int item_id;
	
	@Column(name="ITEM_NAME")
	@NotEmpty(message="Item name cannot be empty")
    @Size(max=50, message="Item name must be less than or equal to 100 characters")
    private String item_name;
	
	@Column(name="ITEM_DESCRIPTION")
	@NotEmpty(message="Item description cannot be empty")
	@Size(max=255, message="Item description must be less than or equal to 255 characters")
    private String item_description;
	
	@Column(name="ITEM_PRICE")
	@DecimalMin(value="0.0", inclusive=false, message="Item price must be greater than zero")
    private float item_price;
    
    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Restaurants restaurants;
    
	public MenuItems(int item_id, String item_name, String item_description, float item_price) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.item_description = item_description;
		this.item_price = item_price;
	}
	
	public MenuItems() {}
    
	public Restaurants getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}

	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_description() {
		return item_description;
	}
	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}
	public float getItem_price() {
		return item_price;
	}
	public void setItem_price(float item_price) {
		this.item_price = item_price;
	}
	@Override
	public String toString() {
		return "MenuItems [item_id=" + item_id + ", item_name=" + item_name + ", item_description=" + item_description
				+ ", item_price=" + item_price +  "]";
	}
}
