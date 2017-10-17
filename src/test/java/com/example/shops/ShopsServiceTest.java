package com.example.shops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.shops.model.Shop;
import com.example.shops.model.ShopAddress;
import com.example.shops.repo.InMemoryShops;
import com.example.shops.repo.ShopsRepository;
import com.example.shops.service.GeoCodeService;
import com.example.shops.service.ShopService;
import com.google.maps.model.LatLng;


@RunWith(SpringRunner.class)
public class ShopsServiceTest {
	
	
	@InjectMocks
	private ShopService shopService=new ShopService();
	
	@Mock
	ShopsRepository shopsRepo;
	
	@Mock
	GeoCodeService geoCodeService;
	
	
	@Test
	public void nearstShopTest() {
		//Arrange
		ShopAddress shopAd = new ShopAddress(1,3000);
		shopAd.setLatitude(39.35871030);
		shopAd.setLongitude(-109.70879110);
		LatLng origin = new LatLng(40.35871030,-110.70879110);
		Shop shop = new Shop("testShop",shopAd);
		Mockito.when(shopsRepo.getAllShop()).thenReturn(Arrays.asList(shop));
		Mockito.when(geoCodeService.getNearstShop(origin, Arrays.asList(shop))).thenReturn(new Shop("nearShop",shopAd));
		//Act
		Shop nearstShop = shopService.getNearstShop(origin);
		//Assert
		assertNotNull(nearstShop);
	}
	
	@Test
	public void versionUpdateTest() {
		
		ShopAddress shopAd  = new ShopAddress(1, 38000);
		Shop shop = new Shop("dummyname",shopAd);
		Shop shop2 = new Shop("dummyname",shopAd);
		InMemoryShops shopsRepo = new InMemoryShops();
		
		Shop addedShop = shopsRepo.addShop(shop);
		Shop addedShop2 = shopsRepo.addShop(shop2);
		
		
		assertEquals(addedShop.getVersion(), addedShop2.getVersion());
		
	}
	
}
