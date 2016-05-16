import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class LineParserTest {

	private LineParser lineParser;

	@Before
	public void setUp() {
		lineParser = new LineParser();
	}

	@Test
	public void should_parse_line_for_simple_frames() {
		Game simpleGame = lineParser.parse("4433");

		assertThat(simpleGame.score(), is((4 + 4) + (3 + 3)));
	}

	@Test
	public void should_parse_hyphen_as_roll_miss() {
		Game secondRollMissed = lineParser.parse("5--4");

		assertThat(secondRollMissed.score(), is(5 + 4));
	}
}
