package com.dod.bot;

import com.dod.models.Point;
import com.dod.service.model.TileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Map for the bot modeled on the responses from the server.
 * Should work in theory but not tested as we ran out of time.
 * </pre>
 */
public class Map {
    private ArrayList<List<TileModel>> map;

    public Map() {
        map = new ArrayList<List<TileModel>>();
    }

    /**
     * Add a tile to the map. Expands the map to the correct size of necessary.
     * @param tiles TileModel[] a collection of Tiles to add to the Map.
     */
    public void addTile(TileModel[] tiles) {
        int xMax = 0;
        int yMax = 0;

        for(TileModel tile : tiles) {
            if(tile.getPosition().x > xMax) xMax = tile.getPosition().x;
            if(tile.getPosition().y > yMax) yMax = tile.getPosition().y;
        }

        for(int x = 0; x < xMax; x++) {
            List<TileModel> row = map.get(x);
            if(row == null) {
                row = new ArrayList<TileModel>();
                map.add(row);
            }

            for(int y =0; y < yMax; y++) {
                TileModel tile = null;

                for(TileModel tileInput : tiles) {
                    if(tileInput.getPosition().equals(new Point(x,y))) {
                        tile = tileInput;
                        break;
                    }
                }

                //row.set(y, tile);
            }
        }
    }
}
