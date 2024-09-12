package com.fooddelivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="DELIVERY_DRIVERS")
public class DeliveryDrivers {
    @Id
    @Column(name="DRIVER_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int driver_id ;
    
    @Column(name="DRIVER_NAME")
    @NotEmpty(message="Driver name cannot be empty")
    @Size(max=30, message="Driver name must be less than or equal to 30 characters")
    private String driver_name;
    
    @Column(name="DRIVER_PHONE")
    @NotEmpty(message="Driver phone cannot be empty")
    @Pattern(regexp="^[+]?[0-9]{10,13}$", message="Driver phone should be a valid phone number")
    private String driver_phone;
    
    @Column(name="DRIVER_VEHICLE")
    @NotEmpty(message="Driver vehicle cannot be empty")
    @Size(max=20, message="Driver vehicle must be less than or equal to 20 characters")
    private String driver_vehicle;
    
	public DeliveryDrivers(int driver_id, String driver_name, String driver_phone, String driver_vehicle) {
		super();
		this.driver_id = driver_id;
		this.driver_name = driver_name;
		this.driver_phone = driver_phone;
		this.driver_vehicle = driver_vehicle;
	}
	
	public DeliveryDrivers() {}

	public int getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}
	public String getDriver_name() {
		return driver_name;
	}
	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}
	public String getDriver_phone() {
		return driver_phone;
	}
	public void setDriver_phone(String driver_phone) {
		this.driver_phone = driver_phone;
	}
	public String getDriver_vehicle() {
		return driver_vehicle;
	}
	public void setDriver_vehicle(String driver_vehicle) {
		this.driver_vehicle = driver_vehicle;
	}
	@Override
	public String toString() {
		return "DeliveryDrivers [driver_id=" + driver_id + ", driver_name=" + driver_name + ", driver_phone="
				+ driver_phone + ", driver_vehicle=" + driver_vehicle + "]";
	}
    
}
