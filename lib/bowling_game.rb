
class BowlingGame

	def initialize(parser = Parser.new)
		@parser = parser
	end

	def score(line)
		game = @parser.parse(line)
		game.compute_score
	end

end

class Parser
	
	SIMPLE_FRAME = /^(\d)(\d)/ 
	FIRST_TRY_MISSED = /^-(\d)/
	SECOND_TRY_MISSED = /^(\d)-/
	FRAME_MISSED = /^--/

	def parse(line)
		case line
		when SIMPLE_FRAME 
			Frame.new($1, $2, parse(line[2..-1]))
		when FIRST_TRY_MISSED
			Frame.new(0, $1, parse(line[2..-1]))
		when SECOND_TRY_MISSED
			Frame.new($1, 0, parse(line[2..-1]))
		when FRAME_MISSED
			Frame.new(0, 0, parse(line[2..-1]))	
		else
			nil
		end
	end
end

class Frame

	def initialize(first, second, nextFrame = nil)
		@first = first.to_i
		@second = second.to_i
		@nextFrame = nextFrame
	end

	attr_reader :first
	attr_reader :second
	attr_reader :nextFrame

	def compute_score
		@first + @second + (! @nextFrame.nil? ? @nextFrame.compute_score : 0)
	end

	def to_s
		"|#{@first},#{@second}" + (@nextFrame != nil ? @nextFrame.to_s : '|')
	end

	def ==(other)
		other != nil &&
			other.instance_of?(Frame) &&
			other.first == @first	&&
			other.second == @second	&&
			other.nextFrame == @nextFrame
	end
end
