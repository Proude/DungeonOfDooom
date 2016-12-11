import com.dod.service.model.GameStateModel;
import communicators.MatchCommunicator;
import communicators.MoveCommunicator;
import communicators.stateCommunicator;

import java.util.UUID;

/**
 * The bot
 */
public class Bot {
    private MatchCommunicator matchCommunicator;
    private MoveCommunicator moveCommunicator;
    private communicators.stateCommunicator stateCommunicator;

    private boolean isPlaying = false;
    private GameStateModel state;

    public Bot() {
        this.matchCommunicator = new MatchCommunicator();
        this.moveCommunicator = new MoveCommunicator();
        this.stateCommunicator = new stateCommunicator();
    }

    public void play(UUID matchId) {
        isPlaying = true;
        matchCommunicator.joinMatch(matchId);
        state = stateCommunicator.getState();

        while(isPlaying) {
            isPlaying = false;
        }
    }
}