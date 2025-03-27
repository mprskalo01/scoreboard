import com.worldcup.scoreboard.Scoreboard;
import com.worldcup.scoreboard.model.Match;

public class Quickstart {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();

        // Match start order
        Match match1 = scoreboard.startMatch("Mexico", "Canada");
        Match match2 = scoreboard.startMatch("Spain", "Brazil");
        Match match3 = scoreboard.startMatch("Germany", "France");
        Match match4 = scoreboard.startMatch("Uruguay", "Italy");
        Match match5 = scoreboard.startMatch("Argentina", "Australia");


        // Update scores
        scoreboard.updateScore(match1, 0, 5);
        scoreboard.updateScore(match2, 10, 2);
        scoreboard.updateScore(match3, 2, 2);
        scoreboard.updateScore(match4, 6, 6);
        scoreboard.updateScore(match5, 3, 1);

        // Finish a match
        //  scoreboard.finishMatch(match3);

        // Print summary
        System.out.println(scoreboard.getSummary());

        /* Expected Output:
        Matches summary:
        1. Uruguay 6 - Italy 6
        2. Spain 10 - Brazil 2
        3. Mexico 0 - Canada 5
        4. Argentina 3 - Australia 1
        ? 5. Germany 2 - France 2 // will not show this one if finished above
        */
    }
}