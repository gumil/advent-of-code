package aoc2023

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val digits = mutableListOf<String>()
            line.forEach { c ->
                if (c.isDigit()) {
                    digits.add(c.toString())
                }
            }
            (digits.first() + digits.last()).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val numberWords = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )

        return input.sumOf { line ->
            val digits = mutableListOf<String>()
            line.forEachIndexed { index, c ->
                if (c.isDigit()) {
                    digits.add(c.toString())
                } else {
                    val sub = line.substring(startIndex = index)
                    for ((word, num) in numberWords) {
                        if (sub.startsWith(word)) {
                            digits.add(num)
                        }
                    }
                }
            }

            (digits.first() + digits.last()).toInt()
        }
    }

    fun testInput() = listOf(
        "1abc2",
        "pqr3stu8vwx",
        "a1b2c3d4e5f",
        "treb7uchet",
    )

    fun testInput2() = listOf(
        "two1nine",
        "eightwothree",
        "abcone2threexyz",
        "xtwone3four",
        "4nineeightseven2",
        "zoneight234",
        "7pqrstsixteen",
    )

    // test if implementation meets criteria from the description, like:
    check(part1(testInput()) == 142)
    val part2 = part2(testInput2())
    check(part2 == 281) {
        "Actual = $part2"
    }

    val input = readInput("aoc2023/Day01")

    check(part1(input) == 54601)
    check(part2(input) == 54078)
}
