package com.atonce.tool.model.cpd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GlobalProducts {

	    private CpdProduct[] products;

	    public CpdProduct[] getProducts ()
	    {
	        return products;
	    }

	    public void setProducts (CpdProduct[] products)
	    {
	        this.products = products;
	    }

	    @Override
	    public String toString()
	    {
	        return "products = "+products;
	    }
	
}
