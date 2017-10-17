package com.example.shops.model;

public class Shop {

	private String name;
	private ShopAddress address;
	
	private long version;

	
	public Shop() {
	}

	public Shop(String name, ShopAddress address) {
		super();
		this.name = name;
		this.address = address;
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ShopAddress getAddress() {
		return address;
	}
	public void setAddress(ShopAddress address) {
		this.address = address;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj.getClass()==this.getClass()))
			return false;
		
		Shop temp = (Shop)obj;
		
		if(this.name==temp.getName()) {
			return true;
		}
		
		return false;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
}
