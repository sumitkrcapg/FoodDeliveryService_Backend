package com.fooddelivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="RATINGS")
public class Ratings {
    @Id
    @Column(name="RATING_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int rating_id;
    
    @Column(name="RATING")
    @NotEmpty(message="Rating cannot be null")
    @Min(value=1, message="Rating must be between 1 and 5")
    @Max(value=5, message="Rating must be between 1 and 5")
    private int rating;
    
    @Column(name="REVIEW")
    @NotEmpty(message="Review cannot be null")
    @Size(max=255, message="Review must be less than or equal to 255 characters")
    private String review;
    
    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Restaurants restaurants;
    
    @ManyToOne
    @JoinColumn(name="order_id")
    private Orders orders;
    
	public Ratings(int rating_id, int rating, String review) {
		super();
		this.rating_id = rating_id;
		this.rating = rating;
		this.review = review;
	}
	public Ratings() {}
	
	public int getRating_id() {
		return rating_id;
	}
	public void setRating_id(int rating_id) {
		this.rating_id = rating_id;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	
	public Restaurants getRestaurants() {
		return restaurants;
	}
	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}
	public Orders getOrder() {
		return orders;
	}
	public void setOrder(Orders orders) {
		this.orders = orders;
	}
	@Override
	public String toString() {
		return "Ratings [rating_id=" + rating_id + ", rating=" + rating + ", review=" + review + "]";
	}
}
