package aoc2023

import println
import readInput

fun main() {
    fun getNumber(
        input: List<String>,
        i: Int,
        j: Int,
    ): Int? {
        val number = input[i][j]
        if (number.isDigit()) {
            var from = j
            var to = j
            // scan to the left
            while (from > 0 && input[i][from - 1].isDigit()) {
                from--
            }

            // scan to the right
            while (to < input[i].length - 1 && input[i][to + 1].isDigit()) {
                to++
            }

            return input[i].substring(from..to).toInt()
        }
        return null
    }

    fun part1(input: List<String>): Int {
        val numberList = mutableListOf<Int>()
        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, c ->
                if (!c.isDigit() && c != '.') {
                    val numberSet = mutableSetOf<Int>()
                    // upper left
                    if (i > 0 && j > 0) {
                        val number = getNumber(input, i - 1, j - 1)
                        if (number != null) numberSet.add(number)
                    }

                    // upper
                    if (i > 0) {
                        val number = getNumber(input, i - 1, j)
                        if (number != null) numberSet.add(number)
                    }

                    // upper right
                    if (i > 0 && j < line.length) {
                        val number = getNumber(input, i - 1, j + 1)
                        if (number != null) numberSet.add(number)
                    }

                    // left
                    if (j > 0) {
                        val number = getNumber(input, i, j - 1)
                        if (number != null) numberSet.add(number)
                    }

                    // right
                    if (j < line.length) {
                        val number = getNumber(input, i, j + 1)
                        if (number != null) numberSet.add(number)
                    }

                    // lower left
                    if (i < input.size && j > 0) {
                        val number = getNumber(input, i + 1, j - 1)
                        if (number != null) numberSet.add(number)
                    }

                    // lower
                    if (i < input.size) {
                        val number = getNumber(input, i + 1, j)
                        if (number != null) numberSet.add(number)
                    }

                    // lower right
                    if (i < input.size && j < line.length) {
                        val number = getNumber(input, i + 1, j + 1)
                        if (number != null) numberSet.add(number)
                    }
                    numberList.addAll(numberSet)
                }
            }
        }
        return numberList.sum()
    }

    fun part2(input: List<String>): Int {
        val numberList = mutableListOf<Int>()
        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, c ->
                if (c == '*') {
                    val numberSet = mutableSetOf<Int>()
                    // upper left
                    if (i > 0 && j > 0) {
                        val number = getNumber(input, i - 1, j - 1)
                        if (number != null) numberSet.add(number)
                    }

                    // upper
                    if (i > 0) {
                        val number = getNumber(input, i - 1, j)
                        if (number != null) numberSet.add(number)
                    }

                    // upper right
                    if (i > 0 && j < line.length) {
                        val number = getNumber(input, i - 1, j + 1)
                        if (number != null) numberSet.add(number)
                    }

                    // left
                    if (j > 0) {
                        val number = getNumber(input, i, j - 1)
                        if (number != null) numberSet.add(number)
                    }

                    // right
                    if (j < line.length) {
                        val number = getNumber(input, i, j + 1)
                        if (number != null) numberSet.add(number)
                    }

                    // lower left
                    if (i < input.size && j > 0) {
                        val number = getNumber(input, i + 1, j - 1)
                        if (number != null) numberSet.add(number)
                    }

                    // lower
                    if (i < input.size) {
                        val number = getNumber(input, i + 1, j)
                        if (number != null) numberSet.add(number)
                    }

                    // lower right
                    if (i < input.size && j < line.length) {
                        val number = getNumber(input, i + 1, j + 1)
                        if (number != null) numberSet.add(number)
                    }

                    if (numberSet.size >=2) {
                        numberList.add(numberSet.reduce { acc, i -> acc * i })
                    }
                }
            }
        }
        return numberList.sum()
    }

    fun testInput() = listOf(
        "467..114..",
        "...*......",
        "..35..633.",
        "......#...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...$.*....",
        ".664.598..",
    )

    // test if implementation meets criteria from the description, like:
    check(part1(testInput()) == 4361)
    check(part2(testInput()) == 467835)

    val input = readInput("2023/Day03")

    check(part1(input) == 512794)
    check(part2(input) == 67779080)
}
