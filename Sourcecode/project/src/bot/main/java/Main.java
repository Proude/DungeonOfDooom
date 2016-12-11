import java.util.UUID;

/**
 * Gets command parameters and intitialises bot
 */
public class Main {

    /**
     * Start the bot
     * @param args expects 1 argument of ID for match to join
     */
    public static void main(String args[]) {
        UUID matchId = UUID.fromString(args[0]);

        Bot bot = new Bot();
        bot.play(matchId);
    }
}
