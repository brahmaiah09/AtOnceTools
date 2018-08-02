package com.atonce.tool.service;

import java.util.List;

import com.atonce.tool.model.cpd.CpdProduct;

public interface ProductValidatorService {
	public List<CpdProduct> productValidator(String styleColor, String season, String region,
			String priceListType);
}
