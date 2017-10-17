package com.example.shops.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.shops.model.Shop;

@Repository
public interface ShopsRepository {

	public Shop addShop(Shop shop);

	public List<Shop> getAllShop();
}
