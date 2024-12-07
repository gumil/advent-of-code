fun main() {
    data class Equation(
        val result: Long,
        val numbers: List<Long>,
    )

    fun parseInput(input: List<String>): List<Equation> {
        return input.map { line ->
            val (result, numberString) = line.split(": ")
            val numbers = numberString.split(" ").map { it.toLong() }
            Equation(result.toLong(), numbers)
        }
    }

    fun generateCombinations(
        numbers: List<Long>,
        operators: List<String>,
    ): List<Long> {
        val results = mutableListOf<Long>()

        fun backtrack(current: Long, index: Int) {
            if (index >= numbers.size) {
                results.add(current)
                return
            }
            for (operator in operators) {
                when (operator) {
                    "+" -> {
                        backtrack(current + numbers[index], index + 1)
                    }

                    "*" -> {
                        backtrack(current * numbers[index], index + 1)
                    }

                    "||" -> {
                        backtrack((current.toString() + numbers[index].toString()).toLong(), index + 1)
                    }
                }

            }
        }

        // Start the recursion with the first number
        backtrack(numbers[0], 1)
        return results
    }

    fun computeSum(equations: List<Equation>, operators: List<String>): Long {
        return equations.sumOf { equation ->
            val (result, numbers) = equation
            val combinations = generateCombinations(numbers, operators)

            if (combinations.contains(result)) result else 0
        }
    }

    fun part1(input: List<String>): Long {
        val equations = parseInput(input)
        return computeSum(equations, listOf("+", "*"))
    }

    fun part2(input: List<String>): Long {
        val equations = parseInput(input)
        return computeSum(equations, listOf("+", "*", "||"))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2024/Day07_test")
    val part1 = part1(testInput)
    part1.println()
    val part2 = part2(testInput)
    part2.println()
    check(part1 == 3749L)
    check(part2 == 11387L)

    val input = readInput("2024/Day07")
    check(part1(input) == 2299996598890)
    check(part2(input) == 362646859298554)
}
