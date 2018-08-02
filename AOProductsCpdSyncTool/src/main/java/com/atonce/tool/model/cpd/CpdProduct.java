package com.atonce.tool.model.cpd;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CpdProduct
{
	private String productCode;
	private Sizes[] sizes;
    private RegionalOfferDates regionalOfferDates;
    private String lifecycle;
    private String season;
    private String region;
    private String sourceStatusCode;
    private String sourceDeletionIndicator;
    private Prices[] prices;
    
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Sizes[] getSizes() {
		return sizes;
	}
	public void setSizes(Sizes[] sizes) {
		this.sizes = sizes;
	}
	public RegionalOfferDates getRegionalOfferDates() {
		return regionalOfferDates;
	}
	public void setRegionalOfferDates(RegionalOfferDates regionalOfferDates) {
		this.regionalOfferDates = regionalOfferDates;
	}
	public String getLifecycle() {
		return lifecycle;
	}
	public void setLifecycle(String lifecycle) {
		this.lifecycle = lifecycle;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSourceStatusCode() {
		return sourceStatusCode;
	}
	public void setSourceStatusCode(String sourceStatusCode) {
		this.sourceStatusCode = sourceStatusCode;
	}
	public String getSourceDeletionIndicator() {
		return sourceDeletionIndicator;
	}
	public void setSourceDeletionIndicator(String sourceDeletionIndicator) {
		this.sourceDeletionIndicator = sourceDeletionIndicator;
	}
	public Prices[] getPrices() {
		return prices;
	}
	public void setPrices(Prices[] prices) {
		this.prices = prices;
	}
	@Override
	public String toString() {
		return "productCode=" + productCode + ", sizes=" + Arrays.toString(sizes) + ", regionalOfferDates="
				+ regionalOfferDates + ", lifecycle=" + lifecycle + ", season=" + season + ", region=" + region
				+ ", sourceStatusCode=" + sourceStatusCode + ", sourceDeletionIndicator=" + sourceDeletionIndicator
				+ ", prices=" + Arrays.toString(prices);
	}

}
