package com.lastminute.marcoluly.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author m.luly
 *
 *         Class with percentage of tax, e.g. if tax is 10%, in tax.percent the
 *         value is 10
 */
@Data
@Builder
public class Tax {

	private Integer id;

	private BigDecimal percent;

}
