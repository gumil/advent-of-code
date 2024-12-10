fun main() {
    fun computeTrailheadScore(
        startPosition: Pair<Int, Int>,
        input: List<String>,
        getRating: Boolean,
    ): Int {
        val directions = listOf(
            -1 to 0, // up
            0 to 1, // right
            1 to 0, // down
            0 to -1, // left
        )

        val ninePositionSet = mutableSetOf<Pair<Int, Int>>()
        var rating = 0
        fun backtrack(position: Pair<Int, Int>, direction: Pair<Int, Int>) {
            val currentHeight = input.getOrNull(position.first)?.getOrNull(position.second) ?: return

            if (currentHeight == '9' && !getRating) {
                ninePositionSet.add(position)
                return
            }

            val nextRow = position.first + direction.first
            val nextColumn = position.second + direction.second
            val nextHeight = input.getOrNull(nextRow)?.getOrNull(nextColumn) ?: return

            if (nextHeight == currentHeight + 1) {

                if (nextHeight == '9' && getRating) {
                    rating++
                    return
                }

                directions.forEach { (x, y) ->
                    backtrack(
                        position = nextRow to nextColumn,
                        direction = x to y,
                    )
                }

            }
        }

        directions.forEach { direction ->
            backtrack(
                position = startPosition.first to startPosition.second,
                direction = direction,
            )
        }

        return if (getRating) rating else ninePositionSet.size
    }

    fun part1(input: List<String>): Int {
        val zeroPositions = input.filter { it.contains("0") }

        return zeroPositions.sumOf { stringWithZero ->
            val startingRow = input.indexOf(stringWithZero)

            var computeTrailheadScore = 0
            stringWithZero.forEachIndexed { index, c ->
                if (c == '0') {
                    computeTrailheadScore += computeTrailheadScore(
                        startPosition = startingRow to index,
                        input = input,
                        getRating = false,
                    )
                }

            }

            computeTrailheadScore
        }
    }

    fun part2(input: List<String>): Int {
        val zeroPositions = input.filter { it.contains("0") }

        return zeroPositions.sumOf { stringWithZero ->
            val startingRow = input.indexOf(stringWithZero)

            var computeTrailheadScore = 0
            stringWithZero.forEachIndexed { index, c ->
                if (c == '0') {
                    val score = computeTrailheadScore(
                        startPosition = startingRow to index,
                        input = input,
                        getRating = true,
                    )
                    computeTrailheadScore += score
                }

            }

            computeTrailheadScore
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2024/Day10_test")
    val part1 = part1(testInput)
    part1.println()
    check(part1 == 36)
    val part2 = part2(testInput)
    part2.println()
    check(part2 == 81)

    val input = readInput("2024/Day10")
    check(part1(input) == 617)
    check(part2(input) == 1477)
}
