import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class BowlingGameTest {

	@Test
	public void should_compute_score_for_simple_frames() {
		BowlingGame game = new BowlingGame();
		
		int score = game.computeScore("9-9-9-9-9-9-9-9-9-9-");

		assertThat(score, is(90));
	}
}
