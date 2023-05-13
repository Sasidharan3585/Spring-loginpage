package com.crash4.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	private int  itemId;
	private String itemName;
	private int  price;
	private  int quantity;
	

}
