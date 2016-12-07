package com.dod.service.service;

import com.dod.models.Map;
import com.dod.models.Point;
import com.dod.models.Tile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

/**
 * Implementation of IParseService.
 */
public class ParseService implements IParseService {

    @Override
    public Map parseMap(JSONObject input) throws NullPointerException {
        JSONObject level = getLevel(input);
        JSONArray rowsOfTiles = (JSONArray) level.get("map");
        int xSize = ((JSONArray) rowsOfTiles.get(0)).size();
        int ySize = rowsOfTiles.size();

        Map map = new Map(
                (String) level.get("name"),
                ((Long) (level.get("coin_num"))).intValue(),
                ((Long) (level.get("coin_win"))).intValue(),
                ((Long) (level.get("Width"))).intValue(),
                ((Long) (level.get("Height"))).intValue(),
                new Point(xSize, ySize));

        for (int y = 0; y < ySize; y++) {
            JSONArray row = (JSONArray) rowsOfTiles.get(y);
            for (int x = 0; x < xSize; x++) {
                JSONObject tile = (JSONObject) row.get(x);
                map.setTile(new Point(x, y), new Tile(((Long) tile.get("type")).intValue()));
            }
        }

        return map;
    }

    private JSONObject getLevel(JSONObject input) {
        Iterator<String> keys = input.keySet().iterator();
        String levelKey = keys.hasNext() ? keys.next() : "";

        return (JSONObject)input.get(levelKey);
    }
}
