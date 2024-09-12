package com.fooddelivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="DELIVERY_ADDRESSES")
public class DeliveryAddresses {
	@Id
	@Column(name="ADDRESS_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int address_id;
	
	@Column(name="ADDRESS_LINE1")
	@NotEmpty(message="Address line 1 cannot be empty")
    @Size(max=100, message = "Address line 1 must be less than or equal to 100 characters")
	private String address_line1;
	
	@Column(name="ADDRESS_LINE2")
	@Size(max=100, message="Address line 2 must be less than or equal to 100 characters")
	private String address_line2;
	
	@Column(name="CITY")
	@NotEmpty(message="City cannot be empty")
    @Size(max=50, message="City must be less than or equal to 50 characters")
	private String city;
	
	@Column(name="STATE")
	@NotEmpty(message="State cannot be empty")
    @Size(max=50, message="State must be less than or equal to 50 characters")
	private String state;
	
	@Column(name="POSTAL_CODE")
	@NotEmpty(message="Postal code cannot be empty")
    @Pattern(regexp="^[1-9][0-9]{4}$", message="Postal code must be a valid format")
	private String postal_code;

	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customers customers;
	
	public DeliveryAddresses(int address_id, String address_line1, String address_line2, String city,
			String state, String postal_code) {
		super();
		this.address_id = address_id;
		this.address_line1 = address_line1;
		this.address_line2 = address_line2;
		this.city = city;
		this.state = state;
		this.postal_code = postal_code;
	}

	public DeliveryAddresses() {}
	
	public int getAddress_id() {
		return address_id;
	}

	public Customers getCustomers() {
		return customers;
	}

	public void setCustomers(Customers customers) {
		this.customers = customers;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public String getAddress_line1() {
		return address_line1;
	}

	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}

	public String getAddress_line2() {
		return address_line2;
	}

	public void setAddress_line2(String address_line2) {
		this.address_line2 = address_line2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	@Override
	public String toString() {
		return "DeliveryAddresses [address_id=" + address_id + ", address_line1="
				+ address_line1 + ", address_line2=" + address_line2 + ", city=" + city + ", state=" + state
				+ ", postal_code=" + postal_code + "]";
	}
}
