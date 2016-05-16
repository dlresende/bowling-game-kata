import java.util.LinkedList;

public class Game {

	private final LinkedList<Frame> frames = new LinkedList<>();

	public int score() {
		return frames.stream().mapToInt(Frame::score).sum();
	}

	public void addFrame(int firstRoll, int secondRoll) {
		Frame frame = new Frame(firstRoll, secondRoll);
		frames.add(frame);
	}

	private void add(Frame frame) {
		frames.add(frame);
	}

	class Frame {

		private final int firstRoll;
		private final int secondRoll;

		public Frame(int _1, int _2) {
			firstRoll = _1;
			secondRoll = _2;
		}

		public int score() {
			return firstRoll + secondRoll;
		}
	}
}

