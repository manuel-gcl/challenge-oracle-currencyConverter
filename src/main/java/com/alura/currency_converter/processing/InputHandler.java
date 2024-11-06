package com.alura.currency_converter.processing;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import com.alura.currency_converter.utilities.CurrencyIOUtils;

/**
 * This class provides methods to prompt the user for program choices, currency
 * codes,and amounts, ensuring that the inputs are valid and conform to expected
 * formats.
 **/

public class InputHandler {
	private final Scanner inputScanner = new Scanner(System.in);
	private Map<String, String> currencyCodesMap = new HashMap<String, String>();

	public InputHandler(final Map<String, String> currencyCodesMap) {
		this.currencyCodesMap = currencyCodesMap;
	}

	public UserChoice getUserChoice() {
		final String errorMessage = "Invalid Input\nChoice must be a number in [1, 4] range";
		UserChoice userChoice = null;

		while (userChoice == null) {
			System.out.println("""
					********************************
					Please select an option:
					1. One-to-One Conversion
					2. One-to-Many Conversion
					3. View Conversion History
					4. Exit
					********************************
					""");
			try {
				final int userInput = inputScanner.nextInt();
				inputScanner.nextLine();
				switch (userInput) {
				case 1:
					userChoice = UserChoice.ONE_TO_ONE;
					break;
				case 2:
					userChoice = UserChoice.ONE_TO_MANY;
					break;
				case 3:
					userChoice = UserChoice.VIEW_HISTORY;
					break;
				case 4:
					userChoice = UserChoice.EXIT;
					break;
				default:
					System.out.println(errorMessage);
				}
			} catch (InputMismatchException e) {
				System.out.println(errorMessage);
				inputScanner.nextLine();
			}
		}

		return userChoice;
	}

	public String getCurrencyCodeInput() {
		String baseCode;

		while (true) {
			System.out.println("Input 3 letters currency base code (eg: 'USD'):");
			baseCode = inputScanner.nextLine().toUpperCase(Locale.ENGLISH);
			if (!currencyCodesMap.containsKey(baseCode)) {
				System.out.println("\nInvalid currency base code, try again:");
				System.out.println(CurrencyIOUtils.getFormattedDict(currencyCodesMap));
			} else {
				break;
			}
		}

		return baseCode;
	}

	public double getCurrencyAmountInput() {
		double amount;
		final String invalidNumMessage = "Invalid currency amount. Please enter a positive numerical value (e.g., 10.5).";

		while (true) {
			try {
				System.out.println("Input currency amount:");
				amount = inputScanner.nextDouble();
				inputScanner.nextLine();
				if (amount < 0) {
					System.out.println(invalidNumMessage);
				} else {
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println(invalidNumMessage);
				inputScanner.nextLine();
			}
		}

		return amount;
	}
}
