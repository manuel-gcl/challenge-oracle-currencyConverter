
# Currency Converter App

This project was developed for **Java backend specialization** from **Alura's Oracle One** program.
The Currency Converter App consist in a command-line Java application that allows users to perform currency conversions in real-time using an external API.   
Users can choose between converting a specific currency amount to a single target currency or to get exchange rate for multiple currencies at once.  
The main goal of this project was learning different aspects of Java programming including API integration, managing JSON data, and implementing organized code structure.

## Installation and Setup
To set up and use the Currency Converter App, follow these steps:

1. **Clone the Repository:**
Clone the repository to your local machine using Git or download the ZIP file.
```console
git clone <repository-url>
```
2. Configure Dependencies with Maven
This project uses Maven to manage dependencies and build the application.

Gson: Gson is already configured as a dependency in the pom.xml file.
To make sure all dependencies are installed, navigate to the project root and run:

```console
mvn clean install
```

3. **Obtain an API Key:**
To use the exchange rate API, you must obtain an API key from [ExchangeRate-API](https://app.exchangerate-api.com/sign-up).  
Follow these steps:
- Visit ExchangeRate-API to sign up.
- After signing up, a free API key will be sent to your email.
4. **Configure API Key:**
Once you have your API key, you need to configure it for the app to use:

- Navigate to the src/main/resources folder in your project directory.

- Locate an already existing file named config.properties.

- ***Replace YOUR_API_KEY with the actual key***:
```
exchangeRate.key=YOUR_API_KEY
```
5. **Build and Run the Application with Maven:**
After setting up the API key, you can build and run the application using Maven.
```
mvn package

```
6. **Run the application:**
```
mvn exec:java -Dexec.mainClass="com.alura.currency_converter.CurrencyConverterApp"

```

## Usage:
```
********************************
Please select an option:
1. One-to-One Conversion
2. One-to-Many Conversion
3. View Conversion History
4. Exit
********************************
```
   
1. **One-to-One Conversion**  
Select 1 to perform a currency conversion from one base currency to a single target currency.
You will be asked to input the base currency code (e.g., USD, EUR).
Then, input the amount to convert.   
Finally, choose the target currency code.   
The application will fetch the latest exchange rate and display the operation result.   
2. **One-to-Many Conversion**  
Select 2 to get exchange rates from a base currency to multiple target currencies at once.
You will be prompted to enter the base currency code.   
The application will display the operation information with all exchange rates of supported currencies.   
3. **View Conversion History**  
Select 3 to view the history of all previously made currency conversions.
The history will include the details of each operation performed, such as the currencies involved and the conversion amounts or exchage rates.   
4. **Exit**  
Select 4 to exit the application.

The app will continue running until you choose to exit. If you need to start a new conversion or view the history, simply select the corresponding option again.
