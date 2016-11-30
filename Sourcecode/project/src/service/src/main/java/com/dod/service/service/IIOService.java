package com.dod.service.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 *
 */
public interface IIOService {
    String getString(String path) throws IOException;
    JSONObject getJsonObject(String path) throws IOException, ParseException;
}
