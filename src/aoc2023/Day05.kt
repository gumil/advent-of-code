package aoc2023

import kotlinx.coroutines.*
import println
import readInput
import kotlin.time.measureTime

fun main() = runBlocking {
    fun createMap(input: List<String>): Map<LongRange, LongRange> {
        return input.associate { line ->
            val (destination, source, length) = line.split(" ").map { it.toLong() }
            val actualLength = length - 1
            source..(source + actualLength) to destination..(destination + actualLength)
        }
    }

    fun findInMap(seed: Long, map: Map<LongRange, LongRange>): Long {
        // find range
        map.forEach { (key, value) ->
            if (key.contains(seed)) {
                val index = seed - key.first
                return value.first + index
            }
        }
        return seed
    }

    fun getMappings(input: List<String>): List<Map<LongRange, LongRange>> {
        val list = mutableListOf<Map<LongRange, LongRange>>()
        val currentList = mutableListOf<String>()
        input.forEach { line ->
            when {
                line.contains("seeds: ") -> {
                    // do nothing
                }
                line.contains("-to-") -> {
                    // do nothing
                }
                line.isEmpty() -> {
                    val map = createMap(currentList)
                    currentList.clear()
                    list.add(map)
                }
                else -> {
                    currentList.add(line)
                }
            }
        }
        return list
    }

    fun part1(input: List<String>): Long {
        val seeds = mutableListOf<Long>()
        input.forEach { line ->
            when {
                line.contains("seeds: ") -> {
                    val (_, seedsString) = line.split(": ")
                    val seedsList = seedsString.split(" ")
                        .map { it.toLong() }
                    seeds.addAll(seedsList)
                    return@forEach
                }
            }
        }

        val mappings = getMappings(input)

        return seeds.minOf { seed ->
            var result = seed
            mappings.forEach {
                result = findInMap(result, it)
            }
            result
        }
    }

    suspend fun part2(input: List<String>): Long {
        val seeds = mutableListOf<LongRange>()
        input.forEach { line ->
            when {
                line.contains("seeds: ") -> {
                    val (_, seedsString) = line.split(": ")
                    val seedsList = seedsString.split(" ")
                        .map { it.toLong() }
                    for (i in seedsList.indices step 2) {
                        seeds.add(seedsList[i] until seedsList[i] + seedsList[i + 1])
                    }
                    return@forEach
                }
            }
        }

        val mappings = getMappings(input)
        return seeds.minOf { seedRange ->
            val mid = (seedRange.first + seedRange.last) / 2
            val midLeft = async(Dispatchers.IO) {
                (seedRange.first..mid).minOf { seed ->
                    var result = seed
                    mappings.forEach {
                        result = findInMap(result, it)
                    }
                    result
                }
            }

            val midRight = async(Dispatchers.IO) {
                (mid + 1..seedRange.last).minOf { seed ->
                    var result = seed
                    mappings.forEach {
                        result = findInMap(result, it)
                    }
                    result
                }
            }

            listOf(midLeft.await(), midRight.await()).min()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2023/Day05_test")
    check(part1(testInput) == 35L)
    GlobalScope.launch {
        check(part2(testInput) == 46L)
    }.join()

    val input = readInput("2023/Day05")
    part1(input).println()
    GlobalScope.launch(Dispatchers.IO) {
        measureTime {
            val part2 = part2(input)
            part2.println()
            check(part2 == 136096660L)
        }.println()
    }.join()


    check(part1(input) == 825516882L)
}
