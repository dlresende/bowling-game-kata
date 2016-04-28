
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
	
	SIMPLE_FRAME = /^(\d)(\d)?/ 
	FIRST_TRY_MISSED = /^-(\d)/
	SECOND_TRY_MISSED = /^(\d)-/
	FRAME_MISSED = /^--/
	SPARE_WITH_FIRST_TRY_MISSED = /^-\//
	SPARE = /^(\d)\//

	def parse(line)
		do_parse(line, 1)
	end

	private	
	def do_parse(line, frame_count)
		case line
		when SPARE_WITH_FIRST_TRY_MISSED
			Spare.new(0, do_parse(line[2..-1], frame_count + 1))
		when SPARE
			Spare.new($1, do_parse(line[2..-1], frame_count + 1))
		when SIMPLE_FRAME
			if frame_count > 10
				Extra.new($1, do_parse(line[1..-1], frame_count + 1))
			else
				Frame.new($1, $2, do_parse(line[2..-1], frame_count + 1))
			end
		when FIRST_TRY_MISSED
			Frame.new(0, $1, do_parse(line[2..-1], frame_count + 1))
		when SECOND_TRY_MISSED
			Frame.new($1, 0, do_parse(line[2..-1], frame_count + 1))
		when FRAME_MISSED
			Frame.new(0, 0, do_parse(line[2..-1], frame_count + 1))
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
			other.first == @first	&&
			other.second == @second	&&
			other.nextFrame == @nextFrame
	end
end

class Spare < Frame
	def initialize(first, nextFrame = nil)
		super(first, 10 - first.to_i, nextFrame)
	end

	def compute_score
		@first + @second + (@nextFrame.nil? ? 0 : @nextFrame.first + @nextFrame.compute_score)  	
	end
end

class Extra < Frame

	def initialize(try, nextFrame = nil)
		super(try, 0, nextFrame)
	end

	def compute_score
		0
	end

	def to_s
		"|#{@first}" + (@nextFrame != nil ? @nextFrame.to_s : '|')
	end
end

