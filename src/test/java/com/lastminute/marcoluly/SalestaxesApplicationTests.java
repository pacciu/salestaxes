package com.lastminute.marcoluly;

import static com.lastminute.marcoluly.utils.Methods.createBigDecimalWithRoundingModeAndScale;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Maps;
import com.lastminute.marcoluly.execptions.NotNegativePriceException;
import com.lastminute.marcoluly.model.Product;
import com.lastminute.marcoluly.model.ProductWithFinalPrice;
import com.lastminute.marcoluly.model.Receipt;
import com.lastminute.marcoluly.services.ProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author m.luly Test class, it works only with active profile mock
 */
@ActiveProfiles("mock")
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SalestaxesApplicationTests {
	Map<Integer, Product> dataset = Dataset.getDataset();

	private static DecimalFormat df;

	static {
		final DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
		df = new DecimalFormat("0.00", otherSymbols);
	}

	@Autowired
	ProductService productService;

	/**
	 * Input 1: 1 book at 12.49 1 music CD at 14.99 1 chocolate bar at 0.85
	 * 
	 * Output 1: 1 book : 12.49 1 music CD: 16.49 1 chocolate bar: 0.85 Sales
	 * Taxes: 1.50 Total: 29.83
	 */
	@Test
	public void test1() throws NotNegativePriceException {
		// GIVEN
		final Map<Integer, Integer> itemsQuantity1 = Maps.newHashMap();
		itemsQuantity1.put(Dataset.BOOK_ID, 1);
		itemsQuantity1.put(Dataset.MUSIC_CD_ID, 1);
		itemsQuantity1.put(Dataset.CHOCOLATE_BAR_ID, 1);

		final Map<Integer, BigDecimal> results1 = Maps.newHashMap();
		results1.put(Dataset.BOOK_ID, createBigDecimalWithRoundingModeAndScale(12.49));
		results1.put(Dataset.MUSIC_CD_ID, createBigDecimalWithRoundingModeAndScale(16.49));
		results1.put(Dataset.CHOCOLATE_BAR_ID, createBigDecimalWithRoundingModeAndScale(0.85));
		final double totalValue = 29.83;
		final double salesTaxesValue = 1.50;

		prepareInputAndTestOutput(itemsQuantity1, results1, totalValue, salesTaxesValue);

	}

	/**
	 * Input 2: 1 imported box of chocolates at 10.00 1 imported bottle of
	 * perfume at 47.50
	 * 
	 * Output 2: 1 imported box of chocolates: 10.50 1 imported bottle of
	 * perfume: 54.65 Sales Taxes: 7.65 Total: 65.15
	 */
	@Test
	public void test2() throws NotNegativePriceException {
		// GIVEN
		final Map<Integer, Integer> itemsQuantity2 = Maps.newHashMap();
		itemsQuantity2.put(Dataset.IMPORTED_BOX_OF_CHOCOLATES_10_00_ID, 1);
		itemsQuantity2.put(Dataset.IMPORTED_BOTTLE_OF_PERFUME_47_50_ID, 1);

		final Map<Integer, BigDecimal> results2 = Maps.newHashMap();
		results2.put(Dataset.IMPORTED_BOX_OF_CHOCOLATES_10_00_ID, createBigDecimalWithRoundingModeAndScale(10.50));
		results2.put(Dataset.IMPORTED_BOTTLE_OF_PERFUME_47_50_ID, createBigDecimalWithRoundingModeAndScale(54.65));
		final double totalValue = 65.15;
		final double salesTaxesValue = 7.65;

		prepareInputAndTestOutput(itemsQuantity2, results2, totalValue, salesTaxesValue);

	}

	/**
	 * Input 3: 1 imported bottle of perfume at 27.99 1 bottle of perfume at
	 * 18.99 1 packet of headache pills at 9.75 1 box of imported chocolates at
	 * 11.25
	 * 
	 * Output 3: 1 imported bottle of perfume: 32.19 1 bottle of perfume: 20.89
	 * 1 packet of headache pills: 9.75 1 imported box of chocolates: 11.85
	 * Sales Taxes: 6.70 Total: 74.68
	 */
	@Test
	public void test3() throws NotNegativePriceException {
		// GIVEN
		final Map<Integer, Integer> itemsQuantity3 = Maps.newHashMap();
		itemsQuantity3.put(Dataset.IMPORTED_BOTTLE_OF_PERFUME_27_99_ID, 1);
		itemsQuantity3.put(Dataset.BOTTLE_OF_PERFUME_ID, 1);
		itemsQuantity3.put(Dataset.PACKET_OF_HEADCHE_PILLS_ID, 1);
		itemsQuantity3.put(Dataset.IMPORTED_BOX_OF_CHOCOLATES_11_25_ID, 1);

		final Map<Integer, BigDecimal> itemsExpectedResults3 = Maps.newHashMap();
		itemsExpectedResults3.put(Dataset.IMPORTED_BOTTLE_OF_PERFUME_27_99_ID,
				createBigDecimalWithRoundingModeAndScale(32.19));
		itemsExpectedResults3.put(Dataset.BOTTLE_OF_PERFUME_ID, createBigDecimalWithRoundingModeAndScale(20.89));
		itemsExpectedResults3.put(Dataset.PACKET_OF_HEADCHE_PILLS_ID, createBigDecimalWithRoundingModeAndScale(9.75));
		itemsExpectedResults3.put(Dataset.IMPORTED_BOX_OF_CHOCOLATES_11_25_ID,
				createBigDecimalWithRoundingModeAndScale(11.85));
		final double totalValue = 74.68;
		final double salesTaxesValue = 6.70;

		prepareInputAndTestOutput(itemsQuantity3, itemsExpectedResults3, totalValue, salesTaxesValue);

	}

	/**
	 * Input 4: 2 book at 24.98 1 chocolate bar at 0.85
	 * 
	 * Output 4: 2 book : 24.98 1 chocolate bar: 0.85 Sales Taxes: 0.00 Total:
	 * 25.83
	 */
	@Test
	public void testWithOneItemWithQuantityTwoAndNoTaxes() throws NotNegativePriceException {
		// GIVEN
		final Map<Integer, Integer> itemsQuantity4 = Maps.newHashMap();
		itemsQuantity4.put(Dataset.BOOK_ID, 2);
		itemsQuantity4.put(Dataset.CHOCOLATE_BAR_ID, 1);

		final Map<Integer, BigDecimal> results4 = Maps.newHashMap();
		results4.put(Dataset.BOOK_ID, createBigDecimalWithRoundingModeAndScale(24.98));
		results4.put(Dataset.CHOCOLATE_BAR_ID, createBigDecimalWithRoundingModeAndScale(0.85));
		final double totalValue = 25.83;
		final double salesTaxesValue = 0;

		prepareInputAndTestOutput(itemsQuantity4, results4, totalValue, salesTaxesValue);

	}

	/**
	 * BAD test
	 * 
	 * A test with negative input should get a RuntimeException with
	 * NotNegativePriceException
	 */
	@Test(expected = NotNegativePriceException.class)
	public void testWithNegativePrice() throws NotNegativePriceException {
		// GIVEN
		final Map<Integer, Integer> itemsQuantity5 = Maps.newHashMap();
		itemsQuantity5.put(Dataset.BOOK_NEGATIVE_PRICE_ID, 1);
		final Map<Integer, BigDecimal> results5 = Maps.newHashMap();
		results5.put(Dataset.BOOK_NEGATIVE_PRICE_ID, createBigDecimalWithRoundingModeAndScale(-12.49));
		final double totalValue = -12.49;
		final double salesTaxesValue = 0;
		try {
			prepareInputAndTestOutput(itemsQuantity5, results5, totalValue, salesTaxesValue);
			fail("It should throws a RuntimeException(cause: NotNegativePriceException)");
		} catch (final RuntimeException e) {
			final Throwable cause = e.getCause();
			assertNotNull(cause);
			assert (cause instanceof NotNegativePriceException);
			throw (NotNegativePriceException) cause;
		}
	}

	private String prettyPrintOutput(Integer quantity, String description, Double price) {
		return quantity + " " + description + " at " + df.format(price);
	}

	private void prepareInputAndTestOutput(Map<Integer, Integer> itemsQuantity,
			Map<Integer, BigDecimal> itemsExpectedResults, double totalValue, double salesTaxesValue) {

		log.info("*****TEST START*****");

		log.info("Printing input:");

		final List<Product> listInput = Lists.newArrayList();

		itemsQuantity.keySet().stream().forEach(productId -> {
			final Product product = dataset.get(productId);
			assertNotNull(product);
			final Integer quantity = itemsQuantity.get(productId);
			log.info(prettyPrintOutput(quantity, product.getDescription(), product.getPrice()));
			for (int i = 0; i < quantity; i++) {
				listInput.add(product);
			}
		});

		// WHEN
		final Receipt output = productService.createReceipt(listInput);

		// THEN
		assertNotNull(output);
		final Map<Integer, ProductWithFinalPrice> productWithFinalPrices = output.getProductWithFinalPrices();
		assertNotNull(productWithFinalPrices);
		assertEquals(itemsExpectedResults.size(), productWithFinalPrices.size());

		log.info("Printing output:");

		productWithFinalPrices.values().stream().forEach(productWithFinalPrice -> {
			final Product product = productWithFinalPrice.getProduct();
			assertNotNull(product);
			final Integer productId = product.getId();
			assert (itemsExpectedResults.containsKey(productId));
			assert (itemsQuantity.containsKey(productId));
			assertEquals(itemsExpectedResults.get(productId), productWithFinalPrice.getFinalPrice());
			assertEquals(itemsQuantity.get(productId), productWithFinalPrice.getQuantity());
			log.info(prettyPrintOutput(productWithFinalPrice.getQuantity(), product.getDescription(),
					productWithFinalPrice.getFinalPrice().doubleValue()));
		});

		final BigDecimal totalSalesTaxes = output.getTotalSalesTaxes();
		assertEquals(new Double(new BigDecimal(salesTaxesValue).doubleValue()),
				new Double(totalSalesTaxes.doubleValue()));

		log.info("Sales Taxes:" + totalSalesTaxes);

		final BigDecimal total = output.getTotal();
		assertEquals(new Double(new BigDecimal(totalValue).doubleValue()), new Double(total.doubleValue()));

		log.info("Total:" + total);

		log.info("*****TEST END*****");
	}

}
