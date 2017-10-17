package com.example.shops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.shops.model.Shop;
import com.example.shops.service.ShopService;
import com.google.maps.model.LatLng;




@RestController
public class ShopsController {
	
	@Autowired
	private ShopService shopService;
	
	@RequestMapping(method=RequestMethod.POST,value="/shops")
	public Shop addShops(@RequestBody Shop shop) {
		return shopService.addShop(shop);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/shops/getnearestshop/{lat}/{lng}")
	public Shop addShops(@PathVariable double lat, @PathVariable double lng) {
		return shopService.getNearstShop(new LatLng(lat,lng));
	}
	
}
