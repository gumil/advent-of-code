fun part1(input: List<List<Char>>): Int {
    val directions = listOf(
        0 to 1, 0 to -1, -1 to 0, 1 to 0, -1 to 1, 1 to -1, 1 to 1, -1 to -1
    )
    fun getWord(r: Int, c: Int, dr: Int, dc: Int): String = (0..3).joinToString("") { i ->
        input.getOrNull(r + i * dr)?.getOrNull(c + i * dc)?.toString() ?: "."
    }
    val allWords = buildList {
        for (r in input.indices) {
            for (c in input[r].indices) {
                directions.forEach { (dr, dc) ->
                    add(getWord(r, c, dr, dc))
                }
            }
        }
    }
    return allWords.count { it == "XMAS" }
}

fun part2(input: List<List<Char>>): Int {
    var total = 0
    for (r in input.indices) {
        for (c in input[r].indices) {
            val x = "${input.getOrNull(r - 1)?.getOrNull(c - 1) ?: "."}" + "${
                input.getOrNull(r)?.getOrNull(c) ?: "."
            }" + "${input.getOrNull(r + 1)?.getOrNull(c + 1) ?: "."}"
            val y = "${input.getOrNull(r + 1)?.getOrNull(c - 1) ?: "."}" + "${
                input.getOrNull(r)?.getOrNull(c) ?: "."
            }" + "${input.getOrNull(r - 1)?.getOrNull(c + 1) ?: "."}"
            x.println()
            y.println()
            println("($r, $c) x = $x y = $y")
            if ((x == "SAM" || x == "MAS") && (y == "SAM" || y == "MAS")) {
                total++
            }
        }
    }
    return total
}

fun main() {
    val input = readInput("2024/Day04_test").map { it.toList() }
    part1(input)
    part2(input).println()
}
