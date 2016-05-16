import static java.lang.Character.isDigit;
import static java.lang.Character.getNumericValue;

public class LineParser {

	public Game parse(String line) {
		Game game = new Game();
		String lineWithoutHyphen = line.replace("-", "0");
		parse(lineWithoutHyphen, game);
		return game;
	}

	private void parse(String frames, Game game) { 

		if(frames.isEmpty()) {
			return;
		}

		char firstTry = frames.charAt(0);

		if(isDigit(firstTry)) {
			int _1 = getNumericValue(firstTry);
			char secondRoll = frames.charAt(1);
			int _2 = getNumericValue(secondRoll);
			game.addFrame(_1, _2);
			parse(frames.substring(2), game);
		}

		else {
			throw new IllegalStateException(frames);
		}
	}
}
