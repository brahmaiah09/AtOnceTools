package com.atonce.tool.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.atonce.tool.aws.connect.AwsReprocess;
import com.atonce.tool.dao.ProductTestDao;
import com.atonce.tool.model.cpd.CpdProduct;
import com.atonce.tool.model.cpd.GlobalProducts;
import com.atonce.tool.model.cpd.Prices;
import com.atonce.tool.model.cpd.Sizes;
import com.atonce.tool.model.db.DbProduct;

@Component
@Service
public class ProductValidatorServiceImpl implements ProductValidatorService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ProductTestDao productTestDao;
	private AwsReprocess awsReprocess;
	private int cpdWhloleSalesPrice;
	private int cpdRetailPrice;
	private int dbWholeSalesPrice;
	private int dbRetailPrice;
	private DbProduct dbProduct;
	private List<String> dbSizesList;
	private String dbBeginOfferDate;
	private String dbEndOfferDate;
	private GlobalProducts globalProducts;
	private CpdProduct cpdProd;
	private String cpdBeginOfferDate;
	private String cpdEndOfferDate;
	private Sizes[] cpdSizes;
	private List<String> cpdSizesList;
	private List<CpdProduct> productdata;

	public List<CpdProduct> productValidator(String styleColor, String season, String region,
			String priceListType) {
		
		productdata = new ArrayList<CpdProduct>();
		dbProduct = getDbStyleData(styleColor, season, region, priceListType);// DB data retriving
		cpdProd = getCpdStyleData(styleColor, season, region, priceListType);// CPD data retriving:

		// if style availability status CPD=YES, MySql=YES compare the parameters
		// of style and if any discrepancy, go for CPD reload process.
		// parameters# wholesale price, retail price, begin&end offer dates, product sizes
		if (dbProduct != null && globalProducts != null) {
			boolean sizesFlag=false;
			boolean offerDates=false;
			boolean prices=false;
			
			log.info("Validating the parameters of the Style {} in CPD service and DB", cpdProd.getProductCode());
			
			for (int i = 0; i < dbSizesList.size(); i++) {
				if (dbSizesList.contains(cpdSizesList.get(i))) {
					log.info("Size {} in DB is in sync with CPD service ", cpdSizesList.get(i));
				} else {
					log.info("Size {} in DB is not in sync with CPD service ", cpdSizesList.get(i));
					sizesFlag=true;
				}
			}
			if (cpdBeginOfferDate.equals(dbBeginOfferDate) && cpdEndOfferDate.equals(dbEndOfferDate)) {
				log.info("Offer dates for style {} in DB are IN SYNC with CPD service ", cpdProd.getProductCode());
			} else {
				log.info("Offer dates for style {} in DB are NOT IN SYNC with CPD service ", cpdProd.getProductCode());
				offerDates=true;
			}
			if (cpdWhloleSalesPrice==dbWholeSalesPrice && cpdRetailPrice==dbRetailPrice) {
				log.info("Prices in DB is in sync with CPD service ");
			} else {
				log.info("Prices in DB is not in sync with CPD service ");
				prices=true;
			}
			if(sizesFlag||offerDates||prices)
			{
				log.info("One of the parameters of style {} is not in sync with CPD, need to do CPD reload in AWS SQS for Style# {}", styleColor);
				awsReprocess = new AwsReprocess();
				// awsReprocess.publishMessage("LOAD_CPD_PRODUCT,"+region+","+season+","+styleColor,
				// "MyTestQueue");
			}
		} 
		
		else if (dbProduct == null && globalProducts != null) {
			CpdProduct product = globalProducts.getProducts()[0];
			product.getLifecycle();
			product.getSourceStatusCode();
			product.getSourceDeletionIndicator();
			product.getPrices();
			product.getRegionalOfferDates();
			product.getSizes();
			if (product.getLifecycle().equals("ACTIVE") || product.getSourceStatusCode().equals("A")
					|| product.getSourceDeletionIndicator().equals(false) || !product.getPrices().equals(null)
					|| !product.getRegionalOfferDates().equals(null) || !product.getSizes().equals(null)) {
				log.info("Style {} is valid, need to do CPD reload in AWS SQS for Style", styleColor);
				awsReprocess = new AwsReprocess();
				// awsReprocess.publishMessage("LOAD_CPD_PRODUCT,"+region+","+season+","+styleColor,
				// "MyTestQueue");
			}
		}

		// if(styleColor.equals(cpdProduct.getProducts()[0].getProductCode()))

		return productdata;
	}

	private CpdProduct getCpdStyleData(String styleColor, String season, String region, String priceListType) {

		globalProducts = productTestDao.fetchCpdProduct(styleColor, season, region, priceListType);
		if (globalProducts != null) {
			cpdProd = globalProducts.getProducts()[0];
			productdata.add(cpdProd);
			cpdBeginOfferDate = cpdProd.getRegionalOfferDates().getFirstProduct().substring(0, 10);// begin offer date
			cpdEndOfferDate = cpdProd.getRegionalOfferDates().getEndProduct().substring(0, 10);// end offer date
			cpdSizes = cpdProd.getSizes();
			cpdSizesList = new ArrayList<String>();// ----Sizes
			for (int i = 0; i < cpdSizes.length; i++) {
				if (!cpdSizes[i].getPromoIndicator().equals(null) && cpdSizes[i].getPromoIndicator().equals("false"))
					cpdSizesList.add(cpdSizes[i].getSizeDescription());
			}
			log.info("Available sizes for the style {} in CPD service are {}", cpdProd.getProductCode(), cpdSizesList);

			Prices[] cpdPrices = cpdProd.getPrices();// ---------Prices
			for (int i = 0; i < cpdPrices.length; i++) {
				if (priceListType.equals(cpdPrices[i].getPriceListType())) {
					cpdWhloleSalesPrice = (cpdPrices[i].getWholesale()) * 100;
					cpdRetailPrice = (cpdPrices[i].getRetail()) * 100;
				}
			}
			return cpdProd;
		} else {
			productdata.add(null);
			log.info("Style# {} is not available in CPD service, Needs to contact to upstream systems", styleColor);
			return null;
		}
		
	}

	private DbProduct getDbStyleData(String styleColor, String season, String region, String priceListType) {
		dbProduct = productTestDao.fetchDbProduct(styleColor, season, region, priceListType);
		if (dbProduct != null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			dbBeginOfferDate = df.format(dbProduct.getBeginOfferDate());// begin offer date
			dbEndOfferDate = df.format(dbProduct.getEndOfferDate());// end offer date
			dbRetailPrice = dbProduct.getRetailPrice();// Retail price
			dbWholeSalesPrice = dbProduct.getWholeSalesPrice();// wholesale price
			String json = dbProduct.getSizes();
			dbSizesList = new ArrayList<String>();
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				dbSizesList.add(item.getString("code"));
			}
			log.info("Available sizes for the style {} in DB are {}", dbProduct.getStyleColor(), dbSizesList);
			return dbProduct;
		} else {
			return null;
		}
	}
}
