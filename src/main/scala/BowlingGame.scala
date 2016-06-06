
class BowlingGame {

	val simpleFrame = "^(\\d)(\\d)(.*)".r
	val spare	= "^\\d/(\\d)(.*)".r
	val spareFollowedByStrike	= "^\\d/(X.*)".r
	val strikeFollowedBy2Strikes	= "^X(XX.*)".r
	val strikeFollowedBySpare	= "^X(\\d/.*)".r
	val strikeFollowedByStrike	= "^XX(\\d)(.*)".r
	val strike			= "^X(\\d)(\\d)(.*)".r

	def score(line: String) = {
		computeScore(line.replace("-", "0"), 1)
	}

	private def computeScore(line: String, frame: Int):Int = {
		if(frame > 10)
			0
		else
			line match {
				case simpleFrame(_1, _2, next) => _1.toInt + _2.toInt + computeScore(next, frame + 1)
				case spareFollowedByStrike(next) => 20 + computeScore(next, frame + 1)
				case spare(_3, next) => 10 + _3.toInt + computeScore(_3 + next, frame + 1)
				case strikeFollowedBy2Strikes(next) => 30 + computeScore(next, frame + 1)
				case strikeFollowedBySpare(next) => 20 + computeScore(next, frame + 1)
				case strikeFollowedByStrike(_3, next) => 20 + _3.toInt + computeScore("X" + _3 + next, frame + 1) // String interpolation seems not to work with scala 2.9.2 under Ubuntu :/
				case strike(_2, _3, next) => 10 + _2.toInt + _3.toInt + computeScore(_2 + _3 + next, frame + 1)
				case _ => 0
			}
	}

}
