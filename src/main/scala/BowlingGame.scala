
class BowlingGame {

	val simpleFrame = "^(\\d)(\\d)(.*)".r
	val spare	= "^\\d/(\\d)(.*)".r
	val spareFollowedByStrike	= "^\\d/(X.*)".r
	val strikeFollowedBy2Strikes	= "^X(XX.*)".r
	val strikeFollowedBySpare	= "^X(\\d/.*)".r
	val strike			= "^X(\\d)(\\d)(.*)".r

	def score(line: String) = {
		computeScore(line.replace("-", "0"))
	}

	private def computeScore(line: String):Int = {
		line match {
			case simpleFrame(_1, _2, next) => _1.toInt + _2.toInt + computeScore(next)
			case spareFollowedByStrike(next) => 20 + computeScore(next)
			case spare(_3, next) => 10 + _3.toInt + computeScore(_3 + next)
			case strikeFollowedBy2Strikes(next) => 30 + computeScore(next)
			case strikeFollowedBySpare(next) => 20 + computeScore(next)
			case strike(_2, _3, next) => 10 + _2.toInt + _3.toInt + computeScore(_2 + _3 + next)
			case _ => 0
		}
	}

}
