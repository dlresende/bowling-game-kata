
class BowlingGame {

	val simpleFrame = "^(\\d)(\\d)(.*)".r
	val spare	= "^\\d/(\\d)(.*)".r

	def score(line: String):Int = {
		computeScore(line.replace("-", "0"))
	}

	private def computeScore(line: String) = {
		line match {
			case simpleFrame(_1, _2, next) => _1.toInt + _2.toInt + score(next)
			case spare(_3, next) => 10 + _3.toInt + score(_3 + next)
			case _ => 0
		}
	}

}
