import org.scalatest._

class BowlingGameSuite extends FunSuite {

	test("should compute score for simple frames") {
		val game = new BowlingGame
		assert(game.score("9-9-9-9-9-9-9-9-9-9-") === 90)
	}
}
