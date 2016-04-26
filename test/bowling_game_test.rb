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
