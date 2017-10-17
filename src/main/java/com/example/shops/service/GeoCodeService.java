package com.example.shops.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shops.model.Shop;
import com.example.shops.model.ShopAddress;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

@Service
public class GeoCodeService {

	
	public LatLng setLatLng(ShopAddress shAd) {
		GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyDf6fB7U_Kr2v5lc8tK8TBQ-Cze6JibEFE").build();
		GeocodingResult[] results = null;
		GeocodingResult geocodingResult = null;
		try {
			results = GeocodingApi.geocode(context, shAd.getNumber() + "," + shAd.getPostcode()).await();
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
		if (results.length>0) {
			geocodingResult = results[0];
			shAd.setLatitude(geocodingResult.geometry.location.lat);
			shAd.setLongitude(geocodingResult.geometry.location.lng);
		}
		else
			return null;
		
		return geocodingResult.geometry.location;
	}


	
	public Shop getNearstShop(LatLng origin,List<Shop> shops){
		GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyDf6fB7U_Kr2v5lc8tK8TBQ-Cze6JibEFE").build();
		Shop nearestShop = null;
		long distance = Long.MAX_VALUE;
		for(Shop shop:shops) {
			if(nearestShop==null)
				nearestShop = shop;
			
			if(shop.getAddress().getLatitude()==0||shop.getAddress().getLongitude()==0)
				continue;
			
			LatLng destination=new LatLng(shop.getAddress().getLatitude(),shop.getAddress().getLongitude());
			DistanceMatrix distanceMatrix;
			try {
				distanceMatrix = DistanceMatrixApi.newRequest(context).origins(origin).destinations(destination).await();
				if (isCorrectResult(distanceMatrix)) {
					if(distanceMatrix.rows[0].elements[0].distance.inMeters<distance) {
						nearestShop = shop;
					}
				}
			} catch (ApiException | InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
		  return nearestShop;
	}


	private boolean isCorrectResult(DistanceMatrix distanceMatrix) {
		boolean result  = false;
			if( !(distanceMatrix.rows.length == 0 || distanceMatrix.rows[0].elements.length == 0 ))
				if(distanceMatrix.rows[0].elements[0].status == DistanceMatrixElementStatus.OK )
					return true;
		return result;
	}
}