public class BowlingGame {

	private final LineParser parser;

	public BowlingGame(LineParser parser) {
		this.parser = parser;
	}

	public int computeScore(String line) {
		Game game = parser.parse(line);	
		return game.score();
	}
}
