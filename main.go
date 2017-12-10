package main

import (
	"log"
	"os"

	"github.com/dlresende/kata-bowling-game/score"
)

func main() {
	if len(os.Args) != 2 {
		log.Fatalf("Wrong number of arguments.\nUsage: %s <line>", os.Args[0])
	}

	score := score.CalculateScore(os.Args[1])
	log.Printf("Score: %d", score)
}
