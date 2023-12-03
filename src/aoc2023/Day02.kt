package aoc2023

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var sumOfGames = 0
        input.forEach { line ->
            val (game, allRounds) = line.split(":", limit = 2)
            val gameNumber = game.split(" ")[1].toInt()
            val rounds = allRounds.split(";")
            val isPossible = rounds.all { round ->
                val cubes = round.split(", ")
                    .map { it.trim() }
                cubes.all { cube ->
                    val (count, color) = cube.split(" ")
                    when (color) {
                        "red" -> count.toInt() <= 12
                        "green" -> count.toInt() <= 13
                        "blue" -> count.toInt() <= 14
                        else -> false
                    }
                }
            }

            if (isPossible) {
                sumOfGames += gameNumber
            }
        }
        return sumOfGames
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (_, allRounds) = line.split(":", limit = 2)
            val rounds = allRounds.split(";")
            var maxRed = 0
            var maxGreen = 0
            var maxBlue = 0
            rounds.forEach { round ->
                val cubes = round.split(", ")
                    .map { it.trim() }
                cubes.forEach { cube ->
                    val (count, color) = cube.split(" ")
                    val countInt = count.toInt()
                    when (color) {
                        "red" -> if (countInt > maxRed) maxRed = countInt
                        "green" -> if (countInt > maxGreen) maxGreen = countInt
                        "blue" -> if (countInt > maxBlue) maxBlue = countInt
                    }
                }
            }
            maxRed * maxGreen * maxBlue
        }
    }

    fun testInput() = listOf(
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green",
    )

    // test if implementation meets criteria from the description, like:
    check(part1(testInput()) == 8)
    check(part2(testInput()) == 2286)

    val input = readInput("2023/Day02")
    check(part1(input) == 2283)
    check(part2(input) == 78669)
}
