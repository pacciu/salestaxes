package com.lastminute.marcoluly.mock;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.lastminute.marcoluly.model.Tax;
import com.lastminute.marcoluly.repositories.TaxRepository;

/**
 * 
 * @author m.luly
 *
 *         Mocked Repository for taxes
 */
@Profile("mock")
@Component
public class TaxRepositoryMock implements TaxRepository {

	private static Map<Integer, Tax> taxes = Maps.newHashMap();

	static {

		final Tax salesTax = buildTax("10", TAX_SALES_ID);

		final Tax importDutyTax = buildTax("5", TAX_IMPORT_DUTY_ID);

		taxes.put(TAX_SALES_ID, salesTax);
		taxes.put(TAX_IMPORT_DUTY_ID, importDutyTax);

	}

	private static Tax buildTax(String percent, Integer id) {
		return Tax.builder().id(id).percent(new BigDecimal(percent)).build();
	}

	@Override
	public Tax findById(Integer id) {
		return taxes.get(id);
	}

}
