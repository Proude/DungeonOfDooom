package com.dod.service.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Handles IO within the Service
 */
public interface IIOService {
    /**
     * Fetches an asset as a String
     * @param path String the path to the asset we are to fetch
     * @return String the contents of the asset
     * @throws IOException if the file is missing
     */
    String getString(String path) throws IOException;

    /**
     * Fetches an asset as parsed JSON
     * @param path String the path to the asset we are to fetch
     * @return JSONObject the parsed content of the asset
     * @throws IOException if the file is missing
     * @throws ParseException if the file isn't encoded in valid JSON
     */
    JSONObject getJsonObject(String path) throws IOException, ParseException;
}
