package com.alura.currency_converter.models;

import java.util.Map;

/**
 * This record is used to parse and store API response data for use within the
 * application.
 **/

public record ExchangeRateResponse(String result, String base_code, String target_code, double conversion_rate,
		double conversion_result, Map<String, Double> conversion_rates, Map<String, String> supported_codes) {
}
