package com.atonce.tool.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.atonce.tool.model.db.DbProduct;

public class ProductPropertyRowMapper implements RowMapper<DbProduct> {
	public DbProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
	      
		  DbProduct product = new DbProduct();
	      product.setStyleColor(rs.getString("STYL_COLR"));
	      product.setSeason(rs.getString("SEASON"));
	      product.setRgn(rs.getString("RGN"));
	      product.setSizes(rs.getString("PROD_SZ"));
	      product.setBeginOfferDate(rs.getDate("BEGIN_OFFER_DT"));
	      product.setEndOfferDate(rs.getDate("END_OFFER_DT"));
	      product.setPriceListType(rs.getString("PR_LIST_TYP"));
	      product.setRetailPrice(rs.getInt("RETAIL_PRICE"));
	      product.setWholeSalesPrice(rs.getInt("WHSL_PRICE"));
	      //product.setSizes(rs.getString("PROD_SZ"));
	      return product;
	   }
}
