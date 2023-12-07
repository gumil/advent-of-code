package aoc2023

import println
import readInput

fun main() {
    val cards = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    val cards2 = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

    data class HandPoints(
        val hand: String,
        val point: Int,
        val jokerAsWildCard: Boolean = false,
    ) : Comparable<HandPoints> {
        override fun compareTo(other: HandPoints): Int {
            val cardValues = if (jokerAsWildCard) cards2 else cards
            if (point == other.point) {
                hand.forEachIndexed { index, c ->
                    val c1 = other.hand[index]
                    val c1Index = cardValues.indexOf(c1)
                    val cIndex = cardValues.indexOf(c)
                    if (cIndex != c1Index) {
                        return cIndex.compareTo(c1Index)
                    }
                }
            }
            return point.compareTo(other.point)
        }
    }

    fun calculatePoints(hand: String): Int {
        val map = hand.groupBy { it }
                .mapValues { (_, list) -> list.size }

        return when {
            // Five of a kind
            map.keys.size == 1 -> 7
            // Four of a kind
            map.keys.size == 2 && map.values.max() == 4 -> 6
            // Full house
            map.keys.size == 2 && map.values.max() == 3 -> 5
            // Three of a kind
            map.keys.size == 3 && map.values.max() == 3 -> 4
            // Two pair
            map.keys.size == 3 && map.values.max() == 2 -> 3
            // One pair
            map.keys.size == 4 -> 2
            // High card
            map.keys.size == 5 -> 1
            else -> 0
        }
    }
    
    fun calculateWinnings(input: List<String>, jokerAsWildCard: Boolean): Int {
        val map = input.associate { line ->
            val (hand, bid) = line.split(" ")
            hand to bid
        }

        return map.keys
            .associateWith { hand ->
                if (hand.contains('J') && jokerAsWildCard) {
                    hand.filter { it != 'J' }
                        .map {
                            calculatePoints(hand.replace('J', it))
                        }
                        .maxOrNull() ?: 7
                } else {
                    calculatePoints(hand)
                }
            }
            .toList()
            .map { (hand, point) -> HandPoints(hand, point, jokerAsWildCard) }
            .sorted()
            .map { map[it.hand]!!.toInt() }
            .reduceIndexed { index, acc, bid ->
                acc + (index + 1) * bid
            }
    }

    fun part1(input: List<String>): Int {
        return calculateWinnings(input, false)
    }

    fun part2(input: List<String>): Int {
        return calculateWinnings(input, true)
    }

    val testInput = listOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483",
    )

    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)

    val input = readInput("2023/Day07")
    val part1 = part1(input)
    part1.println()
    val part2 = part2(input)
    part2.println()

    check(part1 == 250058342)
    check(part2 == 250506580)
}
