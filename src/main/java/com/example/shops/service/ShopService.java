package com.example.shops.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shops.model.Shop;
import com.example.shops.repo.ShopsRepository;
import com.google.maps.model.LatLng;

@Service
public class ShopService {

	@Autowired
	ShopsRepository shopsRepo;
	
	@Autowired
	GeoCodeService geoCodeService;
	
	public Shop addShop(Shop shop) {
		geoCodeService.setLatLng(shop.getAddress());
		return shopsRepo.addShop(shop);
	}
	
	public Shop getNearstShop(LatLng origin){
		List<Shop> allShop = shopsRepo.getAllShop();
		if(allShop==null)
			return null;
		else
			return geoCodeService.getNearstShop(origin, allShop );
	}

	public List<Shop> getAllShops() {
		return shopsRepo.getAllShop();
	}
	
	
}
