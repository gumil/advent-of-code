package aoc2021

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val map = mutableMapOf<Int, MutableList<Char>>()
        val binarySize = input.first().length
        input.forEach { binary ->
            binary.toCharArray().forEachIndexed { index, c ->
                map[index]?.add(c) ?: run {
                    map[index] = mutableListOf(c)
                }
            }
        }

        val gamma = CharArray(binarySize)
        val epsilon = CharArray(binarySize)
        map.entries.forEach { entry ->
            val (ones, zeros) = entry.value.partition { it == '1' }
            if (ones.size > zeros.size) {
                gamma[entry.key] = '1'
                epsilon[entry.key] = '0'
            } else {
                gamma[entry.key] = '0'
                epsilon[entry.key] = '1'
            }
        }
        val gammaDecimal = gamma.joinToString(separator = "").toInt(2)
        val epsilonDecimal = epsilon.joinToString(separator = "").toInt(2)
        return gammaDecimal * epsilonDecimal
    }

    fun getRating(
        input: List<String>,
        shouldGetLarger: Boolean,
    ): Int {
        var bitCriteria = input
        var index = 0
        while (bitCriteria.size > 1) {
            val (ones, zeroes) = bitCriteria.partition { it[index] == '1' }

            bitCriteria = if (ones.size >= zeroes.size) {
                if (shouldGetLarger) ones else zeroes
            } else {
                if (shouldGetLarger) zeroes else ones
            }
            index++
        }
        return bitCriteria.first().toInt(2)
    }

    fun part2(input: List<String>): Int {
        val generator = getRating(input, true)
        val scrubber = getRating(input, false)
        return generator * scrubber
    }

    val testInput = readInput("aoc2021/Day03")
    check(part1(testInput) == 3901196)
    check(part2(testInput) == 4412188)
}
