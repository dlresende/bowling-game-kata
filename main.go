package main

import (
	"log"
	"os"
	"regexp"
	"strconv"
	"strings"
)

func main() {
	if len(os.Args) != 2 {
		log.Fatalf("Wrong number of arguments.\nUsage: %s <line>", os.Args[0])
	}

	score := calculateScore(os.Args[1])
	log.Printf("Score: %d", score)
}

func calculateScore(line string) int {
	line = strings.Replace(line, "-", "0", -1)
	return calculateScoreFrameByFrame(line, 0)
}

var strikeFollowedBySpare = regexp.MustCompile(`^X(\d\/.*)`)
var strike = regexp.MustCompile(`^X((\w?)(\w?).*)`)
var spare = regexp.MustCompile(`^\d\/((\w?).*)`)
var regular = regexp.MustCompile(`^(\d)(\d)(.*)`)

func calculateScoreFrameByFrame(line string, frame int) int {
	if isLast(frame) {
		return 0
	}

	switch {
	case strikeFollowedBySpare.MatchString(line):
		tries := strikeFollowedBySpare.FindStringSubmatch(line)
		otherTries := tries[1]
		return 10 + 10 + calculateScoreFrameByFrame(otherTries, frame+1)
	case strike.MatchString(line):
		tries := strike.FindStringSubmatch(line)
		try1 := getPoints(tries[2])
		try2 := getPoints(tries[3])
		otherTries := tries[1]
		return 10 + try1 + try2 + calculateScoreFrameByFrame(otherTries, frame+1)
	case spare.MatchString(line):
		tries := spare.FindStringSubmatch(line)
		try1 := getPoints(tries[2])
		otherTries := tries[1]
		return 10 + try1 + calculateScoreFrameByFrame(otherTries, frame+1)
	case regular.MatchString(line):
		tries := regular.FindStringSubmatch(line)
		try1 := getPoints(tries[1])
		try2 := getPoints(tries[2])
		otherTries := tries[3]
		return try1 + try2 + calculateScoreFrameByFrame(otherTries, frame+1)
	default:
		return 0
	}
}

func isLast(frame int) bool {
	return frame == 10
}

func getPoints(throw string) int {
	if throw == "X" {
		return 10
	}

	points, err := strconv.Atoi(throw)

	if err != nil {
		log.Fatal(err)
	}

	return points
}
