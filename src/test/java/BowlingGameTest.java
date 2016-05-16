import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class BowlingGameTest {

	private BowlingGame game;

	@Before
	public void setUp() {
		game = new BowlingGame(new LineParser());
	}

	@Test
	public void should_compute_score_for_simple_frames() {
		int score = game.computeScore("9-9-9-9-9-9-9-9-9-9-");

		assertThat(score, is(90));
	}
}
