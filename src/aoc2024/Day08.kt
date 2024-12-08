import kotlin.math.absoluteValue

fun main() {

    fun findPairings(input: List<String>): Map<Char, List<Pair<Int, Int>>> {
        val map = mutableMapOf<Char, List<Pair<Int, Int>>>()
        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, c ->
                if (c != '.') {
                    val pair = i to j
                    map[c] = map[c]?.toMutableList()?.apply { add(pair) } ?: listOf(pair)
                }
            }
        }
        return map
    }

    fun countAntinodes(input: Array<CharArray>, map: Map<Char, List<Pair<Int, Int>>>, extend: Boolean): Int {
        var ctr = 0
        map.keys.forEach { c ->
            val points = map[c]!!

            points.forEachIndexed { index, pointA ->
                val pointsToCompare = points.subList(index + 1, points.size)

                pointsToCompare.forEach { point ->
                    val xDiff = pointA.first - point.first
                    val yDiff = pointA.second - point.second

                    var multiplier = 1
                    do {
                        val newAX = pointA.first + xDiff * multiplier
                        val newAY = pointA.second + yDiff * multiplier
                        val antiNodeA = input.getOrNull(newAX)?.getOrNull(newAY)
                        if (antiNodeA != null && antiNodeA != '#') {
                            input[newAX][newAY] = '#'
                            ctr++
                        }
                        multiplier++
                    } while (extend && antiNodeA != null)

                    multiplier = 1

                    do {
                        val newBX = point.first - xDiff * multiplier
                        val newBY = point.second - yDiff * multiplier

                        val antiNodeB = input.getOrNull(newBX)?.getOrNull(newBY)

                        if (antiNodeB != null && antiNodeB != '#') {
                            input[newBX][newBY] = '#'
                            ctr++
                        }
                        multiplier++
                    } while (extend && antiNodeB != null)
                }
            }

        }
        return ctr
    }

    fun part1(input: List<String>): Int {
        val inputCharArray = input.toCharArray()
        return countAntinodes(inputCharArray, findPairings(input), false)
    }

    fun part2(input: List<String>): Int {
        val inputCharArray = input.toCharArray()
        return countAntinodes(
            inputCharArray,
            findPairings(input),
            true
        ) + inputCharArray.sumOf { it.count { it != '.' && it != '#' } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2024/Day08_test")
    val part1 = part1(testInput)
    val part2 = part2(testInput)
    part1.println()
    part2.println()
    check(part1 == 14)
    check(part2 == 34)

    val input = readInput("2024/Day08")
    check(part1(input) == 285)
    check(part2(input) == 944)
//    part2(input).println()
}
