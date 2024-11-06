package com.alura.currency_converter.processing;

import java.io.IOException;
import java.io.File;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * This class provides methods to read a JSON file and parse its content into a
 * JsonObject using the Gson library.
 **/

public class Reader {
	public JsonObject getJsonContent(final String filepath) throws IOException {
		final Gson gson = new Gson();
		final File fileObj = new File(filepath);
		JsonObject jsonObject = new JsonObject();
		if (fileObj.exists()) {
			try (FileReader reader = new FileReader(fileObj)) {
				jsonObject = gson.fromJson(reader, JsonObject.class);
				if (jsonObject == null) {
					jsonObject = new JsonObject();
				}
			} catch (IOException e) {
				throw new IOException("Can't read file, error: " + e.getMessage(), e);
			} catch (JsonSyntaxException e) {
				throw new JsonSyntaxException("Invalid file content, error: " + e.getMessage(), e);
			}
		}

		return jsonObject;
	}
}