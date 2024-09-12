package com.fooddelivery.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="RESTAURANTS")
public class Restaurants {
	@Id
	@Column(name="RESTAURANT_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int restaurant_id ;
	
	@Column(name="RESTAURANT_NAME")
	@NotEmpty(message="Restaurant name cannot be empty")
    @Size(max=50, message="Restaurant name must be less than or equal to 50 characters")
    private String restaurant_name; 
	
	@NotEmpty(message="Restaurant address cannot be empty")
    @Size(max=255, message="Restaurant address must be less than or equal to 255 characters")
	@Column(name="RESTAURANT_ADDRESS")
    private String restaurant_address; 
	
	@Column(name="RESTAURANT_PHONE")
	@NotEmpty(message="Restaurant phone cannot be empty")
    @Pattern(regexp="^[+]?[0-9]{10,13}$", message="Restaurant phone should be a valid phone number")
    private String restaurant_phone ;
	
	@ManyToMany(mappedBy = "restaurantsFavorite")
    @JsonIgnore
    private Set<Customers> favoritecustomers;
	
	public Restaurants(int restaurant_id, String restaurant_name, String restaurant_address, String restaurant_phone) {
		super();
		this.restaurant_id = restaurant_id;
		this.restaurant_name = restaurant_name;
		this.restaurant_address = restaurant_address;
		this.restaurant_phone = restaurant_phone;
	}
	
	public Restaurants() {}

	public int getRestaurant_id() {
		return restaurant_id;
	}
	public void setRestaurant_id(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	public String getRestaurant_name() {
		return restaurant_name;
	}
	public void setRestaurant_name(String restaurant_name) {
		this.restaurant_name = restaurant_name;
	}
	public String getRestaurant_address() {
		return restaurant_address;
	}
	public void setRestaurant_address(String restaurant_address) {
		this.restaurant_address = restaurant_address;
	}
	public String getRestaurant_phone() {
		return restaurant_phone;
	}
	public void setRestaurant_phone(String restaurant_phone) {
		this.restaurant_phone = restaurant_phone;
	}
	public Set<Customers> getFavoritecustomers() {
		return favoritecustomers;
	}

	public void setFavoritecustomers(Set<Customers> favoritecustomers) {
		this.favoritecustomers = favoritecustomers;
	}

	@Override
	public String toString() {
		return "Restaurants [restaurant_id=" + restaurant_id + ", restaurant_name=" + restaurant_name
				+ ", restaurant_address=" + restaurant_address + ", restaurant_phone=" + restaurant_phone + "]";
	}
}