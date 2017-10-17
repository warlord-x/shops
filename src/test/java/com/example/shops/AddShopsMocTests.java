package com.example.shops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.shops.model.Shop;
import com.example.shops.model.ShopAddress;
import com.example.shops.repo.InMemoryShops;
import com.example.shops.service.GeoCodeService;
import com.example.shops.service.ShopService;


@RunWith(SpringRunner.class)
public class AddShopsMocTests {

	
	@InjectMocks
	private ShopsController shopsController;
	
	@MockBean
	InMemoryShops inMomoryShop;
	
	
	
	/*@Test
	public void testTemplate() {
		//Arrange
		//Act
		//Assert
	}*/	

	@Mock
	private ShopService shopService;
	
	@Mock
	private GeoCodeService geoCodeService;

	@Test
	public void shopAddressTest() {
		//Arrange
		ShopAddress shopAd = new ShopAddress(1,3000);
		Shop shop = new Shop("dummyname",shopAd);
		ShopAddress shopAd2 = new ShopAddress(1,3000);
		Shop shop2 = new Shop("dummyname",shopAd2);
		//Act
		//Assert
		assertEquals(shop.getAddress(), shop2.getAddress());
		assertNotEquals(shop.getAddress(), new ShopAddress(4,3000));
	}
	
	@Test
	public void addShopTest() {
		//Arrange
		ShopAddress shopAd = new ShopAddress(1,3000);
		Shop shop = new Shop("dummyname",shopAd);
		Mockito.when(shopService.addShop(shop)).thenReturn(new Shop("dummyname",shopAd));
		//Act
		Shop addedShop = shopService.addShop(shop);
		//Assert
		verify(shopService).addShop(shop);
		assertTrue(addedShop.equals(shop));
		
	}
	
	@Test
	public void sameShopTest() {
		//Arrange
		ShopAddress shopAd = new ShopAddress(1,3000);
		Shop shop = new Shop("dummyname",shopAd);
		
		Mockito.when(shopService.addShop(shop)).thenReturn(new Shop("dummyname",shopAd));
		//Act
		Shop addedShop = shopService.addShop(shop);
		//Assert
		verify(shopService).addShop(shop);
		assertTrue(addedShop.equals(shop));	
	}
	
	
	@Test
	public void addShopAddressTest() {
		//Arrange
		ShopAddress shopAd = new ShopAddress(1,30000);
		Shop shop = new Shop("dummyname",shopAd);
		Mockito.when(shopService.addShop(shop)).thenReturn(new Shop("dummyname",shopAd));
		//Act
		Shop addedShop = shopService.addShop(shop);
		//Assert
		assertEquals(addedShop.getAddress(), shopAd);
	}


	@Test
	public void getAllShopsTest() {
		//Arrange
		ShopAddress shopAd = new ShopAddress(1,3000);
		Shop shop = new Shop("dummyname",shopAd);
		ShopAddress shopAd2 = new ShopAddress(1,3000);
		Shop shop2 = new Shop("dummyname",shopAd2);
		List<Shop> shops = Arrays.asList(shop,shop2);
		Mockito.when(shopService.getAllShops()).thenReturn(shops);
		//Act
		List<Shop> shopsReturned =  shopService.getAllShops();
		//Assert
		assertEquals(shopsReturned.size(), 2);
	}
	
}
