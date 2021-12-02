fun main() {
    fun part1(input: List<Int>): Int {
        var increasedCounter = 0
        var currentMeasurement = input.first().toInt()

        input
            .forEach { measurement ->
                if (measurement > currentMeasurement) {
                    increasedCounter++
                }
                currentMeasurement = measurement
            }
        return increasedCounter
    }

    fun part2(input: List<Int>): Int {
        val map = mutableMapOf<Int, Int>()

        for (i in 0 until (input.size - 2)) {
            map[i] = input[i] + input[i + 1] + input[i + 2]
        }

        return part1(map.values.toList())
    }

    val testInput = readInput("Day01_test").map { it.toInt() }
    check(part1(testInput) == 1766)
    check(part2(testInput) == 1797)
}
