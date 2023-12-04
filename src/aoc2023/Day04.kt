package aoc2023

import println
import readInput
import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (_, nums) = line.split(": ", limit = 2)
            val (winString, cardString) = nums.split(" | ", limit = 2)
            val winningNumbers = winString.split(" ")
                .filter { it.isNotBlank() }
                .map { it.trim().toInt() }
            val cardNumbers = cardString.split(" ")
                .filter { it.isNotBlank() }
                .map { it.trim().toInt() }

            val numbersWon = cardNumbers.filter { winningNumbers.contains(it) }
            2f.pow(numbersWon.size - 1).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val scratchCards = IntArray(input.size) { 1 }
        input.forEachIndexed { index, line ->
            val (_, nums) = line.split(": ", limit = 2)
            val (winString, cardString) = nums.split(" | ", limit = 2)
            val winningNumbers = winString.split(" ")
                .filter { it.isNotBlank() }
                .map { it.trim().toInt() }
            val cardNumbers = cardString.split(" ")
                .filter { it.isNotBlank() }
                .map { it.trim().toInt() }

            val numbersWon = cardNumbers.filter { winningNumbers.contains(it) }
            val startIndex = index + 1
            val endIndex = startIndex + numbersWon.size
            val currentMultiplier = scratchCards[index]
            for (i in startIndex until endIndex) {
                scratchCards[i] = scratchCards[i] + currentMultiplier
            }
        }
        return scratchCards.sum()
    }

    val testInput = listOf(
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
    )

    // test if implementation meets criteria from the description, like:
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)


    val input = readInput("2023/Day04")
    part1(input).println()
    part2(input).println()

    check(part1(input) == 26443)
    check(part2(input) == 6284877)
}
