package aoc2023

import println
import readInput

fun main() {
    fun parse(input: String): List<Int> {
        return input.split(" ")
            .map { it.toInt() }
    }
    
    fun extrapolate(history: List<Int>, isBackwards: Boolean = false): List<Int> {
        var sum = -1
        var currentHistory = history
        val result = mutableListOf<Int>()
        while (sum != 0) {
            val difference = mutableListOf<Int>()
            for (i in 0 until currentHistory.size - 1) {
                difference.add(currentHistory[i + 1] - currentHistory[i])
            }
            sum = difference.sum()
            currentHistory = difference
            result.add(if (isBackwards) difference.first() else difference.last())
        }
        
        return result
    }
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val history = parse(line)
            history.last() + extrapolate(history).sum()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val history = parse(line)
            history.first() - extrapolate(history, isBackwards = true).reduceRight { acc, i -> acc - i}
        }
    }

    val testInput = listOf(
            "0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45",
    )
    check(part1(testInput) == 114)
    val part2Test = part2(testInput)
    println(part2Test)
    check(part2Test == 2)

    val input = readInput("2023/Day09")
    val part1 = part1(input)
    part1.println()
    val part2 = part2(input)
    part2.println()
    
    check(part1 == 1479011877)
    check(part2 == 973)
}
