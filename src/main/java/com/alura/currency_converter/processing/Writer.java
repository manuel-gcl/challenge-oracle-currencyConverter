package com.alura.currency_converter.processing;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * The Writer class provides functionality to write JSON data to a file.
 **/

public class Writer {
	public void writeToJson(final JsonObject jsonObject, final String fileName, final boolean prittyFormat)
			throws IOException {
		final String fileExtension = ".json";
		String filename = fileName;

		try {
			if (!filename.endsWith(fileExtension)) {
				filename += fileExtension;
			}
			final FileWriter writer = new FileWriter(filename);
			final Gson gson;
			if (prittyFormat) {
				gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setPrettyPrinting()
						.create();
			} else {
				gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
			}
			writer.write(gson.toJson(jsonObject));

			writer.close();
		} catch (IOException e) {
			throw new IOException("Error while writing " + filename + ".Error: " + e.getMessage(), e);
		}
	}
}