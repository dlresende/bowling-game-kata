import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class GameTest {

	@Test
	public void should_compute_score_for_simple_frames() {
		Game onlySimpleFrames = new Game();
		onlySimpleFrames.addFrame(3, 3);
		onlySimpleFrames.addFrame(2, 2);

		int score = onlySimpleFrames.score();

		assertThat(score, is((3 + 3) + (2 + 2)));
	}
}
