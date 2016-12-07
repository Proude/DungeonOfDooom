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

    public Match(Map map) {
        this.id = UUID.randomUUID();
        this.map = map;
        this.characters = new ArrayList();
    }

    public Map getMap() {
        return map;
    }

    public void addCharacter(Player player, Point position) {
        characters.add(new Character(position, player));
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

    public boolean hasCharacter(String userName) {
        return getCharacter(userName) != null;
    }

    public UUID getId() {
        return id;
    }
}
