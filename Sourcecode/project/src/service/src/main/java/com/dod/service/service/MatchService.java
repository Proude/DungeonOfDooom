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
    public void startMatch() {

    }

    @Override
    public MatchStatus getStatus(String username) {
        if(!matchList.playerHasMatch(username)) {
            return new MatchStatus();
        } else {
            Match match = matchList.getMatchForPlayer(username);
            return new MatchStatus(match);
        }
    }

    @Override
    public void leaveMatch() {

    }

    @Override
    public void endMatch() {

    }

    @Override
    public void joinMatch() {

    }

    @Override
    public void joinMatch(Player player) {

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
