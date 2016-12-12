package com.dod.service.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementation of IOService.
 */
public class IOService implements IIOService {

    private String pathToAssets = ".//assets";
    private JSONParser parser;

    public IOService(String pathToAssets) {
        this.pathToAssets = pathToAssets;
        parser = new JSONParser();
    }

    public IOService() {
        parser= new JSONParser();
    }

    @Override
    public String getString(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(pathToAssets + path));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    @Override
    public JSONObject getJsonObject(String path) throws IOException, ParseException {
        String input = getString(path);
        return (JSONObject) parser.parse(input);
    }
}
