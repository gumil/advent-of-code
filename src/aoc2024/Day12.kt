import kotlin.math.tan

fun main() {
    fun computeAreaAndPerimeter(input: List<String>, withSides: Boolean): Int {
        data class Location(
            val row: Int,
            val column: Int,
        )

        data class Key(val point: Int, val direction: Pair<Int, Int>)
        fun Pair<Location, Pair<Int, Int>>.toKeyRows() = Key(first.row, second)
        fun Pair<Location, Pair<Int, Int>>.toKeyColumns() = Key(first.column, second)

        data class Sides(
            val vertical: Set<Pair<Location, Pair<Int, Int>>>,
            val horizontal: Set<Pair<Location, Pair<Int, Int>>>,
        ) {
            val numOfSides = vertical.size + horizontal.size

            fun getPerimeter(): Int {
                var ctr = 0
                val verticalSides = vertical.groupBy { it.toKeyRows() }
                val horizontalSides = horizontal.groupBy { it.toKeyColumns() }

                verticalSides.forEach { (key, points) ->
                    ctr++
                    val sorted = points.sortedBy { it.first.column }

                    for (i in 0 until sorted.lastIndex) {
                        if (sorted[i].first.column + 1 != sorted[i + 1].first.column) {
                            ctr++
                        }
                    }
                }

                horizontalSides.forEach { (key, points) ->
                    ctr++
                    val sorted = points.sortedBy { it.first.row }

                    for (i in 0 until sorted.lastIndex) {
                        if (sorted[i].first.row + 1 != sorted[i + 1].first.row) {
                            ctr++
                        }
                    }
                }
                return ctr
            }
        }

        val charArr = input.toCharArray()
        val areaMap = mutableMapOf<Pair<Char, Pair<Int, Int>>, Int>()
        val perimeterMap = mutableMapOf<Pair<Char, Pair<Int, Int>>, Int>()
        val sides = mutableMapOf<Pair<Char, Pair<Int, Int>>, Sides>()

        val directions = listOf(
            -1 to 0, // up
            0 to 1, // right
            1 to 0, // down
            0 to -1, // left
        )
        fun checkAdjacent(key: Pair<Char, Pair<Int, Int>>, c: Char, location: Pair<Int, Int>): Int {
            return directions.count { direction ->
                val (x, y) = direction
                val row = location.first + x
                val column = location.second + y
                val adjacentCell = input.getOrNull(row)?.getOrNull(column)
                val isAdjacentDifferentAsChar = c != adjacentCell


                if (isAdjacentDifferentAsChar) {
                    val sidesx = sides[key] ?: Sides(emptySet(), emptySet())
                    val locationx = Location(row, column)
                    val sideRows = sidesx.vertical.toMutableSet().apply {

                        if (direction == directions[0] || direction == directions[2]) add(locationx to (x to y))
                    }
                    val sideColumns = sidesx.horizontal.toMutableSet().apply {
                        if (direction == directions[1] || direction == directions[3]) { add(locationx to (x to y)) }
                    }
                    sides[key] = Sides(sideRows, sideColumns)
                }

                isAdjacentDifferentAsChar
            }
        }

        fun addAreaOrParameter(c: Char, start: Pair<Int, Int>, position: Pair<Int, Int>) {
            val key = c to start
            areaMap[key] = areaMap[key]?.inc() ?: 1

            val checkAdjacent = checkAdjacent(key, c, position.first to position.second)
            if (checkAdjacent > 0) {
                perimeterMap[key] = (perimeterMap[key] ?: 0) + checkAdjacent
            }

            charArr[position.first][position.second] = '.'
        }

        fun move(start: Pair<Int, Int>,) {
            val c = charArr[start.first][start.second]
            fun backtrack(position: Pair<Int, Int>, direction: Pair<Int, Int>) {
                val nextRow = position.first + direction.first
                val nextColumn = position.second + direction.second
                val next = charArr.getOrNull(nextRow)?.getOrNull(nextColumn) ?: return

                if (next == c) {
                    addAreaOrParameter(c, start,nextRow to nextColumn)
                    directions.forEach { (x, y) ->
                        backtrack(
                            position = nextRow to nextColumn,
                            direction = x to y,
                        )
                    }
                }
            }

            addAreaOrParameter(c, start, start)
            directions.forEach { direction ->
                backtrack(
                    position = start.first to start.second,
                    direction = direction,
                )
            }
        }

        fun findFirstNonVisited(): Pair<Int, Int> {
            for (r in charArr.indices) {
                for (c in charArr[0].indices) {
                    if (charArr[r][c] != '.') return r to c
                }
            }
            return -1 to -1
        }

        var start = findFirstNonVisited()
        while (start != Pair(-1, -1)) {
            move(start)
            start = findFirstNonVisited()
        }

        return areaMap.keys.sumOf { c ->
            val perimeter = if (withSides) sides[c]?.getPerimeter()!! else perimeterMap[c]!!
            areaMap[c]!! * perimeter
        }
    }

    fun part1(input: List<String>): Int {
        return computeAreaAndPerimeter(input, false)
    }

    fun part2(input: List<String>): Int {
        return computeAreaAndPerimeter(input, true)
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("2024/Day12_test1")
    val part1test1 = part1(testInput1)
    part1test1.println()
    check(part1test1 == 140)
    val part2test1 = part2(testInput1)
    part2test1.println()
    check(part2test1 == 80)

    val testInput2 = readInput("2024/Day12_test2")
    val part1test2 = part1(testInput2)
    part1test2.println()
    check(part1test2 == 772)

    val testInput3 = readInput("2024/Day12_test3")
    val part1test3 = part1(testInput3)
    part1test3.println()
    check(part1test3 == 1930)
    val part2test3 = part2(testInput3)
    part2test3.println()
    check(part2test3 == 1206)

    val testInput4 = readInput("2024/Day12_test4")
    val part2test4 = part2(testInput4)
    part2test4.println()
    check(part2test4 == 236)

    val input = readInput("2024/Day12")
    check(part1(input) == 1449902)
    check(part2(input) == 908042)
}
