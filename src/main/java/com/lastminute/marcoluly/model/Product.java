package com.lastminute.marcoluly.model;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author m.luly
 * 
 *         Class with product informations
 *
 */
@Data
@Builder
public class Product {

	private Integer id;

	private String description;

	private ProductType productType;

	private Double price;

	private Boolean isImported;

}
