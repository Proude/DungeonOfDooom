package com.dod.service.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Handles IO within the Service
 */
public class IOService implements IIOService {

    private String pathToAssets = "..//..//assets";
    private JSONParser parser;

    public IOService(String pathToAssets) {
        this.pathToAssets = pathToAssets;
        parser = new JSONParser();
    }

    public IOService() {
        parser= new JSONParser();
    }

    /**
     * Fetches an asset as a String
     * @param path String the path to the asset we are to fetch
     * @return String the contents of the asset
     * @throws IOException if the file is missing
     */
    @Override
    public String getString(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(pathToAssets + path));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    /**
     * Fetches an asset as parsed JSON
     * @param path String the path to the asset we are to fetch
     * @return JSONObject the parsed content of the asset
     * @throws IOException if the file is missing
     * @throws ParseException if the file isn't encoded in valid JSON
     */
    @Override
    public JSONObject getJsonObject(String path) throws IOException, ParseException {
        String input = getString(path);
        return (JSONObject) parser.parse(input);
    }
}
