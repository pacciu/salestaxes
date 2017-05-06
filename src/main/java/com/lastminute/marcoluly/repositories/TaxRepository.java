package com.lastminute.marcoluly.repositories;

import com.lastminute.marcoluly.model.Tax;

/**
 * 
 * @author m.luly
 *
 *
 *         Interface for the repository of Taxes, two known values ids: sales
 *         taxes and import duty taxes
 */
public interface TaxRepository {

	public static final Integer TAX_SALES_ID = 1;

	public static final Integer TAX_IMPORT_DUTY_ID = 2;

	public Tax findById(Integer id);

}
