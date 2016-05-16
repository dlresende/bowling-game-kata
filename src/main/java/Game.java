import java.util.LinkedList;

// Visitor
public class Game {

	private final LinkedList<Frame> frames = new LinkedList<>();

	public int score() {
		return frames.stream().mapToInt((frame) -> frame.score(this)).sum();
	}

	public void addFrame(int firstRoll, int secondRoll) {
		Frame frame = new Frame(firstRoll, secondRoll);
		frames.add(frame);
	}

	public void addSpare(int firstRoll) {
		Spare spare = new Spare(firstRoll);
		frames.add(spare);
	}

	public void addExtra(int roll) {
		Extra extra = new Extra(roll);
		frames.add(extra);
	}

	private int computeScoreFor(Frame current){
		return current.score();
	}

	private int computeScoreFor(Spare current){
		return current.score() + nextFor(current).firstRoll;
	}

	private int computeScoreFor(Extra current) {
		return current.score();
	}
	
	private Frame nextFor(Frame frame) {
		int frameIndex = frames.indexOf(frame);
		return frames.get(frameIndex + 1);
	}

	private void add(Frame frame) {
		frames.add(frame);
	}

	// Elements
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
		
		public int score(Game game) {
			return game.computeScoreFor(this);
		}
	}

	class Spare extends Frame {
		public Spare(int _1) {
			super(_1, 10 - _1);
		}
		
		@Override
		public int score(Game game) {
			return game.computeScoreFor(this);
		}
	}

	class Extra extends Frame {
		public Extra(int _1) {
			super(_1, 0);
		}

		@Override
		public int score() {
			return 0;
		}

		@Override
		public int score(Game game) {
			return game.computeScoreFor(this);
		}
	}
}

