package com.alura.currency_converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.alura.currency_converter.exceptions.APIException;
import com.alura.currency_converter.models.Currency;
import com.alura.currency_converter.processing.CurrencyOperation;
import com.alura.currency_converter.processing.InputHandler;
import com.alura.currency_converter.processing.Reader;
import com.alura.currency_converter.processing.UserChoice;
import com.alura.currency_converter.processing.Writer;
import com.alura.currency_converter.services.ExchangeRateApiService;
import com.alura.currency_converter.utilities.CurrencyIOUtils;

/**
 * Main class for the Currency Converter application. This application allows
 * users to convert currencies using real-time exchange rates fetched from an
 * external API.
 *
 * Features: - Convert from one currency to another - Convert from one currency
 * to all API's supported currencies. - View the history of past currency
 * conversions. - Save the conversion history to a JSON file on program exit.
 *
 * Usage: Run the program and follow the menu prompts for different currency
 * conversion options.
 **/

public class CurrencyConverterApp {

	public static void main(String[] args) throws IOException, APIException {
		final String operationsRecord = "src/main/resources/data/operations_record.json";
		final String operationListKey = "operations";
		final List<CurrencyOperation> opsList;
		final Reader reader = new Reader();
		final ExchangeRateApiService apiSearcher = new ExchangeRateApiService();
		final Map<String, String> currencyCodesMap = apiSearcher.getAcceptedCurrencyCodes();
		final InputHandler inputHandler = new InputHandler(currencyCodesMap);

		int operationId;
		Currency sourceCurrency;

		System.out.println("""
				********************************
				*****Currency Converter App*****
				********************************
				""");

		final JsonObject jsonContent = reader.getJsonContent(operationsRecord);

		if (jsonContent.isEmpty() || !jsonContent.has(operationListKey)) {
			opsList = new ArrayList<>();
			final Gson gson = new Gson();
			jsonContent.addProperty(operationListKey, gson.toJson(opsList));
		} else {
			opsList = CurrencyIOUtils.getOperationsListFromJsonObj(jsonContent, operationListKey);
		}

		operationId = CurrencyIOUtils.getMaxOperationID(opsList);

		while (true) {
			final UserChoice userChoice = inputHandler.getUserChoice();

			if (userChoice == UserChoice.EXIT) {
				break;
			}
			
			if (userChoice != UserChoice.VIEW_HISTORY){
				operationId++;
			}
			
			final CurrencyOperation operation = new CurrencyOperation(operationId, apiSearcher);
			sourceCurrency = operation.getSourceCurrency();

			if (userChoice == UserChoice.ONE_TO_ONE || userChoice == UserChoice.ONE_TO_MANY) {
				sourceCurrency.setBaseCode(inputHandler.getCurrencyCodeInput());
			}

			switch (userChoice) {
			case ONE_TO_ONE:
				sourceCurrency.setAmount(inputHandler.getCurrencyAmountInput());
				operation.setTargetCurrency();
				operation.getTargetCurrency().setBaseCode(inputHandler.getCurrencyCodeInput());
				operation.processApiResponse(operation.getOneToOneConvertionInformation(), true);
				opsList.add(operation);
				System.out.println(operation);
				break;
			case ONE_TO_MANY:
				operation.processApiResponse(operation.getOneToManyConvertionInformation(), false);
				opsList.add(operation);
				System.out.println(operation);
				break;
			case VIEW_HISTORY:
				System.out.println("""
						*******************************
						**Currency Operations History**
						*******************************
						""");
				System.out.println(opsList);
				break;
			default:
				break;
			}
		}
		CurrencyIOUtils.updateOperationsList(jsonContent, operationListKey, opsList);

		try {
			final Writer writer = new Writer();
			writer.writeToJson(jsonContent, operationsRecord, true);
			System.out.println("\nOperations record was succesfully saved\n");
		} catch (IOException e) {
			System.out.println("Operations record file wasn't save.\nError:" + e.getMessage());
		}

		System.out.println("""
				*******************************
				*********Program Ended*********
				*******************************
				""");
	}
}
