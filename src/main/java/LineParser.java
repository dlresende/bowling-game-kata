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

		char firstRoll = frames.charAt(0);

		if(isStrike(firstRoll)) {
			if(isExtra(frame)) {
				game.addExtra(10);
				parse(frames.substring(1), game, frame + 1);
			}
			
			else {
				game.addStrike();
				parse(frames.substring(1), game, frame + 1);
			}
		}

		else if(isDigit(firstRoll)) {
			int _1 = getNumericValue(firstRoll);

			if(isExtra(frame)) {
				game.addExtra(_1);
				parse(frames.substring(1), game, frame + 1);
			}

			else {
				char secondRoll = frames.charAt(1);

				if(isSpare(secondRoll)) {
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

	private boolean isStrike(char roll) {
		return roll == 'X';
	}

	private boolean isExtra(int frame) {
		return frame > 10;
	}

	private boolean isSpare(char roll) {
		return roll == '/';
	}
}
