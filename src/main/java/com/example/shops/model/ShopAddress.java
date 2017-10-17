package com.example.shops.model;

public class ShopAddress {

	private double longitude;
	private double latitude;
	private long number;
	private long postcode;
	
	
	
	public ShopAddress() {
	}


	public ShopAddress(long number, long postcode) {
		super();
		this.number = number;
		this.postcode = postcode;
	}
	
	
	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public long getPostcode() {
		return postcode;
	}
	public void setPostcode(long postcode) {
		this.postcode = postcode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj.getClass()==this.getClass()))
			return false;
		
		ShopAddress temp = (ShopAddress)obj;
		
		if(this.longitude==temp.getLongitude()&&this.latitude==temp.getLatitude()&&this.number==temp.getNumber()&&this.postcode==temp.getPostcode()) {
			return true;
		}
		
		return false;
	}
}
