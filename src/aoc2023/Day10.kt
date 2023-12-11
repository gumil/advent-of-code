package aoc2023

import println
import readInput


fun main() {
    data class Point(
            val y: Int,
            val x: Int,
    )

    fun traverse(input: List<String>, points: List<Point>, visitedPoints: List<Point>): Pair<Int, List<Point>> {
        var currentPoints = points
        val currentVisitedPoints = visitedPoints.toMutableList()
        var traversal = 0
        do {
            val newPoints = mutableListOf<Point>()
            traversal++
            currentPoints.forEach { point ->
                val x = point.x
                val y = point.y

                val c = input[y][x]
                currentVisitedPoints.add(point)
                val (dir1, dir2) = when (c) {
                    '|' -> {
                        val north = Point(x = x, y = y - 1)
                        val south = Point(x = x, y = y + 1)
                        north to south
                    }

                    '-' -> {
                        val west = Point(x = x - 1, y = y)
                        val east = Point(x = x + 1, y = y)
                        west to east
                    }

                    'L' -> {
                        val north = Point(x = x, y = y - 1)
                        val east = Point(x = x + 1, y = y)
                        north to east
                    }

                    'J' -> {
                        val north = Point(x = x, y = y - 1)
                        val west = Point(x = x - 1, y = y)
                        north to west
                    }

                    '7' -> {
                        val south = Point(x = x, y = y + 1)
                        val west = Point(x = x - 1, y = y)
                        south to west
                    }

                    'F' -> {
                        val south = Point(x = x, y = y + 1)
                        val east = Point(x = x + 1, y = y)
                        south to east
                    }

                    else -> {
                        null to null
                    }
                }

                if (dir1 != null && !currentVisitedPoints.contains(dir1)) newPoints.add(dir1)
                if (dir2 != null && !currentVisitedPoints.contains(dir2)) newPoints.add(dir2)
            }
            currentPoints = newPoints
        } while (currentPoints.isNotEmpty())
        return traversal to visitedPoints
    }

    fun getConnectedPoints(input: List<String>, point: Point): List<Point> {
        val x = point.x
        val y = point.y
        val line = input.first()
        val points = mutableListOf<Point>()
        if (y > 0) {
            val c = input[y - 1][x]
            if (c == '|' || c == '7' || c == 'F') points.add(Point(y - 1, x))
        }
        if (y < line.length) {
            val c = input[y + 1][x]
            if (c == '|' || c == 'L' || c == 'J') points.add(Point(y + 1, x))
        }
        if (x > 0) {
            val c = input[y][x - 1]
            if (c == '-' || c == 'L' || c == 'F') points.add(Point(y, x - 1))
        }
        if (x < input.size) {
            val c = input[y][x + 1]
            if (c == '-' || c == 'J' || c == '7') points.add(Point(y, x + 1))
        }
        return points
    }

    fun traverse(input: List<String>): Pair<Int, List<Point>> {
        var root = Point(0, 0)
        row@ for (i in input.indices) {
            val line = input[i]
            for (j in line.indices) {
                if (line[j] == 'S') {
                    root = Point(i, j)
                    break@row
                }
            }
        }
        return traverse(input, getConnectedPoints(input, root), listOf(root))
    }

    fun part2(input: List<String>): Int {
        val traverse = traverse(input)
        val tiles = mutableListOf<Point>()
        for (i in input.indices) {
            val line = input[i]
            for (j in line.indices) {
                if (line[j] == '.') {
                    tiles.add(Point(i, j))
                }
            }
        }
        return 0
    }

    val squareInput = listOf(
            ".....",
            ".S-7.",
            ".|.|.",
            ".L-J.",
            ".....",
    )
    val squareInputWithExtras = listOf(
            "-L|F7",
            "7S-7|",
            "L|7||",
            "-L-J|",
            "L|-JF",
    )

    val complexInput = listOf(
            "..F7.",
            ".FJ|.",
            "SJ.L7",
            "|F--J",
            "LJ...",
    )


    // test if implementation meets criteria from the description, like:
    check(traverse(squareInput).first == 4)
    check(traverse(squareInputWithExtras).first == 4)
    check(traverse(complexInput).first == 8)

    val input = readInput("2023/Day10")
    val traverse = traverse(input)
    val part1 = traverse.first
    part1.println()
    val part2 = part2(input)
    part2.println()
    check(part1 == 7030)
    check(part2 == 285)
}
