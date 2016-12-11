import communicators.MatchCommunicator;
import communicators.MoveCommunicator;
import communicators.StatusCommunicator;

import java.util.UUID;

/**
 * The bot
 */
public class Bot {
    private MatchCommunicator matchCommunicator;
    private MoveCommunicator moveCommunicator;
    private StatusCommunicator statusCommunicator;

    private boolean isPlaying = false;

    public Bot() {
        this.matchCommunicator = new MatchCommunicator();
        this.moveCommunicator = new MoveCommunicator();
        this.statusCommunicator = new StatusCommunicator();
    }

    public void play(UUID matchId) {
        isPlaying = true;

        while(isPlaying) {
            ///...
        }
    }
}