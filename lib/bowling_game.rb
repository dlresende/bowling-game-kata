class EmptyFrame

	def compute_score
		0
	end

	def roll(index)
		0
	end

	def ==(other)
		! other.nil? 
	end

	def to_s
		'|'
	end
end

class Frame < EmptyFrame

	def initialize(first_roll, second_roll, next_frame = EMPTY_FRAME)
		@first_roll = first_roll.to_i
		@second_roll = second_roll.to_i
		@next_frame = next_frame
	end

	attr_reader :first_roll
	attr_reader :second_roll
	attr_reader :next_frame

	def compute_score
		@first_roll + @second_roll + @next_frame.compute_score
	end

	def roll(index)
		if index == 0
			@first_roll
		elsif index == 1
			@second_roll
		else
			@next_frame.try(index - 1)
		end
	end
	
	def to_s
		"|#{@first_roll},#{@second_roll}" + @next_frame.to_s
	end

	def ==(other)
		other != nil &&
			other.first_roll == @first_roll	&&
			other.second_roll == @second_roll	&&
			other.next_frame == @next_frame
	end
end

class Spare < Frame
	def initialize(first_roll, next_frame = nil)
		super(first_roll, 10 - first_roll.to_i, next_frame)
	end
	
	def compute_score
		@first_roll + @second_roll + @next_frame.first_roll + @next_frame.compute_score
	end
end

class Extra < Frame

	def initialize(try, next_frame = EMPTY_FRAME)
		super(try, 0, next_frame)
	end
	
	def roll(index)
		if index.zero?
			@first_roll	
		else
			@next_frame.roll(index - 1)
		end
	end

	def compute_score
		0
	end

	def to_s
		"|#{@first_roll}" + @next_frame.to_s
	end
end

class Strike < Frame
	
	def initialize(next_frame = EMPTY_FRAME)
		super(10, 0, next_frame)
	end

	def roll(index)
		if index.zero?
			10
		else
			@next_frame.roll(index - 1)
		end
	end

	def compute_score
		10 + roll(1) + roll(2) + @next_frame.compute_score
	end

	def to_s
		'|10' + @next_frame.to_s
	end
end


EMPTY_FRAME = EmptyFrame.new

class Parser
	
	SIMPLE_FRAME_OR_EXTRA_TRY = /^(\d)(\d)?/ 
	FIRST_TRY_MISSED = /^-(\d)/
	SECOND_TRY_MISSED = /^(\d)-/
	FRAME_OR_EXTRA_TRY_MISSED = /^--?/
	SPARE_WITH_FIRST_TRY_MISSED = /^-\//
	SPARE = /^(\d)\//
	STRIKE_OR_EXTRA_FRAME = /^X/

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
		when SIMPLE_FRAME_OR_EXTRA_TRY
			if frame_count > 10
				Extra.new($1, do_parse(line[1..-1], frame_count + 1))
			else
				Frame.new($1, $2, do_parse(line[2..-1], frame_count + 1))
			end
		when FIRST_TRY_MISSED
			Frame.new(0, $1, do_parse(line[2..-1], frame_count + 1))
		when SECOND_TRY_MISSED
			Frame.new($1, 0, do_parse(line[2..-1], frame_count + 1))
		when FRAME_OR_EXTRA_TRY_MISSED
			if frame_count > 10
				Extra.new(0, do_parse(line[1..-1], frame_count + 1))
			else
				Frame.new(0, 0, do_parse(line[2..-1], frame_count + 1))
			end
		when STRIKE_OR_EXTRA_FRAME
			if frame_count > 10
				Extra.new(10, do_parse(line[1..-1], frame_count + 1))
			else
				Strike.new(do_parse(line[1..-1], frame_count + 1))
			end
		else
			EMPTY_FRAME
		end
	end
end

class BowlingGame

	def initialize(parser = Parser.new)
		@parser = parser
	end

	def score(line)
		game = @parser.parse(line)
		game.compute_score
	end

end

