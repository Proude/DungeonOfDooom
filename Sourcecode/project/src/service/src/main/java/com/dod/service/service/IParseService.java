package com.dod.service.service;

import com.dod.models.Map;
import org.json.simple.JSONObject;

/**
 *
 */
public interface IParseService {
    Map parseMap(JSONObject input) throws NullPointerException;
}
