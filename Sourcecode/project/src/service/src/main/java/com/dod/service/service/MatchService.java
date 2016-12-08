package com.dod.service.service;

import com.dod.db.repositories.IPlayerRepository;
import com.dod.game.IMatchList;
import com.dod.game.MatchList;
import com.dod.models.Map;
import com.dod.models.Match;
import com.dod.models.Player;
import com.dod.service.constant.Assets;
import com.dod.service.model.MatchStatus;

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
        matchList.addMatch(match);

        return new MatchStatus(match);
    }

    @Override
    public void startMatch(UUID id) {

    }

    @Override
    public MatchStatus getStatus(Player player) {
        if(!matchList.playerHasMatch(player.getUsername())) {
            return new MatchStatus();
        } else {
            Match match = matchList.getMatchForPlayer(player.getUsername());
            return new MatchStatus(match);
        }
    }

    @Override
    public void leaveMatch(Player player) {

    }

    @Override
    public void endMatch(Player player) {

    }

    @Override
    public void joinMatch(Player player, UUID matchID) {

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
}
