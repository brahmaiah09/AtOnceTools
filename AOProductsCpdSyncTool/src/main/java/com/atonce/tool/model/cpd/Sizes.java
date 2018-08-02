package com.atonce.tool.model.cpd;

public class Sizes
{
    private String promoIndicator;

    private String sizeDescription;

	public String getPromoIndicator() {
		return promoIndicator;
	}

	public void setPromoIndicator(String promoIndicator) {
		this.promoIndicator = promoIndicator;
	}

	public String getSizeDescription() {
		return sizeDescription;
	}

	public void setSizeDescription(String sizeDescription) {
		this.sizeDescription = sizeDescription;
	}

	@Override
	public String toString() {
		return "Sizes [promoIndicator=" + promoIndicator + ", sizeDescription=" + sizeDescription + "]";
	}

}
