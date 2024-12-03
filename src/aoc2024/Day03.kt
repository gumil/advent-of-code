fun main() {
    fun parseExpression(
        corruptedMemory: String,
        initialMultEnabled: Boolean,
        ignoreDoAndDont: Boolean,
    ): Pair<Int, Boolean> {
        // Regex patterns
        val mulPattern = Regex("""mul\((\d+),(\d+)\)""")
        val doPattern = Regex("""do\(\)""")
        val dontPattern = Regex("""don't\(\)""")

        // Parse the input string
        val tokens = Regex("""mul\(\d+,\d+\)|do\(\)|don't\(\)""").findAll(corruptedMemory).map { it.value }

        // Initialize variables
        var mulEnabled = initialMultEnabled // At the start, `mul` is enabled
        var sum = 0

        // Process each token
        for (token in tokens) {
            when {
                doPattern.matches(token) -> mulEnabled = true
                dontPattern.matches(token) -> mulEnabled = false
                mulPattern.matches(token) && (mulEnabled || ignoreDoAndDont) -> {
                    // Extract numbers from the mul instruction and add the result
                    val (x, y) = mulPattern.find(token)!!.destructured
                    sum += x.toInt() * y.toInt()
                }
            }
        }

        // Output the result
        return sum to mulEnabled
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { parseExpression(it, initialMultEnabled = true, ignoreDoAndDont = true).first }
    }

    fun part2(input: List<String>): Int {
        var mulEnabled = true
        return input.sumOf {
            val (sum, enabled) = parseExpression(it, initialMultEnabled = mulEnabled, ignoreDoAndDont = false)
            mulEnabled = enabled
            sum
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"
    check(parseExpression(testInput, initialMultEnabled = true, ignoreDoAndDont = true).first == 161)
    val testInput2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
    check(parseExpression(testInput2, initialMultEnabled = true, ignoreDoAndDont = false).first == 48)

    val input = readInput("2024/Day03")
    check(part1(input) == 161085926)
    check(part2(input) == 82045421)
}
