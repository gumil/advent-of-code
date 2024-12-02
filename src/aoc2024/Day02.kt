import kotlin.math.absoluteValue

fun main() {

    fun isSafe(levels: List<Int>): Boolean {
        var isIncreasing = 0
        levels.reduce { acc, level ->
            val difference = acc - level

            if (difference.absoluteValue !in 1..3) return false

            if (difference > 0) {
                if (isIncreasing < 0) return false
                isIncreasing++
            } else {
                if (isIncreasing > 0) return false
                isIncreasing--
            }

            level
        }
        return true
    }

    fun part1(input: List<String>): Int {
        return input.map { line ->
            val levels = line.split(" ").map { it.toInt() }
            isSafe(levels)
        }.count { it }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val levels = line.split(" ").map { it.toInt() }
            val safe = isSafe(levels)
            if (!safe) {
                levels.forEachIndexed { index, _ ->
                    val newLevel = levels.toMutableList().apply {
                        removeAt(index)
                    }
                    val isSafe = isSafe(newLevel)
                    if (isSafe) return@map true
                }
                return@map false
            }
            safe
        }.count { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2024/Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("2024/Day02")
    check(part1(input) == 526)
    check(part2(input) == 566)
}
