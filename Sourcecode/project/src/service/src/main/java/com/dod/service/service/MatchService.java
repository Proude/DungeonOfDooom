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
 * Implementation of IMatchService signiature
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

    @Override
    public void startMatch(Player player) {
        Match match = matchList.getMatchForPlayer(player.getUsername());
        Date temp = new Date();
        match.setTimer(temp.getTime());
        match.setState(MatchState.Ingame);
    }

    @Override
    public MatchStatus getStatus(Player player) {
        if(!matchList.playerHasMatch(player.getUsername())) {
            return null;
        } else {
            Match match = matchList.getMatchForPlayer(player.getUsername());
            return new MatchStatus(match);
        }
    }

    @Override
    public void leaveMatch(Player player) {
        Match match = matchList.getMatchForPlayer(player.getUsername());

        match.removeCharacter(player);
    }

    @Override
    public void endMatch(Player player) {
        Match match = matchList.getMatchForPlayer(player.getUsername());
        matchList.removeMatch(match.getId());
    }

    @Override
    public void joinMatch(Player player, UUID matchId) throws SQLException {
        Match match = matchList.getMatch(matchId);
        player = playerRepository.get(player);
        match.addCharacter(player, match.getMap().getRandomFreeTilePoint());
    }

    @Override
    public MatchStatus[] getLobbyingMatches() {
        List<Match> matches = matchList.getLobbyingMatches();
        MatchStatus[] matchStatuses = new MatchStatus[matches.size()];

        for(int i = 0; i < matches.size(); i++) {
            matchStatuses[i] = new MatchStatus(matches.get(i));
        }

        return matchStatuses;
    }

    @Override
    public MatchResultModel getMatchResult(Player player) {
        Match match = matchList.getMatchForPlayer(player.getUsername());
        Character winner = match.getCharacterWithHighestCoins();

        return new MatchResultModel(winner.getPlayer().getUsername(), winner.getCollectedCoins(), match.getScore());
    }
}
