package com.alura.currency_converter.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.alura.currency_converter.processing.CurrencyOperation;

/**
 * Utility class providing static methods to handle currency operation data.
 *
 * This class includes methods for: - Retrieving the maximum operation ID from a
 * list of CurrencyOperation objects. - Converting JSON objects to lists of
 * CurrencyOperation instances. - Updating JSON objects with new or modified
 * lists of operations. - Formatting dictionaries (maps) for display purposes.
 */

public final class CurrencyIOUtils {
	public static int getMaxOperationID(final List<CurrencyOperation> currencyOpsList) {
		int maxID = 0;
		for (final CurrencyOperation currencyOp : currencyOpsList) {
			if (currencyOp.getOperationID() > maxID) {
				maxID = currencyOp.getOperationID();
			}
		}
		return maxID;
	}

	public static List<CurrencyOperation> getOperationsListFromJsonObj(final JsonObject jsonObject,
			final String listKey) {
		Gson gson = new Gson();
		List<CurrencyOperation> currencyOps = new ArrayList<>();
		if (jsonObject.has(listKey)) {
			final JsonArray operationsArray = jsonObject.getAsJsonArray(listKey);
			for (final JsonElement element : operationsArray) {
				final CurrencyOperation currOp = gson.fromJson(element, CurrencyOperation.class);
				currencyOps.add(currOp);
			}
		}

		return currencyOps;
	}

	public static JsonObject updateOperationsList(final JsonObject jsonObject, final String listKey,
			final List<CurrencyOperation> newOperations) {
		final Gson gson = new Gson();

		jsonObject.add(listKey, gson.toJsonTree(newOperations).getAsJsonArray());

		return jsonObject;
	}

	public static String getFormattedDict(final Map<String, ?> dict) {
		final StringBuilder str = new StringBuilder();
		if (dict != null) {
			for (final Map.Entry<String, ?> entry : dict.entrySet()) {
				str.append("\n\t\t").append(entry.getKey()).append(": ").append(entry.getValue());
			}
		} else {
			str.append("{}");
		}
		
		return str.toString();
	}
}
