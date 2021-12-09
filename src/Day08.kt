fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            val digits = line.split(" ")
            val outputs = digits.subList(digits.indexOf("|") + 1, digits.size)

            outputs.filter {
                it.length != 5 && it.length != 6
            }
        }.sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val digits = line.split("|")
            val inputs = digits.first().split(" ").filter { it.isNotBlank() }
            val outputs = digits.last().split(" ").filter { it.isNotBlank() }
            val charMap = ('a'..'g').associateWith { 0 }.toMutableMap()

            // Occurrence values
            //     8888
            //    6    8
            //    6    8
            //     7777
            //    4    9
            //    4    9
            //     7777
            val sumMap = mapOf(
                42 to 0,
                17 to 1,
                34 to 2,
                39 to 3,
                30 to 4,
                37 to 5,
                41 to 6,
                25 to 7,
                49 to 8,
                45 to 9,
            )

            inputs.forEach { input ->
                input.forEach { c ->
                    charMap[c] = (charMap[c] ?: 0) + 1
                }
            }

            val sb = StringBuilder()
            outputs.forEach { input ->
                val sum = input.sumOf { charMap[it] ?: 0 }
                sb.append(sumMap[sum])
            }

            sb.toString().toInt()
        }
    }

    val input = readInput("Day08")
    val sample = readInput("Day08_test")

    println(part2(input))
    check(part1(sample) == 26)
    check(part2(sample) == 61229)
    check(part1(input) == 504)
    check(part2(input) == 1073431)
}
