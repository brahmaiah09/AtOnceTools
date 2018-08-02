package com.atonce.tool.model.cpd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Prices
{
    private String priceListType;

    private int wholesale;

    private int retail;

	public String getPriceListType() {
		return priceListType;
	}

	public void setPriceListType(String priceListType) {
		this.priceListType = priceListType;
	}

	public int getWholesale() {
		return wholesale;
	}

	public void setWholesale(int wholesale) {
		this.wholesale = wholesale;
	}

	public int getRetail() {
		return retail;
	}

	public void setRetail(int retail) {
		this.retail = retail;
	}

	@Override
	public String toString() {
		return "Prices [priceListType=" + priceListType + ", wholesale=" + wholesale + ", retail=" + retail + "]";
	}

	


}
