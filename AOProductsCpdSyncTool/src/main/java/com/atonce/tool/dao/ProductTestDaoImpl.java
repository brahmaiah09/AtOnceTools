package com.atonce.tool.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.atonce.tool.mapper.ProductPropertyRowMapper;
import com.atonce.tool.model.cpd.GlobalProducts;
import com.atonce.tool.model.db.DbProduct;
@Service
public class ProductTestDaoImpl implements ProductTestDao {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private JdbcTemplate jdbcTemplate;
	private DbProduct dbProduct;
	public static final String REST_SERVICE_URI = "https://test-cpdservices.nike.net/productservice/products.json?productCode={productCode}&season={season}&region={region}";
	private RestTemplate restTemplate;
	private final String SQL_QUERY="SELECT STYL_COLR, SEASON, PR_LIST_TYP, WHSL_PRICE, RETAIL_PRICE, BEGIN_OFFER_DT, END_OFFER_DT, RGN, PROD_SZ FROM test.prod where STYL_COLR =? AND SEASON =? AND RGN =? AND PR_LIST_TYP=?";
	
	public DbProduct fetchDbProduct(String styleColor, String season, String region, String priceListType)
	{
		try {
			log.info("----Starting-----");	
		log.info("Verifying the style {} in DB",styleColor);
		dbProduct=jdbcTemplate.queryForObject(SQL_QUERY, new Object[]{styleColor, season, region, priceListType}, new ProductPropertyRowMapper());
		log.info("Style {} is available in DB",styleColor);
		//log.info("Style {} check status in CPD service is {}",styleColor,status);
		return dbProduct;
		}catch(EmptyResultDataAccessException e) {
			//System.out.println(e);
			log.info("Style {} is not available in DB",styleColor);
			return null;
		}
		
	}
	
	public GlobalProducts fetchCpdProduct(String styleColor, String season, String region, String priceListType)
	{
		restTemplate = new RestTemplate();
		GlobalProducts cpdProducts=null;
		log.info("Verifying the style {} in CPD Service",styleColor);
		try {
			Map<String, String> uriParams = new HashMap<String, String>();
	        uriParams.put("productCode", styleColor);
	        uriParams.put("season", season);
	        uriParams.put("region", region);
		cpdProducts = restTemplate.getForObject(REST_SERVICE_URI, GlobalProducts.class,uriParams);
		log.info("Style {} is available in CPD Service",cpdProducts.getProducts()[0].getProductCode());
		return cpdProducts;
		}catch(Exception e){
			log.info("Style {} is not available in CPD Service, Need to contact source systems for furtehr action",styleColor);
			//System.out.println(e);
			return null;
		}
	}
		
}