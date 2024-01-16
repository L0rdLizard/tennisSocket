import dev.Maksim.client.model.BallModel;
import dev.Maksim.client.model.GameModel;
import org.junit.jupiter.api.Test;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class TestClient {
    @Test
    public void RacketTest() {
        GameModel gameModel = new GameModel();
        gameModel.getTennisCourt().getRacketLeft().setRacketPos(100);
        int y = gameModel.getTennisCourt().getRacketLeft().getY() + 45;
        assertEquals(50, y);
    }

    @Test
    public void ScoreTest() throws InterruptedException {
        GameModel gameModel = new GameModel();
        gameModel.changePlaying(true);
        for (BallModel ball : gameModel.getTennisCourt().getBalls()) {
            ball.setX(-200);
        }
        Thread.sleep(5);
        int result = gameModel.getTennisCourt().getScoreRight();
        Thread.sleep(5);
//        System.out.println(result);
        assertEquals(result, 1);
    }
}
