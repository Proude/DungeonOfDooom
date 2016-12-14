package com.dod.service.service;

import com.dod.db.repositories.IPlayerRepository;
import com.dod.game.IMatchList;
import com.dod.models.*;
import com.dod.models.Character;
import com.dod.service.constant.Assets;
import com.dod.service.model.MatchResultModel;
import com.dod.service.model.MatchStatus;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <pre>
 * Manages joining/starting/ending matches.
 * Makes heavy use of MatchList to store matches in memory.
 * Uses PlayerRepository to fetch Player data.
 * Uses IOService and ParseService to load levels when starting a new Match.
 * </pre>
 */
public class MatchService implements IMatchService {

    private IIOService ioService;
    private IParseService parseService;
    private IPlayerRepository playerRepository;
    private IMatchList matchList;

    public MatchService(IIOService ioService, IParseService parseService, IPlayerRepository playerRepository, IMatchList matchList) {
        this.ioService = ioService;
        this.parseService = parseService;
        this.playerRepository = playerRepository;
        this.matchList = matchList;
    }

    /**
     * Creates a new Match
     * @param userName String username of the Player who is starting the Match
     * @param level int the number of the level to load for this Match
     * @return MatchStatus of the newly created Match
     */
    @Override
    public MatchStatus createMatch(String userName, int level) {
        Map map = null;
        Player player;

        try {
            String path = String.format(Assets.MapLevelFormat, Integer.toString(level));
            map = parseService.parseMap(ioService.getJsonObject(path));
            player = playerRepository.get(new Player(userName));
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        Match match = new Match(map);
        match.addCharacter(player, map.getRandomFreeTilePoint());
        for(int i = 0; i < map.getCoinNo(); i++) {
            map.getTile(map.getRandomFreeTilePoint()).setType(TileType.Coin.getValue());
        }
        matchList.addMatch(match);

        return new MatchStatus(match);
    }

    /**
     * Changes a Match's state to InGame
     * @param player Player whose ongoing Match will be modified
     */
    @Override
    public void startMatch(Player player) {
        Match match = matchList.getMatchForPlayer(player.getUsername());
        Date temp = new Date();
        match.setTimer(temp.getTime());
        match.setState(MatchState.Ingame);
    }

    /**
     * Returns the MatchStatus for a particular Player's Match
     * @param player Player whose ongoing Match will be fetched
     * @return
     */
    @Override
    public MatchStatus getStatus(Player player) {
        if(!matchList.playerHasMatch(player.getUsername())) {
            return null;
        } else {
            Match match = matchList.getMatchForPlayer(player.getUsername());
            return new MatchStatus(match);
        }
    }

    /**
     * Removes a Player from their current ongoing Match
     * @param player Player the Player whom will be removed from their ongoing Match
     */
    @Override
    public void leaveMatch(Player player) {
        Match match = matchList.getMatchForPlayer(player.getUsername());

        match.removeCharacter(player);
    }

    /**
     * Changes a Match's state to Over
     * @param player Player whose ongoing Match will be modified
     */
    @Override
    public void endMatch(Player player) {
        Match match = matchList.getMatchForPlayer(player.getUsername());
        matchList.removeMatch(match.getId());
    }

    /**
     * Adds the Player to a particular Match
     * @param player Player whom will be added
     * @param matchID UUID of the Match that player will be addd to
     * @throws SQLException thrown if Player doesn't exist or a SQL connectivity issue occurs
     */
    @Override
    public void joinMatch(Player player, UUID matchId) throws SQLException {
        Match match = matchList.getMatch(matchId);
        player = playerRepository.get(player);
        match.addCharacter(player, match.getMap().getRandomFreeTilePoint());
    }

    /**
     * Get all Matches currently in the Lobbying state
     * @return MatchStatus[] array of all Matches in the Lobbying state
     */
    @Override
    public MatchStatus[] getLobbyingMatches() {
        List<Match> matches = matchList.getLobbyingMatches();
        MatchStatus[] matchStatuses = new MatchStatus[matches.size()];

        for(int i = 0; i < matches.size(); i++) {
            matchStatuses[i] = new MatchStatus(matches.get(i));
        }

        return matchStatuses;
    }

    /**
     * Gets the MatchResultModel for a finished Match
     * todo why not remove the Player from the Match at this point rather than send another request?
     * @param player Player the Player that has a finished Match
     * @return MatchResultModel pertaining to the player's Match
     */
    @Override
    public MatchResultModel getMatchResult(Player player) {
        Match match = matchList.getMatchForPlayer(player.getUsername());
        Character winner = match.getCharacterWithHighestCoins();

        return new MatchResultModel(winner.getPlayer().getUsername(), winner.getCollectedCoins(), match.getScore());
    }
}
