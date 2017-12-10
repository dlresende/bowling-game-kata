package score

import (
	"regexp"
	"strconv"
	"strings"
)

func CalculateScore(line string) int {
	line = strings.Replace(line, "-", "0", -1)
	return calculateScoreFrameByFrame(line, 0)
}

type RegularFrame struct{ regexp *regexp.Regexp }

func NewRegularFrame() Calculator {
	return &RegularFrame{regexp: regexp.MustCompile(`^(\d)(\d)(.*)`)}
}

func (r *RegularFrame) canHandle(line string) bool {
	return r.regexp.MatchString(line)
}

func (r *RegularFrame) calculateScore(line string) (int, string) {
	tries := r.regexp.FindStringSubmatch(line)
	try1 := getPoints(tries[1])
	try2 := getPoints(tries[2])
	otherTries := tries[3]
	return try1 + try2, otherTries
}

type SpareFrame struct{ regexp *regexp.Regexp }

func NewSpareFrame() Calculator {
	return &SpareFrame{regexp: regexp.MustCompile(`^\d\/((\w?).*)`)}
}

func (s *SpareFrame) canHandle(line string) bool {
	return s.regexp.MatchString(line)
}

func (s *SpareFrame) calculateScore(line string) (int, string) {
	tries := s.regexp.FindStringSubmatch(line)
	try1 := getPoints(tries[2])
	otherTries := tries[1]
	return 10 + try1, otherTries
}

type StrikeFrame struct{ regexp *regexp.Regexp }

func NewStrikeFrame() Calculator {
	return &StrikeFrame{regexp: regexp.MustCompile(`^X((\w?)(\w?).*)`)}
}

func (frame *StrikeFrame) canHandle(line string) bool {
	return frame.regexp.MatchString(line)
}

func (frame *StrikeFrame) calculateScore(line string) (int, string) {
	tries := frame.regexp.FindStringSubmatch(line)
	try1 := getPoints(tries[2])
	try2 := getPoints(tries[3])
	otherTries := tries[1]
	return 10 + try1 + try2, otherTries
}

type StrikeFollowedBySpareFrame struct{ regexp *regexp.Regexp }

func NewStrikeFollowedBySpareFrame() Calculator {
	return &StrikeFollowedBySpareFrame{regexp: regexp.MustCompile(`^X(\d\/.*)`)}
}

func (frame *StrikeFollowedBySpareFrame) canHandle(line string) bool {
	return frame.regexp.MatchString(line)
}

func (frame *StrikeFollowedBySpareFrame) calculateScore(line string) (int, string) {
	tries := frame.regexp.FindStringSubmatch(line)
	otherTries := tries[1]
	return 10 + 10, otherTries
}

type Calculator interface {
	canHandle(line string) bool
	calculateScore(line string) (int, string)
}

func rules() []Calculator {
	return []Calculator{NewStrikeFollowedBySpareFrame(), NewStrikeFrame(), NewSpareFrame(), NewRegularFrame()}
}

func calculateScoreFrameByFrame(line string, frame int) int {
	if isLast(frame) {
		return 0
	}

	for _, rule := range rules() {
		if rule.canHandle(line) {
			frameScore, otherTries := rule.calculateScore(line)
			return frameScore + calculateScoreFrameByFrame(otherTries, frame+1)
		}
	}

	panic("Should never hit here")
}

func isLast(frame int) bool {
	return frame == 10
}

func isStrike(throw string) bool {
	return throw == "X"
}

func getPoints(throw string) int {
	if isStrike(throw) {
		return 10
	}

	points, err := strconv.Atoi(throw)
	if err != nil {
		panic(err)
	}

	return points
}
