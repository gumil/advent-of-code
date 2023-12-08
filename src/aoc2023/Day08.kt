package aoc2023

import println
import readInput

data class Node(
        val key: String,
        val left: String,
        val right: String,
)

enum class Direction { LEFT, RIGHT }

fun main() {

    fun traverse(
            nodes: List<Node>,
            moves: List<Direction>,
    ): Int {
        var node = nodes.first { it.key == "AAA" }
        var i = 0
        while (node.key != "ZZZ") {
            val direction = moves[i++ % moves.size]
            node = when (direction) {
                Direction.LEFT -> nodes.first { it.key == node.left }
                Direction.RIGHT -> nodes.first { it.key == node.right }
            }
        }
        return i
    }

    fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
    fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)

    fun traversePart2(
            nodes: List<Node>,
            moves: List<Direction>,
    ): Long {
        val startingNodes = nodes.filter { it.key.endsWith('A') }
        return startingNodes.map { node ->
            var i = 0L
            var newNode = node
            while (!newNode.key.endsWith('Z')) {
                val direction = moves[(i++ % moves.size).toInt()]
                newNode = when (direction) {
                    Direction.LEFT -> nodes.first { it.key == newNode.left }
                    Direction.RIGHT -> nodes.first { it.key == newNode.right }
                }
            }
            i
        }.reduce { acc, i -> lcm(acc, i) }

    }

    fun moves(input: String): List<Direction> {
        return input.map { c ->
            when (c) {
                'L' -> Direction.LEFT
                'R' -> Direction.RIGHT
                else -> error("no direction")
            }
        }
    }

    fun nodesList(input: List<String>): List<Node> {
        return input.drop(2).map { line ->
            val (parent, children) = line.split(" = ")
            val (leftNode, rightNode) = children
                    .substring(1 until children.length - 1)
                    .split(", ")
            Node(parent, leftNode, rightNode)
        }
    }

    fun part1(input: List<String>): Int {
        val moves = moves(input.first())
        val nodesList = nodesList(input)

        return traverse(nodesList, moves)
    }

    fun part2(input: List<String>): Long {
        val moves = moves(input.first())
        val nodesList = nodesList(input)

        return traversePart2(nodesList, moves)
    }

    val testInput = listOf(
            "RL",
            "",
            "AAA = (BBB, CCC)",
            "BBB = (DDD, EEE)",
            "CCC = (ZZZ, GGG)",
            "DDD = (DDD, DDD)",
            "EEE = (EEE, EEE)",
            "GGG = (GGG, GGG)",
            "ZZZ = (ZZZ, ZZZ)",
    )

    val testInput2 = listOf(
            "LLR",
            "",
            "AAA = (BBB, BBB)",
            "BBB = (AAA, ZZZ)",
            "ZZZ = (ZZZ, ZZZ)",
    )

    val testInput3 = listOf(
            "LR",
            "",
            "11A = (11B, XXX)",
            "11B = (XXX, 11Z)",
            "11Z = (11B, XXX)",
            "22A = (22B, XXX)",
            "22B = (22C, 22C)",
            "22C = (22Z, 22Z)",
            "22Z = (22B, 22B)",
            "XXX = (XXX, XXX)",
    )
    check(part1(testInput) == 2)
    val testPart1Test2 = part1(testInput2)
    testPart1Test2.println()
    check(testPart1Test2 == 6)
    val testPart2 = part2(testInput3)
    testPart2.println()
    check(testPart2 == 6L)

    val input = readInput("2023/Day08")
    val part1 = part1(input)
    part1.println()
    val part2 = part2(input)
    part2.println()
    check(part1 == 23147)
    check(part2 == 22289513667691)
}
