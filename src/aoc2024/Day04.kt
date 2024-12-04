fun main() {

    val xmas = Regex("XMAS")
    val samx = Regex("SAMX")

    fun findXmas(input: List<String>): Int {
        return input.sumOf { s ->
            xmas.findAll(s).count() + samx.findAll(s).count()
        }
    }

    fun findVertical(input: List<String>): Int {
        val row = input.size
        val column = input.first().length
        val list = mutableListOf<String>()
        for (i in 0 until column) {
            val sb = StringBuilder()
            for (j in 0 until row) {
                sb.append(input[j][i])
            }
            list.add(sb.toString())
        }
        return findXmas(list)
    }

    fun findDiagonalRight(input: List<String>): Int {
        val row = input.size
        val list = mutableListOf<String>()
        for (i in 0 until row) {
            val sb = StringBuilder()
            for (j in i until row) {
                sb.append(input[j - i][j])
            }
            list.add(sb.toString())
        }
        return findXmas(list)
    }

    fun findDiagonalLeft(input: List<String>): Int {
        val row = input.lastIndex
        val list = mutableListOf<String>()
        for (i in 0 .. row) {
            val sb = StringBuilder()
            val startIndex = row - i
            for (j in startIndex downTo 0) {
                sb.append(input[startIndex - j][j])
            }
            list.add(sb.toString())
        }
        return findXmas(list)
    }

    fun findDiagonalBottomRight(input: List<String>): Int {
        val column = input.first().lastIndex
        val list = mutableListOf<String>()
        for (i in 1..column) {
            val sb = StringBuilder()
            for (j in column downTo i) {
                sb.append(input[j][column - j + i])
            }
            list.add(sb.toString())
        }

        return findXmas(list)
    }

    fun findDiagonalBottomLeft(input: List<String>): Int {
        val column = input.first().lastIndex
        val list = mutableListOf<String>()
        for (i in 1..column) {
            val sb = StringBuilder()
            for (j in column downTo i) {
                sb.append(input[j][j - i])
            }
            list.add(sb.toString())
        }
        return findXmas(list)
    }

    fun part1(input: List<String>): Int {
        return findXmas(input) + findVertical(input) +
               findDiagonalLeft(input) + findDiagonalRight(input) +
               findDiagonalBottomLeft(input) + findDiagonalBottomRight(input)
    }

    fun part2(input: List<String>): Int {
        val row = input.size
        val column = input.first().length
        var count = 0
        for (i in 0 until row) {
            for (j in 0 until column) {
                val diagonalRight = StringBuilder().apply {
                    append(input[i][j])
                    append(input.getOrNull(i + 1)?.getOrNull(j + 1) ?: ".")
                    append(input.getOrNull(i + 2)?.getOrNull(j + 2) ?: ".")
                }.toString()

                val diagonalLeft = StringBuilder().apply {
                    append(input.getOrNull(i)?.getOrNull(j + 2) ?: ".")
                    append(input.getOrNull(i + 1)?.getOrNull(j + 1) ?: ".")
                    append(input.getOrNull(i + 2)?.getOrNull(j) ?: ".")
                }.toString()

                if ((diagonalLeft == "SAM" || diagonalLeft == "MAS") && (diagonalRight == "SAM" || diagonalRight == "MAS")) {
                    count++
                }
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2024/Day04_test")
    val part1 = part1(testInput)
    val part2 = part2(testInput)
    check(part1 == 18)
    check(part2 == 9)

    val input = readInput("2024/Day04")
    check(part1(input) == 2549)
    check(part2(input) == 2003)
}
