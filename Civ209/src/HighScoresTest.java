

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;


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
        hScores.sortScores(hScores.getScoreList());
        assertEquals("Isaac", hScores.getScoreList().get(0).getPlayerName());

    }

    @Test
    public void test_save_and_load() throws IOException {
        HighScores game1 = new HighScores();
        ScoreEntry score = new ScoreEntry("Eric", 702);
        game1.addScoreList(score);
        game1.save(game1.getScoreList());
        HighScores scores = new HighScores();
        scores.load();
        ScoreEntry firstScore = scores.getScoreList().get(0);
        assertEquals("Eric", firstScore.getPlayerName());
        assertEquals(702, firstScore.getPlayerScore());
        ScoreEntry score2 = new ScoreEntry("Caleb", 540);
        game1.addScoreList(score2);
        game1.save(game1.getScoreList());
        HighScores s = new HighScores();
        s.load();
        ScoreEntry secondScore = s.getScoreList().get(1);
        assertEquals("Caleb", secondScore.getPlayerName());
        assertEquals(540, secondScore.getPlayerScore());

    }

}