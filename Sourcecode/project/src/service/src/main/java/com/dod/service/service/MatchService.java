package com.dod.service.service;

import com.dod.db.repositories.IPlayerRepository;
import com.dod.game.MatchList;
import com.dod.models.Map;
import com.dod.models.Match;
import com.dod.models.Player;
import com.dod.service.constant.Assets;
import com.dod.service.model.MatchStatus;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Implementation of IMatchService signiature
 */
public class MatchService implements IMatchService {

    private IIOService ioService;
    private IParseService parseService;
    private IPlayerRepository playerRepository;

    public MatchService(IIOService ioService, IParseService parseService, IPlayerRepository playerRepository) {
        this.ioService = ioService;
        this.parseService = parseService;
        this.playerRepository = playerRepository;
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
        MatchList.addMatch(match);

        return new MatchStatus(match);
    }

    @Override
    public void initMatch() {

    }

    @Override
    public void getStatus() {

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
    public MatchStatus[] getLobbyingMatches() {
        List<Match> matches = MatchList.getLobbyingMatches();
        MatchStatus[] matchStatuses = new MatchStatus[matches.size()];

        for(int i = 0; i < matches.size(); i++) {
            matchStatuses[i] = new MatchStatus(matches.get(i));
        }

        return matchStatuses;
    }
}
