package com.atonce.tool.model.db;

import java.util.Date;

public class DbProduct {
	private String styleColor;
	private String season;
    private String rgn;
    private String sizes;
    private String priceListType;
    private Date beginOfferDate;
    private Date endOfferDate;
    private int wholeSalesPrice;
    private int retailPrice;
	public String getSizes() {
		return sizes;
	}
	public void setSizes(String sizes) {
		this.sizes = sizes;
	}
	public String getStyleColor() {
		return styleColor;
	}
	public void setStyleColor(String styleColor) {
		this.styleColor = styleColor;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public String getRgn() {
		return rgn;
	}
	public void setRgn(String rgn) {
		this.rgn = rgn;
	}
	public String getPriceListType() {
		return priceListType;
	}
	public void setPriceListType(String priceListType) {
		this.priceListType = priceListType;
	}
	public Date getBeginOfferDate() {
		return beginOfferDate;
	}
	public void setBeginOfferDate(Date beginOfferDate) {
		this.beginOfferDate = beginOfferDate;
	}
	public Date getEndOfferDate() {
		return endOfferDate;
	}
	public void setEndOfferDate(Date endOfferDate) {
		this.endOfferDate = endOfferDate;
	}
	public int getWholeSalesPrice() {
		return wholeSalesPrice;
	}
	public void setWholeSalesPrice(int wholeSalesPrice) {
		this.wholeSalesPrice = wholeSalesPrice;
	}
	public int getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(int retailPrice) {
		this.retailPrice = retailPrice;
	}
	@Override
	public String toString() {
		return "DbProduct [styleColor=" + styleColor + ", season=" + season + ", rgn=" + rgn + ", priceListType="
				+ priceListType + ", beginOfferDate=" + beginOfferDate + ", endOfferDate=" + endOfferDate
				+ ", wholeSalesPrice=" + wholeSalesPrice + ", retailPrice=" + retailPrice + ", sizes=\" + sizes + \"]";
	}
	
}
