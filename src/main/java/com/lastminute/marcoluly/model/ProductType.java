package com.lastminute.marcoluly.model;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author m.luly
 *
 *         The type of the product: book, food, ...
 *
 */
@Data
@Builder
public class ProductType {

	private Integer id;

	private String description;

	private Boolean hasSalesTax;

}
