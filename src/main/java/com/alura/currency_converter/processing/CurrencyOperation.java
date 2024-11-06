package com.alura.currency_converter.processing;

import com.alura.currency_converter.exceptions.APIException;
import com.alura.currency_converter.models.Currency;
import com.alura.currency_converter.models.ExchangeRateResponse;
import com.alura.currency_converter.services.ExchangeRateApiService;

/**
 * Class that represents a currency conversion operation. It includes methods to
 * set source and target currencies, process API responses, and retrieve
 * conversion information.
 */

public class CurrencyOperation {
	private int operationID;
	private String opStatus;
	private Currency sourceCurrency;
	private Currency targetCurrency;
	private transient ExchangeRateApiService apiSearcher;

	public CurrencyOperation(final int operationId, final ExchangeRateApiService apiSearcher) {
		sourceCurrency = new Currency();
		this.operationID = operationId;
		this.apiSearcher = apiSearcher;
	}

	public void setSourceCurrency(final String sourceCurrencyCode, final double currencyAmount) {
		sourceCurrency.setBaseCode(sourceCurrencyCode);
		sourceCurrency.setAmount(currencyAmount);
	}

	public Currency getSourceCurrency() {
		return sourceCurrency;
	}

	public void setTargetCurrency() {
		targetCurrency = new Currency();
	}

	public void setTargetCurrency(final String targetCurrencyCode, final double currencyAmount) {
		targetCurrency = new Currency();
		targetCurrency.setBaseCode(targetCurrencyCode);
		targetCurrency.setAmount(currencyAmount);
	}

	public Currency getTargetCurrency() {
		return targetCurrency;
	}

	public int getOperationID() {
		return operationID;
	}

	public void processApiResponse(final ExchangeRateResponse jsonResponse, final boolean isOneToOne) {
		setOpStatus(jsonResponse.result());
		if (isOneToOne) {
			targetCurrency.setBaseCode(jsonResponse.target_code());
			targetCurrency.setAmount(jsonResponse.conversion_result());
		} else {
			sourceCurrency.setConversionRates(jsonResponse.conversion_rates());
		}
	}

	public ExchangeRateResponse getOneToOneConvertionInformation() throws APIException {
		final String url;
		if (sourceCurrency.getAmount() != 0) {
			url = apiSearcher.getUrl(sourceCurrency.getBaseCode(), targetCurrency.getBaseCode(),
					sourceCurrency.getAmount());
		} else {
			url = apiSearcher.getUrl(sourceCurrency.getBaseCode(), targetCurrency.getBaseCode());
		}

		return apiSearcher.getApiResponse(url);
	}

	public ExchangeRateResponse getOneToManyConvertionInformation() throws APIException {
		return apiSearcher.getApiResponse(apiSearcher.getUrl(sourceCurrency.getBaseCode()));
	}

	public String getOpStatus() {
		return opStatus;
	}

	public void setOpStatus(final String opStatus) {
		this.opStatus = opStatus;
	}

	@Override
	public String toString() {
		final StringBuilder str = new StringBuilder(80);
		str.append("Operation ID: ").append(operationID).append("\nOperation Status: ").append(opStatus)
				.append("\nSource Currency: \n").append(sourceCurrency);

		if (targetCurrency != null) {
			str.append("Target Currency: \n").append(targetCurrency).append('\n');
		}

		return str.toString();
	}
}