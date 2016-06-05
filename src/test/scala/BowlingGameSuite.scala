import org.scalatest._

class BowlingGameSuite extends FunSuite {

	test("should compute score for simple frames") {
		val game = new BowlingGame
		assert(game.score("90909090909090909090") === 90)
	}
	
	test("should replace - by 0") {
		val game = new BowlingGame
		assert(game.score("9--99-9-9-9-9-9-9-9-") === 90)
	}
}
