package com.atonce.tool.model.cpd;

public class RegionalOfferDates
{
    private String firstProduct;

    private String endProduct;

    public String getFirstProduct ()
    {
        return firstProduct;
    }

    public void setFirstProduct (String firstProduct)
    {
        this.firstProduct = firstProduct;
    }

    public String getEndProduct ()
    {
        return endProduct;
    }

    public void setEndProduct (String endProduct)
    {
        this.endProduct = endProduct;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [firstProduct = "+firstProduct+", endProduct = "+endProduct+"]";
    }
}
