package tests;
import org.junit.Test;
import static org.junit.Assert.*;
import HighScores.*;

public class HighScoresTest {

    @Test
    public void test_Add_Score() {
        HighScores hScores = new HighScores();
        ScoreEntry score = new ScoreEntry("Rachel", 500);
        ScoreEntry score2 = new ScoreEntry("Brandon", 200);
        hScores.addScoreList(score);
        assertEquals(1, hScores.getScoreList().size());
        hScores.addScoreList(score2);

        assertEquals(2, hScores.getScoreList().size());

    }

    @Test
    public void test_Sort_Score() {
        HighScores hScores = new HighScores();
        ScoreEntry score = new ScoreEntry("Simon", 200);
        ScoreEntry score2 = new ScoreEntry("Isaac", 400);
        hScores.addScoreList(score);
        assertEquals(1, hScores.getScoreList().size());
        hScores.addScoreList(score2);
        assertEquals(2, hScores.getScoreList().size());
        hScores.sortScores();
        assertEquals("Isaac", hScores.getScoreList().get(0).getPlayerName());

    }

}