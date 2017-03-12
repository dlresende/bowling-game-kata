import org.scalatest._

class BowlingGameSuite extends FunSuite {
	val game = new BowlingGame

	test("should compute score for simple frames") {
		assert(game.score("90909090909090909090") === 90)
	}

	test("should replace - by 0") {
		assert(game.score("9--99-9-9-9-9-9-9-9-") === 90)
	}

	test("should compute score for spares") {
		assert(game.score("5/5/5/5/5/5/5/5/5/5/5") === 150)
		assert(game.score("5/5/5/5/5/5/5/5/5/5/X") === 155)
	}

	test("should not compute extra frames") {
		assert(game.score("0000000000000000005/5") === 15)
	}

	test("should compute score for strikes") {
		assert(game.score("X54----------------") === 10+5+4 + 5+4)
		assert(game.score("XXXXXXXXXXXX") === 300)
		assert(game.score("X5/----------------") === 10+5+5 + 5+5)
		assert(game.score("1/35XXX458/X3/XX6") === 10+3 + 3+5 + 10+10+10 + 10+10+4 + 10+4+5 + 4+5 + 10+10 + 10+3+7 + 10+10 + 10+10+6)
	}

	test("should not add rolls beyond the 10th roll into the score") {
		assert(game.score("XXXXXXXXXX12") === 10+10+10 + 10+10+10 + 10+10+10 + 10+10+10 + 10+10+10 + 10+10+10 + 10+10+10 + 10+10+10 + 10+10+1 + 10+1+2)
	}
}
