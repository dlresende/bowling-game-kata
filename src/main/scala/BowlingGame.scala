
class BowlingGame {

	val simpleFrame = "^(\\d)(\\d)(.*)".r

	def score(line: String):Int = {
		line.replace("-", "0") match {
			case simpleFrame(_1, _2, next) => _1.toInt + _2.toInt + score(next)
			case _ => 0
		}
	}

}
