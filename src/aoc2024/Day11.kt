fun main() {
    fun replaceEvenDigits(digits: String): Pair<String, String> {
        val length = digits.length
        val (first, second) = digits.chunked(length / 2)
        return first to second
    }

    val map = mutableMapOf<Pair<Long, Int>, Long>()

    fun transformNumber(number: Long, iterations: Int): Long {
        val s = number.toString()

        if (iterations == 0) return 1

        val cachedBlink = map[number to iterations]

        if (cachedBlink != null) {
            return cachedBlink
        }

        val result = when {
            number == 0L -> transformNumber(1, iterations - 1)
            s.length % 2 == 0 -> {
                val (first, second) = replaceEvenDigits(s)
                transformNumber(first.toLong(), iterations - 1) + transformNumber(second.toLong(), iterations - 1)
            }
            else -> transformNumber(number * 2024, iterations - 1)
        }

        map[number to iterations] = result

        return result
    }

    fun part1(input: List<String>): Long {
        val inputList = input.map { it.toLong() }
        return inputList.sumOf { transformNumber(it, 25) }
    }

    fun part2(input: List<String>): Long {
        val inputList = input.map { it.toLong() }
        return inputList.sumOf { transformNumber(it, 75) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = "125 17".split(" ")
    val part1 = part1(testInput)
    part1.println()
    check(part1 == 55312L)

    val part2 = part2(testInput)
    part2.println()

    val input = "773 79858 0 71 213357 2937 1 3998391".split(" ")
    check(part1(input) == 199982L)
    check(part2(input) == 237149922829154L)
}
