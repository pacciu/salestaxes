package com.lastminute.marcoluly.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author m.luly
 * 
 *         The product in a receipt with informations about quantity and price
 *         with taxes
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithFinalPrice {

	Product product;

	Integer quantity;

	BigDecimal finalPrice;

}
