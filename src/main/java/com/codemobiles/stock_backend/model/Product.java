package com.codemobiles.stock_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
	private long id;
	private String name;
	private String image;
	private int price;
	private int stock;
}
