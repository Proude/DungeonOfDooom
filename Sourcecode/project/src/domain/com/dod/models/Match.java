package com.dod.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <pre>
 *     A Match represents a particular collection of Players that are playing on a particular Map stored in memory
 *     A Match has a Map
 *     A Match has a unique ID
 *     A Match
 * </pre>
 */
public class Match {

    private UUID id;
    private Map map;
    private List<Character> characters;
    private MatchState state;
    private long timer;
    private int score;

    public Match(Map map) {
        this.id = UUID.randomUUID();
        this.map = map;
        this.characters = new ArrayList();
        state = MatchState.Lobbying;
        timer = 0;
        score = 0;
    }


    public Map getMap() {
        return map;
    }

    /**
     * Adds a Player to this Match with a new Character
     * @param player Player the Player who will join this Match as a Character
     * @param position Point the position the new Character will occupy
     */
    public void addCharacter(Player player, Point position) {
        characters.add(new Character(position, player));
    }

    public void removeCharacter(Player player) {
        for(Character character : characters) {
            if(character.getPlayer().getUsername().equals(player.getUsername())) {
                characters.remove(character);
                break;
            }
        }
    }


    public Character getCharacter(String username) {
        Character result = null;

        for(Character character : characters) {
            if(character.getPlayer().getUsername().equals(username)) {
                result = character;
                break;
            }
        }

        return result;
    }

    /**
     * Gets a list of names of each Player currently in this Match
     * @return String[] array of players names
     */
    public String[] getPlayerNames() {
        String[] names = new String[characters.size()];

        for(int i = 0; i < characters.size(); i++) {
            names[i] = characters.get(i).getPlayer().getUsername();
        }

        return names;
    }

    /**
     * Returns where or not a character is in this Match
     * @param userName String the name of the Player to check
     * @return boolean true if the Player is in this Match otherwise false
     */
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

    /**
     * Returns all Characters on a particular Tile
     * @param point Point the location of the Tile to check
     * @return List\<Character\> a list of Characters that are presently standing on that tile
     */
    public List<Character> getCharactersOnTile(Point point) {
        List<Character> charactersOnTile = new ArrayList();

        for(Character character :characters) {
            if(character.getPosition().equals(point)) {
                charactersOnTile.add(character);
            }
        }

        return charactersOnTile;
    }

    /**
     * Gets the Caracter with the highest score
     * @return Character with the highest score
     */
    public Character getCharacterWithHighestCoins() {
        Character character = null;

        for(Character c : characters) {
            if(character == null || c.getCollectedCoins() > character.getCollectedCoins()) {
                character = c;
            }
        }

        return character;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public int getScore() { return this.score; }

    public void setScore(int score) { this.score = score; }
}
