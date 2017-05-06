package com.lastminute.marcoluly.services;

import java.util.List;

import com.lastminute.marcoluly.model.Product;
import com.lastminute.marcoluly.model.Receipt;

/**
 * 
 * @author m.luly
 * 
 *         The service with business logic for creation of a Receipt
 *
 */
public interface ProductService {

	/**
	 * Creates the receipt.
	 *
	 * @param products
	 *            the products given in input
	 * @return the receipt with all informations
	 * @throws RuntimeException
	 *             of NotNegativePriceException when a product in input has
	 *             negative price
	 */
	Receipt createReceipt(List<Product> products);

}
