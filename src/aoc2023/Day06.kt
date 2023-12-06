package aoc2023

import println
import readInput

fun main() {
    fun parse(line: String): List<Int> {
        val (_, numberString) = line.split(":").map { it.trim() }
        return numberString
            .split(Regex(" +"))
            .map { it.toInt() }
    }
    
    fun calculateNumberOfWays(
        time: Long,
        distance: Long,
        ): Long {
        var ways = 0L
        (0..time).forEach { hold ->
            val remainingTime = time - hold
            val actualDistance = hold * remainingTime
            if (actualDistance > distance) {
                ways++
            }
        }
        return ways
    }
    
    fun calculate(
        times: List<Int>,
        distances: List<Int>,
    ): Long {
        return times.mapIndexed { index, time ->
            calculateNumberOfWays(time.toLong(), distances[index].toLong())
        }.reduce { acc, i -> acc * i }
    }
    
    fun part1(input: List<String>): Long {
        val (times, distances) = input.map { line ->
            parse(line)
        }

        return calculate(times, distances)
    }

    fun part2(input: List<String>): Long {
        val (time, distance) = input.map { line ->
            parse(line).map { it.toString() }.reduce { acc, s -> acc + s }.toLong()
        }

        return calculateNumberOfWays(time, distance)
    }

    val testInput = listOf(
        "Time:      7  15   30",
        "Distance:  9  40  200",
    )
    val testPart1 = part1(testInput)
    check(testPart1 == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("2023/Day06")
    val part1 = part1(input)
    part1.println()
    check(part1 == 2612736L)
    val part2 = part2(input)
    part2.println()
    check(part2 == 29891250L)
}
