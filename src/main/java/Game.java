import java.util.LinkedList;

// Visitor
public class Game {

	private final LinkedList<Frame> frames = new LinkedList<>();

	public int score() {
		return frames.stream().mapToInt((frame) -> frame.score(this)).sum();
	}

	public void addFrame(int firstTry, int secondTry) {
		Frame frame = new Frame(firstTry, secondTry);
		add(frame);
	}

	public void addSpare(int firstTry) {
		Spare spare = new Spare(firstTry);
		add(spare);
	}

	public void addStrike() {
		Strike strike = new Strike();
		add(strike);
	}

	public void addExtra(int roll) {
		Extra extra = new Extra(roll);
		add(extra);
	}

	private int computeScoreFor(Frame current){
		return current.score();
	}

	private int computeScoreFor(Spare current){
		return current.score() + frameAfter(current).firstTry;
	}

	private int roll(int rollNumber, Frame current) {
		return current.roll(rollNumber, this);	
	}

	private int computeScoreFor(Strike current) {
		Frame next = frameAfter(current);
		return current.score() + next.roll(1, this) + next.roll(2, this);
	}

	private int computeScoreFor(Extra current) {
		return current.score();
	}
	
	private Frame frameAfter(Frame frame) {
		int frameIndex = frames.indexOf(frame);

		if(frameIndex == -1) {
			throw new IllegalArgumentException("Frame " + frame + " does not exist.");
		}

		try {
			return frames.get(frameIndex + 1);
		} catch(IndexOutOfBoundsException exception) {
			return new EmptyFrame();
		}
	}

	private void add(Frame frame) {
		frames.add(frame);
	}

	// Elements
	class Frame {

		private final int firstTry;
		private final int secondTry;

		public Frame(int _1, int _2) {
			firstTry = _1;
			secondTry = _2;
		}

		public int score() {
			return firstTry + secondTry;
		}
		
		public int score(Game game) {
			return game.computeScoreFor(this);
		}

		public int roll(int roll, Game game) {
			if(roll == 1) {
				return firstTry;
			}

			if(roll == 2) {
				return secondTry;
			}

			return game.frameAfter(this).roll(roll - 2, game);
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

	class Strike extends Frame {
		public Strike() {
			super(10, 0);
		}

		@Override
		public int score(Game game) {
			return game.computeScoreFor(this);
		}

		@Override
		public int roll(int roll, Game game) {
			if(roll == 1) {
				return 10;
			}

			return game.frameAfter(this).roll(roll - 1, game);
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
		
		@Override
		public int roll(int roll, Game game) {
			if(roll == 1) {
				return 10;
			}

			return game.frameAfter(this).roll(roll - 1, game);
		}
	}

	// Null object
	class EmptyFrame extends Frame {
		public EmptyFrame() {
			super(0, 0);
		}
	}
}

