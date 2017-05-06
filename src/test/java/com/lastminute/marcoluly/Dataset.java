package com.lastminute.marcoluly;

import java.util.Map;

import com.google.common.collect.Maps;
import com.lastminute.marcoluly.model.Product;
import com.lastminute.marcoluly.model.ProductType;

/**
 * 
 * @author m.luly
 * 
 *         Dataset for the tests
 */
public class Dataset {

	public static final Integer BOOK_ID = 1;

	public static final Integer MUSIC_CD_ID = 2;

	public static final Integer CHOCOLATE_BAR_ID = 3;

	public static final Integer IMPORTED_BOX_OF_CHOCOLATES_10_00_ID = 4;

	public static final Integer IMPORTED_BOTTLE_OF_PERFUME_47_50_ID = 5;

	public static final Integer IMPORTED_BOTTLE_OF_PERFUME_27_99_ID = 6;

	public static final Integer BOTTLE_OF_PERFUME_ID = 7;

	public static final Integer PACKET_OF_HEADCHE_PILLS_ID = 8;

	public static final Integer IMPORTED_BOX_OF_CHOCOLATES_11_25_ID = 9;

	public static final Integer BOOK_NEGATIVE_PRICE_ID = 10;

	private static Map<Integer, Product> dataset = Maps.newHashMap();

	static {

		final ProductType books = buildProductType(1, "Books", false);

		final ProductType food = buildProductType(2, "Food", false);

		final ProductType medicalProducts = buildProductType(3, "Medical products", false);

		final ProductType music = buildProductType(4, "Music", true);

		final ProductType perfumes = buildProductType(5, "Perfumes", true);

		createProductAndAddToDataset(BOOK_ID, "Book", false, (12.49), books);

		createProductAndAddToDataset(MUSIC_CD_ID, "Music cd", false, (14.99), music);

		createProductAndAddToDataset(CHOCOLATE_BAR_ID, "Chocolate bar", false, (0.85), food);

		createProductAndAddToDataset(IMPORTED_BOX_OF_CHOCOLATES_10_00_ID, "Imported box of chocolates", true, (10.00),
				food);

		createProductAndAddToDataset(IMPORTED_BOTTLE_OF_PERFUME_47_50_ID, "imported bottle of perfume", true, (47.50),
				perfumes);

		createProductAndAddToDataset(IMPORTED_BOTTLE_OF_PERFUME_27_99_ID, "imported bottle of perfume", true, (27.99),
				perfumes);

		createProductAndAddToDataset(BOTTLE_OF_PERFUME_ID, "bottle of perfume", false, (18.99), perfumes);

		createProductAndAddToDataset(PACKET_OF_HEADCHE_PILLS_ID, "packet of headache pills", false, (9.75),
				medicalProducts);

		createProductAndAddToDataset(IMPORTED_BOX_OF_CHOCOLATES_11_25_ID, "box of imported chocolates", true, (11.25),
				food);

		createProductAndAddToDataset(BOOK_NEGATIVE_PRICE_ID, "Book", false, (-12.49), books);
	}

	private static void createProductAndAddToDataset(Integer id, String description, boolean isImported, Double price,
			ProductType productType) {
		final Product product = Product.builder().id(id).description(description).isImported(isImported).price(price)
				.productType(productType).build();
		dataset.put(id, product);
	}

	private static ProductType buildProductType(int id, String description, Boolean hasSalesTax) {
		return ProductType.builder().id(id).description(description).hasSalesTax(hasSalesTax).build();
	}

	public static final Map<Integer, Product> getDataset() {

		return dataset;

	}

}
