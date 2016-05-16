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

	@Test
	public void should_compute_score_for_spares() {
		Game spares = new Game();
		spares.addSpare(5);
		spares.addFrame(2, 2);

		int score = spares.score();

		assertThat(score, is((10 + 2) + (2 + 2)));
	}

	@Test
	public void should_compute_score_for_extra_frames() {
		Game extra = new Game();
		extra.addFrame(0, 0);
		extra.addFrame(1, 0);
		extra.addFrame(2, 0);
		extra.addFrame(3, 0);
		extra.addFrame(4, 0);
		extra.addFrame(5, 0);
		extra.addFrame(6, 0);
		extra.addFrame(7, 0);
		extra.addFrame(8, 0);
		extra.addSpare(9);
		extra.addExtra(5);

		int score = extra.score();
		
		assertThat(score, is(1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + (10 + 5)));
	}
}
