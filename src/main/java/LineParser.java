import static java.lang.Character.isDigit;
import static java.lang.Character.getNumericValue;

public class LineParser {

	public Game parse(String line) {
		Game game = new Game();
		String lineWithoutHyphen = line.replace("-", "0");
		parse(lineWithoutHyphen, game, 1);
		return game;
	}

	private void parse(String frames, Game game, int frame) { 

		if(frames.isEmpty()) {
			return;
		}

		char firstTry = frames.charAt(0);

		if(firstTry == 'X') {
			if(frame > 10) {
				game.addExtra(10);
				parse(frames.substring(1), game, frame + 1);
			}
			
			else {
				game.addStrike();
				parse(frames.substring(1), game, frame + 1);
			}
		}

		else if(isDigit(firstTry)) {
			int _1 = getNumericValue(firstTry);

			if(frame > 10) {
				game.addExtra(_1);
				parse(frames.substring(1), game, frame + 1);
			}

			else {
				char secondRoll = frames.charAt(1);

				if(secondRoll == '/') {
					game.addSpare(_1);
					parse(frames.substring(2), game, frame + 1);
				}

				else {
					int _2 = getNumericValue(secondRoll);
					game.addFrame(_1, _2);
					parse(frames.substring(2), game, frame + 1);
				}
			}
		}

		else {
			throw new IllegalStateException(frames);
		}
	}
}
