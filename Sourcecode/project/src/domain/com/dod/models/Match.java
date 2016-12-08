package com.dod.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a match
 */
public class Match {

    private UUID id;
    private Map map;
    private List<Character> characters;
    private MatchState state;

    public Match(Map map) {
        this.id = UUID.randomUUID();
        this.map = map;
        this.characters = new ArrayList();
        state = MatchState.Lobbying;
    }

    public Map getMap() {
        return map;
    }

    public void addCharacter(Player player, Point position) {
        characters.add(new Character(position, player));
    }

    public void removeCharacter(Player player) {
        for(Character character : characters) {
            if(character.getPlayer().getUsername() == player.getUsername()) {
                characters.remove(character);
                break;
            }
        }
    }

    public Character getCharacter(String username) {
        Character result = null;

        for(Character character : characters) {
            if(character.getPlayer().getUsername() == username) {
                result = character;
                break;
            }
        }

        return result;
    }

    public String[] getPlayerNames() {
        String[] names = new String[characters.size()];

        for(int i = 0; i < characters.size(); i++) {
            names[i] = characters.get(i).getPlayer().getUsername();
        }

        return names;
    }

    public boolean hasCharacter(String userName) {
        return getCharacter(userName) != null;
    }

    public UUID getId() {
        return id;
    }

    public void startGame() {
        state = MatchState.Ingame;
    }

    public MatchState getState() {
        return state;
    }

    public void setState(MatchState state) {
        this.state = state;
    }
}
