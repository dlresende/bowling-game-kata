package score

import (
	"testing"
)

func TestThatItCalculatesTheScoreForSimpleThrows(t *testing.T) {
	score := CalculateScore("90909090909090909090")

	assertEqual(90, score, t)
}

func TestThatHyphenMeansZero(t *testing.T) {
	score := CalculateScore("9-9-9-9-9-9-9-9-9-9-")

	assertEqual((9+0)+(9+0)+(9+0)+(9+0)+(9+0)+(9+0)+(9+0)+(9+0)+(9+0)+(9+0), score, t)
}

func TestThatItCalculatesTheScoreForSpares(t *testing.T) {
	score := CalculateScore("5/5/5/5/5/5/5/5/5/5/5")

	assertEqual((5+5+5)+(5+5+5)+(5+5+5)+(5+5+5)+(5+5+5)+(5+5+5)+(5+5+5)+(5+5+5)+(5+5+5)+(5+5+5), score, t)
}

func TestThatItCalculatesTheScoreForStrikes(t *testing.T) {
	score := CalculateScore("XXXXXXXXXXXX")

	assertEqual((10+10+10)+(10+10+10)+(10+10+10)+(10+10+10)+(10+10+10)+(10+10+10)+(10+10+10)+(10+10+10)+(10+10+10)+(10+10+10), score, t)
}

func TestThatItCalculatesTheScoreForStrikesFollowedBySpares(t *testing.T) {
	score := CalculateScore("X5/XXXXXXXXXX")

	assertEqual((10+5+5)+(5+5+10)+(10+10+10)+(10+10+10)+(10+10+10)+(10+10+10)+(10+10+10)+(10+10+10)+(10+10+10)+(10+10+10), score, t)
}

func assertEqual(expected, actual int, t *testing.T) {
	if expected != actual {
		t.Fatalf("\nexpected: %d\ngot: %d", expected, actual)
	}
}
