fun main() {
    fun getRange(y1: Int, y2: Int) = if (y1 > y2) {
        y2 to y1
    } else {
        y1 to y2
    }

    fun part1(input: List<String>, size: Int = 1000): Int {
        val oceanFloor = emptyArray(size)
        input.forEach { line ->
            val coordinates = line.split(" -> ")
            val start = coordinates.first().split(",")
            val end = coordinates.last().split(",")

            val x1 = start.first().toInt()
            val y1 = start.last().toInt()

            val x2 = end.first().toInt()
            val y2 = end.last().toInt()

            // vertical movement
            if (x1 == x2) {
                val (startY, endY) = getRange(y1, y2)

                for (i in startY..endY) {
                    oceanFloor[i][x1] = oceanFloor[i][x1] + 1
                }
            }

            // horizontal movement
            if (y1 == y2) {
                val (startX, endX) = getRange(x1, x2)

                for (i in startX..endX) {
                    oceanFloor[y1][i] = oceanFloor[y1][i] + 1
                }
            }
        }

        return oceanFloor.sumOf { row ->
            row.count { it >= 2 }
        }
    }

    fun accumulatorCondition(start: Int, end: Int): (Int, Int) -> Boolean = if (start > end) {
        { startIndex, endIndex -> startIndex >= endIndex }
    } else {
        { startIndex, endIndex -> startIndex <= endIndex }
    }

    fun accumulator(start: Int, end: Int): (Int) -> Int = if (start > end) {
        { it - 1 }
    } else {
        { it + 1 }
    }

    fun part2(input: List<String>, size: Int = 1000): Int {
        val oceanFloor = emptyArray(size)
        input.forEach { line ->
            val coordinates = line.split(" -> ")
            val start = coordinates.first().split(",")
            val end = coordinates.last().split(",")

            val x1 = start.first().toInt()
            val y1 = start.last().toInt()

            val x2 = end.first().toInt()
            val y2 = end.last().toInt()

            when {
                x1 == x2 -> {
                    val (startY, endY) = getRange(y1, y2)

                    for (i in startY..endY) {
                        oceanFloor[i][x1] = oceanFloor[i][x1] + 1
                    }
                }
                y1 == y2 -> {
                    val (startX, endX) = getRange(x1, x2)

                    for (i in startX..endX) {
                        oceanFloor[y1][i] = oceanFloor[y1][i] + 1
                    }
                }
                else -> {
                    val accumulatorX = accumulator(x1, x2)
                    val accumulatorY = accumulator(y1, y2)

                    val conditionX = accumulatorCondition(x1, x2)
                    val conditionY = accumulatorCondition(y1, y2)

                    var (startX, endX) = x1 to x2
                    var (startY, endY) = y1 to y2

                    while (conditionX(startX, endX) || conditionY(startY, endY)) {
                        oceanFloor[startY][startX] = oceanFloor[startY][startX] + 1
                        startX = accumulatorX(startX)
                        startY = accumulatorY(startY)
                    }
                }
            }
        }

        return oceanFloor.sumOf { row ->
            row.count { it >= 2 }
        }
    }

    val input = readInput("Day05")
    val sample = readInput("Day05_test")

    check(part1(sample, 10) == 5)
    check(part2(sample, 10) == 12)
    check(part1(input) == 6856)
    check(part2(input) == 20666)
}
