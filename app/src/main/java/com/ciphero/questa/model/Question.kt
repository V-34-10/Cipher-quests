package com.ciphero.questa.model

data class Question(val text: String, val answers: List<String>, val correctAnswer: String)

object CasinoQuizQuestions {

    val questions = listOf(
        Question(
            "What is the house edge in roulette?",
            listOf("0.5%", "2.7%", "5.26%"),
            "5.26%"
        ),
        Question(
            "How many cards are in a standard deck used in blackjack?",
            listOf("52", "54", "48"),
            "52"
        ),
        Question(
            "What is the highest possible hand in poker?",
            listOf("Royal flush", "Straight flush", "Full house"),
            "Royal flush"
        ),
        Question(
            "What are the kissing kings?",
            listOf(
                "King of clubs and King of spades",
                "King of clubs and King of diamonds",
                "Kind of hearts and King of diamonds"
            ),
            "King of clubs and King of diamonds"
        ),
        Question(
            "What is the difference between an American roulette wheel and a European roulette wheel?",
            listOf(
                "An American wheel has a double zero",
                "The wheels spin in the opposite direction",
                "No difference"
            ),
            "An American wheel has a double zero"
        ),
        Question(
            "How many betting positions are there on a Baccarat table?",
            listOf("fifteen", "fourteen", "nine"),
            "fourteen"
        ),
        Question(
            "What is a dead man's hand?",
            listOf(
                "Aces and eights",
                "Ace high with no pair",
                "Four deuces"
            ),
            "Aces and eights"
        ),
        Question(
            "How many ways are there to make a royal flush in five card stud?",
            listOf("Two", "One", "Four"),
            "Four"
        ),
        Question(
            "In a new deck of cards what order are the suits of the cards in from top to bottom?",
            listOf(
                "Clubs diamonds hearts spades",
                "Hearts clubs diamonds spades",
                "Hearts diamonds clubs spades"
            ),
            "Hearts clubs diamonds spades"
        ),
        Question(
            "What is the only mathematically beatable game in a casino?",
            listOf("Baccarat", "Craps", "Blackjack"),
            "Blackjack"
        ),
        Question(
            "Where is the largest casino in the world?",
            listOf("Nevada", "New Jersey", "Connecticut"),
            "Connecticut"
        ),
        Question(
            "How many cards are in a six deck shoe used for Spanish 21?",
            listOf("288", "240", "312"),
            "288"
        ),
        Question(
            "In craps, what roll is 'little joe from kokomo'?",
            listOf("2", "4", "A 5 made with a 3 and a 2"),
            "4"
        ),
        Question(
            "In card games, what is a holdout used for?",
            listOf(
                "to keep your place in a game",
                "to hold the cards",
                "to cheat"
            ),
            "to cheat"
        ),
        Question(
            "In the game of Keno, how many numbers are drawn each game?",
            listOf("20", "80", "10"),
            "20"
        ),
        Question(
            "In Bingo, what letter is represented by the number 31?",
            listOf("N", "B", "G"),
            "N"
        ),
        Question(
            "In what game can you hop or press, and even win when you're wrong?",
            listOf("Slots", "Blackjack", "Craps"),
            "Craps"
        ),
        Question(
            "How many number combinations are on a pair of dice?",
            listOf("21", "11", "24"),
            "21"
        )
    )
}