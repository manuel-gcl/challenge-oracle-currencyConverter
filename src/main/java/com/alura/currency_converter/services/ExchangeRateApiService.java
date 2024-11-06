package com.alura.currency_converter.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import com.alura.currency_converter.models.ExchangeRateResponse;
import com.alura.currency_converter.exceptions.APIResponseParserError;
import com.alura.currency_converter.exceptions.APIConectionError;
import com.alura.currency_converter.exceptions.APIException;
import com.alura.currency_converter.exceptions.InvalidAPIKeyException;

/**
 * The class provides methods to interact with an external exchange rate API. It
 * facilitates fetching accepted currencies and provides utility methods to
 * construct API request URLs.
 **/

public class ExchangeRateApiService {
	private final static String PAIR_CONV = "/pair/";
	private final static String TO_MANY_CONV = "/latest/";
	private final static String URL_FILE_KEY = "exchangeRate.url";
	private final static String API_FILE_KEY = "exchangeRate.key";
	private final static String PROPERTY_FILE = "config.properties";
	private String url;

	public ExchangeRateApiService() throws IOException, APIException {

		final Properties properties = new Properties();
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE)) {

			if (inputStream == null) {
				throw new FileNotFoundException("config.properties file not found in resources.");
			}
			properties.load(inputStream);

			url = properties.getProperty(URL_FILE_KEY);
			final String apiKey = properties.getProperty(API_FILE_KEY);

			if (url == null || apiKey == null) {
				throw new IllegalArgumentException("URL or API Key are undefined in config.properties file.");
			}

			url += apiKey;

		} catch (IOException e) {
			throw new IOException("Can't load config.properties file: " + e.getMessage(), e);
		}
	}

	public String getUrl(final String sourceCode) {
		return String.format("%s%s%s", url, TO_MANY_CONV, sourceCode);
	}
	
	public String getUrl(final String sourceCode, final String targetCode) {
		return String.format("%s%s%s/%s", url, PAIR_CONV, sourceCode, targetCode);
	}

	public String getUrl(final String sourceCode, final String targetCode, final Double sourceAmount) {
		final String amountString = new BigDecimal(sourceAmount.toString()).toPlainString();

		return String.format("%s%s%s/%s/%s", url, PAIR_CONV, sourceCode, targetCode, amountString);
	}

	public ExchangeRateResponse getApiResponse(final String url) throws APIException {
		final HttpClient client = HttpClient.newHttpClient();
		final URI address = URI.create(url);
		final HttpRequest request = HttpRequest.newBuilder().uri(address).build();
		final HttpResponse<String> response;
		final List<String> apiKeyErrorCodes = new ArrayList<>(List.of("inactive-account", "invalid-key"));

		try {
			response = client.send(request, BodyHandlers.ofString());
			client.close();
		} catch (IOException | InterruptedException e) {
			throw new APIException("Can't get response from API, error: ", e);
		}

		final int validResponse = 200;
		if (response.statusCode() != validResponse) {
			throw new APIConectionError("Failed to conect to API. Response code: " + response.statusCode());
		}

		final JsonObject jsonResponse;
		try {
			jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);
		} catch (JsonParseException e) {
			throw new APIResponseParserError("Failed to parse the API response: ", e);
		}

		// Check for specific API key error codes
		if (jsonResponse.has("error-type")) {
			final String errorType = jsonResponse.get("error-type").getAsString();
			if (apiKeyErrorCodes.contains(errorType)) {
				throw new InvalidAPIKeyException("API Key Error: " + errorType + "Check config.properties file data\n");
			}
		}
		
		return new Gson().fromJson(response.body(), ExchangeRateResponse.class);
	}

	public Map<String, String> getAcceptedCurrencyCodes() throws APIException {
		final String codesStr = "/codes";
		final ExchangeRateResponse response = getApiResponse(url.concat(codesStr));

		return response.supported_codes();
	}
}
