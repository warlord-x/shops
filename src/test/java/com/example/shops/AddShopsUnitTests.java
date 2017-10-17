package com.example.shops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.shops.model.Shop;
import com.example.shops.model.ShopAddress;
import com.example.shops.repo.InMemoryShops;
import com.example.shops.repo.ShopsRepository;
import com.example.shops.service.GeoCodeService;
import com.example.shops.service.ShopService;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;


@RunWith(SpringRunner.class)
public class AddShopsUnitTests {

	
	@InjectMocks
	private ShopsController shopsController;
	
	@MockBean
	InMemoryShops inMomoryShop;
	
	@Autowired
	private ShopsRepository shopsRepository;
	
	
	@Mock
	private ShopService shopService;

	public Shop getDummyShop() {
		ShopAddress shopAd = new ShopAddress(1,3000);
		Shop shop = new Shop("dummyname",shopAd);
		return shop;
	}
	
	@Test
	public void shopAddressTest() {
		//Arrange
		Shop shop = getDummyShop();
		ShopAddress shopAd2 = new ShopAddress(1,3000);
		Shop shop2 = new Shop("dummyname",shopAd2);
		//Act
		//Assert
		assertEquals(shop.getAddress(), shop2.getAddress());
		assertNotEquals(shop.getAddress(), new ShopAddress(4,3000));
	}

	
	@Test
	public void updateShopVersionTest() {
		//Arrange
		shopsRepository = new InMemoryShops(); //use actual implementation to check update method
		ShopAddress shopAd = new ShopAddress(1,30000);
		Shop shop1 = new Shop("dummyname",shopAd);
		shopsRepository.addShop(shop1);
		
		//Act
		Shop shop2 = new Shop("dummyname",new ShopAddress(2,30000));
		shopsRepository.addShop(shop2);
		
		Shop shop3 = new Shop("dummyname",new ShopAddress(3,30000));
		Shop addedShop3 = shopsRepository.addShop(shop3);

		//Assert
		
		assertEquals(3, addedShop3.getVersion());
		assertTrue(addedShop3.getAddress().getNumber()==3);
	}
	
	
	
	
	@Test
	public void getGeoCodeTest() throws ApiException, InterruptedException, IOException {
		//Arrange
		
		ShopAddress shopAd  = new ShopAddress(1, 38000);
		GeoCodeService geoService = new GeoCodeService();
		//Act
		LatLng setLatLng = geoService.setLatLng(shopAd);
		shopAd.setLatitude(setLatLng.lat);
		shopAd.setLongitude(setLatLng.lng);
		
		//Assert
		assertEquals(shopAd.getLatitude(), 40.35871030,1.0);
		assertEquals(shopAd.getLongitude(), -110.70879110,1.0);
		
	}

	
	
}
