import kotlin.math.absoluteValue

fun main() {
    fun parseInput(input: List<String>): List<Pair<Int, Int>> {
        val locationPairs = input.map { line ->
            val (loc1, loc2) = line.split("   ")
            loc1.toInt() to loc2.toInt()
        }
        return locationPairs
    }

    fun part1(input: List<String>): Int {
        val locationPairs = parseInput(input)

        val firstLocation = locationPairs.map { it.first }.sorted()
        val secondLocation = locationPairs.map { it.second }.sorted()

        return (0..firstLocation.lastIndex).sumOf { index ->
            (firstLocation[index] - secondLocation[index]).absoluteValue
        }
    }

    fun part2(input: List<String>): Int {
        val locationPairs = parseInput(input)
        val firstLocation = locationPairs.map { it.first }
        val secondLocation = locationPairs.map { it.second }

        return firstLocation.sumOf { location ->
            val multiplier = secondLocation.count { location == it }
            location * multiplier
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2024/Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("2024/Day01")
    check(part1(input) == 1579939)
    check(part2(input) == 20351745)
}
