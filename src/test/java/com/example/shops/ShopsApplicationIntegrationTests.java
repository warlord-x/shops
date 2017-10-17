package com.example.shops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.charset.Charset;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.shops.model.Shop;
import com.example.shops.model.ShopAddress;
import com.example.shops.service.ShopService;
import com.google.gson.Gson;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class ShopsApplicationIntegrationTests {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	@Rule
    public ExpectedException thrown= ExpectedException.none();
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ShopService shopService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void addShopTest() throws Exception {
		
		/*Arrange*/
		//update address
		String shopJson = getDummyShopJson("abc",3000);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.post("/shops")
										.accept(MediaType.APPLICATION_JSON)
										.content(shopJson)
										.contentType(contentType);
		
		/*Act*/
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		/*Assert*/
		
		//check partial values
		String expected = "{\"name\":\"abc\"}"; 
		
		String addedShop = result.getResponse().getContentAsString();
		
		//verify added shop
		JSONAssert.assertEquals(expected, addedShop, false); 
		
		//verify that shops repository is not empty
		assertEquals(shopService.getAllShops().isEmpty(), false); 
		
		Shop shopFromJson = new Gson().fromJson(addedShop, Shop.class);
		//verify latitude / longitude is updated via google maps service
		assertNotNull(shopFromJson.getAddress().getLatitude()); 
		assertNotNull(shopFromJson.getAddress().getLongitude());
	}
	
	@Test
	public void updateShopTest() throws Exception { 
		/*Arrange*/
		String shopJson = getDummyShopJson("abc",3002);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.post("/shops")
										.accept(MediaType.APPLICATION_JSON)
										.content(shopJson)
										.contentType(contentType);
		
		/*Act*/
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		/*Assert*/
		
		String addedShop = result.getResponse().getContentAsString();
		Shop shopFromJson = new Gson().fromJson(addedShop, Shop.class);
		
		//verify address is updated for same shop
		assertEquals(shopFromJson.getAddress().getPostcode(),3002); 
		//verify version is incremented for same shop
		assertEquals(shopFromJson.getVersion(),2);
		
	}
	
	@Test
	public void getNearestShop() throws Exception { 
		/*Arrange*/
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
										.get("/shops/getnearestshop/50.4202533/13.4189007/")
										.accept(MediaType.APPLICATION_JSON)
										.contentType(contentType);
		
		/*Act*/
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		/*Assert*/
		
		String addedShop = result.getResponse().getContentAsString();
		Shop shopFromJson = new Gson().fromJson(addedShop, Shop.class);
		//verify a near shop is found
		assertNotNull(shopFromJson.getName());
		
	}
	
	public String getDummyShopJson(String name, long postcode) {
		ShopAddress shopAd = new ShopAddress(1,postcode);
		Shop shop = new Shop(name,shopAd);
		Gson gson = new Gson();
		return gson.toJson(shop);
	}
	
	
}
