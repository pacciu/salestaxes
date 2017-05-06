package com.lastminute.marcoluly.services.impl;

import static com.lastminute.marcoluly.utils.Methods.createBigDecimalWithRoundingModeAndScale;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.lastminute.marcoluly.exceptions.NotNegativePriceException;
import com.lastminute.marcoluly.model.Product;
import com.lastminute.marcoluly.model.ProductWithFinalPrice;
import com.lastminute.marcoluly.model.Receipt;
import com.lastminute.marcoluly.model.Tax;
import com.lastminute.marcoluly.repositories.TaxRepository;
import com.lastminute.marcoluly.services.ProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class ProductServiceImpl, implementation for the Product Service
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService, InitializingBean {

	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	private static final Integer ONE = 1;

	private static final BigDecimal ZERO = BigDecimal.ZERO;

	private static final Double FIVE_PERCENT = 0.05;

	@Autowired
	TaxRepository taxRepository;

	Tax salesTax;

	Tax importDutyTax;

	@Override
	public Receipt createReceipt(List<Product> products) {

		final Stream<Product> productsStream = products.stream();

		final Receipt result = new Receipt(Maps.newHashMap(), ZERO, ZERO);
		final Map<Integer, ProductWithFinalPrice> productWithFinalPrices = result.getProductWithFinalPrices();
		productsStream.forEach(product -> {
			final Integer id = product.getId();
			if (log.isDebugEnabled()) {
				log.debug("Alalyzing product id [" + id + "]");
			}
			ProductWithFinalPrice productWithFinalPrice;
			final Double price = product.getPrice();
			if (price < 0) {
				final NotNegativePriceException cause = new NotNegativePriceException(
						"Product " + product.getDescription() + " has price < 0 [" + price + "]");
				log.error(cause.getMessage());
				if (log.isDebugEnabled()) {
					log.debug("Error on creating receipt", cause);
				}
				throw new RuntimeException(cause);
			}
			final BigDecimal calculatePercent = calculatePercent(product);
			final BigDecimal totalSalesTax = obtainTotalSalesTaxesForAnItem(price, calculatePercent);
			final BigDecimal calculatedFinalPrice = calculateFinalPrice(price, totalSalesTax);
			if (log.isDebugEnabled()) {
				log.debug("Product with id [" + id + "]" + " has tax percent of [" + calculatePercent
						+ "], totalSalesTaxes [" + totalSalesTax + "], calculatedFinalPrice [" + calculatedFinalPrice
						+ "]");
			}
			if (productWithFinalPrices.containsKey(id)) {
				// if in the items of the receipt exists this id, it means that
				// we have another item with quantity > 1
				productWithFinalPrice = productWithFinalPrices.get(id);
				productWithFinalPrice.setQuantity(productWithFinalPrice.getQuantity() + ONE);
				productWithFinalPrice.setFinalPrice(productWithFinalPrice.getFinalPrice().add(calculatedFinalPrice));
			} else {
				productWithFinalPrice = new ProductWithFinalPrice(product, ONE, calculatedFinalPrice);
			}
			productWithFinalPrices.put(id, productWithFinalPrice);
			result.setTotal(result.getTotal().add(calculatedFinalPrice));
			result.setTotalSalesTaxes(
					result.getTotalSalesTaxes().add(createBigDecimalWithRoundingModeAndScale(totalSalesTax)));
		});
		result.setTotal(createBigDecimalWithRoundingModeAndScale(result.getTotal()));
		result.setTotalSalesTaxes(createBigDecimalWithRoundingModeAndScale(result.getTotalSalesTaxes()));
		if (log.isDebugEnabled()) {
			log.debug("Receipt total: " + result);
		}
		return result;
	}

	private BigDecimal calculatePercent(Product product) {
		BigDecimal result = ZERO;
		if (product.getIsImported()) {
			// percent of import duty
			result = result.add(importDutyTax.getPercent());
		}
		if (product.getProductType().getHasSalesTax()) {
			// percent of sales taxes
			result = result.add(salesTax.getPercent());
		}
		return result;
	}

	private BigDecimal calculateFinalPrice(Double price, BigDecimal totalSalesTax) {
		return createBigDecimalWithRoundingModeAndScale(new BigDecimal(price).add(totalSalesTax));
	}

	/**
	 * Function to obtain total sales taxes for an item. The rounding rules for
	 * sales tax are that for a tax rate of n%, a shelf price of p contains
	 * (np/100 rounded up to the nearest 0.05) amount of sales tax
	 */
	private BigDecimal obtainTotalSalesTaxesForAnItem(Double initialPrice, BigDecimal percent) {
		final Double percentResult = initialPrice * percent.doubleValue() / ONE_HUNDRED.doubleValue();
		final double dividedByFivePercent = new BigDecimal(percentResult / FIVE_PERCENT).setScale(0, RoundingMode.UP)
				.doubleValue();
		final double result = dividedByFivePercent * FIVE_PERCENT;
		return createBigDecimalWithRoundingModeAndScale(result);
	}

	/**
	 * After injection of repository, this method sets sales tax and import duty
	 * tax from theirs static IDS
	 */
	@Override
	public void afterPropertiesSet() throws Exception {

		// assertNotNull("Tax repository should not be null", taxRepository);

		final Integer taxSalesId = TaxRepository.TAX_SALES_ID;
		salesTax = taxRepository.findById(taxSalesId);

		final Integer taxImportDutyId = TaxRepository.TAX_IMPORT_DUTY_ID;
		importDutyTax = taxRepository.findById(taxImportDutyId);

		// assertNotNull("Tax sales with id " + taxSalesId + " should be present
		// on respository", salesTax);

		// importDutyTax);

	}

}
