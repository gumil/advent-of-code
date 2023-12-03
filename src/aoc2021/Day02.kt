package aoc2021

import readInput

enum class Direction {
    FORWARD, DOWN, UP
}

fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0

        input.forEach { movement ->
            val split = movement.split(" ")
            val direction = Direction.valueOf(split.first().uppercase())
            val distance = split.last().toInt()

            when (direction) {
                Direction.FORWARD -> horizontal += distance
                Direction.DOWN -> depth += distance
                Direction.UP -> depth -= distance
            }
        }

        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var aim = 0
        var horizontal = 0
        var depth = 0

        input.forEach { movement ->
            val split = movement.split(" ")
            val direction = Direction.valueOf(split.first().uppercase())
            val distance = split.last().toInt()

            when (direction) {
                Direction.FORWARD -> {
                    horizontal += distance
                    depth += (aim * distance)
                }
                Direction.DOWN -> {
                    aim += distance
                }
                Direction.UP -> {
                    aim -= distance
                }
            }
        }

        return horizontal * depth
    }

    val testInput = readInput("aoc2021/Day02")
    check(part1(testInput) == 1698735)
    check(part2(testInput) == 1594785890)
}
