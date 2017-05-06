package com.lastminute.marcoluly.model;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author m.luly
 *
 *
 *         Class with the informations of the receipt: the items with final
 *         price total of taxes and total
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

	Map<Integer, ProductWithFinalPrice> productWithFinalPrices;

	BigDecimal totalSalesTaxes;

	BigDecimal total;

}
