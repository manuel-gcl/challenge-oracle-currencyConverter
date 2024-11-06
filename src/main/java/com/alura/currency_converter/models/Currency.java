package com.alura.currency_converter.models;

import java.util.Map;

import com.alura.currency_converter.utilities.CurrencyIOUtils;

/**
 * Represents a currency with its base code, amount, and conversion rates.
**/

public class Currency {
	private String baseCode;
	private double amount=0;
	private Map<String, Double> conversionRates;
	
	
	public String getBaseCode() {
		return baseCode;
	}
	
	public void setBaseCode(final String code) {
		this.baseCode = code;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(final double amount) {
		this.amount = amount;
	}
	
	 public Map<String, Double> getConversionRates() { 
		 return conversionRates; 
	}
	 
	public void setConversionRates(final Map<String, Double> conversionRates) { 
		this.conversionRates = conversionRates; 
	}
	
	@Override
	public String toString() {
		final StringBuilder str = new StringBuilder(50);
		str.append("\tBase Code: ").append(baseCode).append('\n');
		if (amount > 0) {
			str.append("\tAmount: ").append(amount).append('\n');
		}
		if (conversionRates != null) {
			str.append("\tConversion Rates:").append(CurrencyIOUtils.getFormattedDict(conversionRates)).append('\n');
		}

		return str.toString();
	}
}
