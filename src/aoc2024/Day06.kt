fun main() {
    val directions = listOf(
        -1 to 0, // up
        0 to 1, // right
        1 to 0, // down
        0 to -1, // left
    )

    fun turnRight(direction: Pair<Int, Int>): Pair<Int, Int> {
        var index = directions.indexOf(direction) + 1
        if (index == directions.size) {
            index = 0
        }
        return directions[index]
    }


    fun traverse(input: Array<CharArray>): Pair<Set<Pair<Int, Int>>, Boolean> {
        val row = input.indexOfFirst { it.contains('^') }
        val column = input[row].indexOf('^')

        var direction = -1 to 0
        var loc = row to column
        val visitedLocationAndDirection = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        var hasLoop = false
        while (true) {
            if (!visitedLocationAndDirection.add(loc to direction)) {
                hasLoop = true
                break
            }
            val nextRow = loc.first + direction.first
            val nextColumn = loc.second + direction.second
            val nextToken = input.getOrNull(nextRow)?.getOrNull(nextColumn) ?: break
            if (nextToken == '#') {
                direction = turnRight(direction)
            } else {
                loc = nextRow to nextColumn
            }
        }
        return visitedLocationAndDirection.map { it.first }.toSet() to hasLoop
    }

    fun part1(stringInput: List<String>): Int {
        val input = stringInput.map { it.toCharArray() }.toTypedArray()
        return traverse(input).first.size
    }


    fun part2(stringInput: List<String>): Int {
        val input = stringInput.map { it.toCharArray() }.toTypedArray()
        var loops = 0
        val obstructions = traverse(input).first
        obstructions.forEach { (r, c) ->
            if (input[r][c] == '.') {
                val newInput = stringInput.map { it.toCharArray() }.toTypedArray()
                newInput[r][c] = '#'
                val hasLoop = traverse(newInput).second
                if (hasLoop) {
                    loops++
                }
            }
        }
        return loops
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2024/Day06_test")
    val part1 = part1(testInput)
    part1.println()
    val part2 = part2(testInput)
    part2.println()
    check(part1 == 41)
    check(part2 == 6)

    val input = readInput("2024/Day06")
    check(part1(input) == 4656)
    check(part2(input) == 1575)
}
