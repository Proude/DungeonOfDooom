package com.dod.service.service;

import com.dod.game.IMatchList;
import com.dod.models.Character;
import com.dod.models.*;
import com.dod.service.model.CharacterModel;
import com.dod.service.model.GameStateModel;
import com.dod.service.model.TileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates a representation of the current game state form the perspective of a particular character
 */
public class StateService implements IStateService {

    IVisibilityService visibilityService;
    IMatchList matchList;

    public StateService(IVisibilityService visibilityService, IMatchList matchList) {
        this.visibilityService = visibilityService;
        this.matchList = matchList;
    }

    /**
     * Generates and returns a representation of the current game state form the perspective of a particular character
     * @param player Player the Player a GameStateModel will be generated for
     * @return GameStateModel a model of the current game state
     */
    @Override
    public GameStateModel GetState(Player player) {
        Match match = matchList.getMatchForPlayer(player.getUsername());
        Map map = visibilityService.createVisibleMap(match.getMap(), match.getCharacter(player.getUsername()));

        List<TileModel> tiles = new ArrayList();
        List<CharacterModel> characters = new ArrayList();

        for(int x = 0; x < map.getWidth(); x++) {
            for(int y = 0; y < map.getHeight(); y++) {
                Point point = new Point(x,y);
                Tile tile = map.getTile(point);

                if(tile.isVisible()) {
                    tiles.add(new TileModel(tile.getType(), point));
                    List<Character> charactersOnTile = match.getCharactersOnTile(point);
                    for(Character character : charactersOnTile) {
                        characters.add(new CharacterModel(
                                character.getPlayer().getUsername(),
                                character.getCollectedCoins(),
                                character.getPosition()));
                    }
                }
            }
        }

        Character character = match.getCharacter(player.getUsername());
        return new GameStateModel(tiles.toArray(
                new TileModel[tiles.size()]),
                characters.toArray(new CharacterModel[characters.size()]),
                new CharacterModel(
                        character.getPlayer().getUsername(),
                        character.getCollectedCoins(),
                        character.getPosition()),
                match.getState() == MatchState.Over,
                match.getMap().getCoinWin());
    }
}