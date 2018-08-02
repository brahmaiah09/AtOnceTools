package com.atonce.tool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atonce.tool.model.cpd.CpdProduct;
import com.atonce.tool.service.ProductValidatorService;

@RestController
public class ProductTestController {
	
	@Autowired
	private ProductValidatorService productValidatorService;
	
	@RequestMapping("/get/{id}")
	@ResponseBody
	public List<CpdProduct> getProduct(@PathVariable String id, @RequestParam String season, @RequestParam String region, @RequestParam String priceListType )
	{
		List<CpdProduct> status=productValidatorService.productValidator(id, season, region, priceListType);
		return status;
	}
		
}
