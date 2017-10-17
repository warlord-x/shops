package com.example.shops.repo;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.shops.model.Shop;

@Component
public class InMemoryShops implements ShopsRepository {

	Hashtable<String, Shop> shops;
	
	@Override
	public Shop addShop(Shop shop) {
		if(shops==null)
			shops = new Hashtable<String,Shop>();//Thread safe 
		
		if(!shopExists(shop)) {
			shop.setVersion(1);
		}
		else {
			if(!shop.getAddress().equals(shops.get(shop.getName()).getAddress()))
				shop = updateShop(shop);
			else
				return shops.get(shop.getName());
		}
		shops.put(shop.getName(),shop);
		
		return shop;
	}

	
	private Shop updateShop(Shop shop) {
		long newVersion = shops.get(shop.getName()).getVersion()+1;
		shop.setVersion(newVersion);
		return shop;
	}

	private boolean shopExists(Shop shop) {
		if(shops.get(shop.getName())!=null)
			return true;
		else
			return false;
	}

	
	@Override
	public List<Shop> getAllShop() {
		if(shops==null)
			return null;
		return shops.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
	}
}
