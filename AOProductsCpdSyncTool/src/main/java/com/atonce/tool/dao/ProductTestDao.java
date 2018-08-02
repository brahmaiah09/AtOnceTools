package com.atonce.tool.dao;

import com.atonce.tool.model.cpd.GlobalProducts;
import com.atonce.tool.model.db.DbProduct;
public interface ProductTestDao {

	public DbProduct fetchDbProduct(String styleColor, String season, String region, String priceListType);
	public GlobalProducts fetchCpdProduct(String styleColor, String season, String region, String priceListType);
}
