package aoc2023

import println
import readInput
import kotlin.math.abs

fun main() {
    data class Point(
            val row: Int,
            val column: Int,
    )

    fun computeDistance(origin: Int, destination: Int, values: IntArray): Int {
        var current = origin
        var sum = 0
        if (origin < destination) {
            while (current < destination) {
                val value = values[current++]
                sum += value
            }

        }
        if (origin > destination) {
            while (current > destination) {
                sum += values[current--]
            }
        }
        return sum
    }

    fun part1(input: List<String>, multiplier: Int): Long {
        val points = mutableListOf<Point>()
        val rows = IntArray(input.size)
        val columnSize = input.first().length
        val columns = IntArray(columnSize)
        var pairs = 0L
        for (i in input.indices) {
            val line = input[i]
            var spaceCount = multiplier
            for (j in line.indices) {
                if (line[j] == '#') {
                    points.add(Point(i, j))
                    spaceCount = 1
                }
            }
            rows[i] = spaceCount
        }

        for (i in 0 until columnSize) {
            var spaceCount = multiplier
            for (j in input.indices) {
                if (input[j][i] == '#') {
                    spaceCount = 1
                }
            }
            columns[i] = spaceCount
        }

        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val a = points[i]
                val b = points[j]
                val horizontalDistance = computeDistance(a.column, b.column, columns)
                val verticalDistance = computeDistance(a.row, b.row, rows)

                val distance = horizontalDistance + verticalDistance
                pairs += distance
            }
        }

        return pairs
    }

    fun part2(input: List<String>): Long {
        return part1(input, 1_000_000)
    }

    val testInput = listOf(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#.....",
    )
    check(part1(testInput, 2) == 374L)
    check(part1(testInput, 10) == 1030L)
    check(part1(testInput, 100) == 8410L)

    val input = readInput("2023/Day11")
    val part1 = part1(input, 2)
    part1.println()
    check(part1 == 9556896L)
    val part2 = part2(input)
    part2.println()
    check(part2 == 685038186836L)
}
