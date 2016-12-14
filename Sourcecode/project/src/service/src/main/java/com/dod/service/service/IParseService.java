package com.dod.service.service;

import com.dod.models.Map;
import org.json.simple.JSONObject;

/**
 * Parses JSON objects- namely the Map
 */
public interface IParseService {
    /**
     * Parses a Map object from it's JSON encoding
     * @param input JSONObject a JSON encoding of the Map
     * @return Map an initialised Map parsed from JSON
     * @throws NullPointerException may be thrown by SimpleJson while parsing
     */
    Map parseMap(JSONObject input) throws NullPointerException;
}
