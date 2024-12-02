package aoc2023

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        input.forEach { line ->
            val (conditions, damaged) = line.split(" ")
            val damagedGroup = damaged.split(",").map { it.toInt() }
            val possibleRanges = mutableListOf<IntRange>()
            val fixedRanges = mutableListOf<IntRange>()
            var start = 0
            var currentChar = ' '
            var currentList: MutableList<IntRange>? = null
            conditions.forEachIndexed { index, c ->
                if (c != currentChar || index == conditions.length - 1) {
                    if (index == conditions.length - 1 && c == currentChar) {
                        currentList?.add(start .. index)
                    } else {
                        currentList?.add(start until index)
                    }
                    currentChar = c
                    start = index
                    currentList = when (c) {
                        '?' -> possibleRanges
                        '#' -> fixedRanges
                        else -> null
                    }
                }
            }
            
            damagedGroup.forEach { damaged ->
                possibleRanges.forEach { range ->
                    
                }
                fixedRanges.size
            }
            
            println("current = $line")
            println(fixedRanges)
            println(possibleRanges)
        }
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = listOf(
            "???.### 1,1,3",
            ".??..??...?##. 1,1,3",
            "?#?#?#?#?#?#?#? 1,3,1,6",
            "????.#...#... 4,1,1",
            "????.######..#####. 1,6,5",
            "?###???????? 3,2,1",
    )
    check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
