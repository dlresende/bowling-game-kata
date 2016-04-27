require 'test/unit'
require_relative '../lib/bowling_game'

class BowlingGameTest < Test::Unit::TestCase

	def setup
		@bowling = BowlingGame.new
	end

	def test_that_can_compute_score_for_simple_frames
		assert_equal 90, @bowling.score('9-9-9-9-9-9-9-9-9-9-')
	end
end

class ParserTest < Test::Unit::TestCase

	def setup
		@parser = Parser.new
	end

	def test_that_simple_frames_can_be_parsed
		game = Frame.new(1, 2, Frame.new(2, 3, Frame.new(3, 4)))

		assert_equal game, @parser.parse('122334')
	end
	
	def test_that_hyphen_is_zero
		game = Frame.new(0, 2, Frame.new(2, 0, Frame.new(3, 4)))

		assert_equal game, @parser.parse('-22-34')
	end
end

class FrameTest < Test::Unit::TestCase

	def test_that_score_is_computed_to_simple_frames
		game = Frame.new(0, 2, Frame.new(2, 0, Frame.new(3, 4)))
	
		assert_equal (2 + 2 + 7), game.compute_score	
	end
end
