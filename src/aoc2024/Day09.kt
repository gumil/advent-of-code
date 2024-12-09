fun main() {

    fun parseInput(input: String): Array<String> {
        val inputDigits = input.map { it.digitToInt() }
        val arraySize = inputDigits.sum()
        val arr = Array(arraySize) { "" }
        var arrIndex = 0
        var id = 0

        inputDigits.forEachIndexed { index, digit ->
            for (i in 0 until digit) {
                if (index % 2 == 0) {
                    arr[arrIndex++] = "$id"
                } else {
                    arr[arrIndex++] = "."
                }
            }

            if (index % 2 == 0) id++
        }

        return arr
    }

    fun part1(input: String): Long {
        val arr = parseInput(input)
        var arrIndex = arr.lastIndex

        arr.forEachIndexed { index, c ->
            val isEveryDigitMoved = arr.slice(index..arr.lastIndex).all { it == "." }
            if (isEveryDigitMoved) return@forEachIndexed
            if (c == ".") {
                arr[index] = arr[arrIndex]
                arr[arrIndex] = "."

                do {
                    arrIndex--
                } while (arr[arrIndex] == ".")
            }
        }

        val indexOfFirstDot = arr.indexOfFirst { it == "." }
        val newArr = arr.slice(0 until indexOfFirstDot)
        return newArr.map { it.toLong() }.mapIndexed { index, digit ->  index * digit }.sum()
    }

    fun part2(input: String): Long {
        var endId = input.length / 2
        val occupiedSpace = input.filterIndexed { index, _ -> index % 2 == 0 }.map { it.digitToInt() }.reversed()
        val arr = parseInput(input)

        var startIndex = 0
        occupiedSpace.forEach { fileSize ->
            var freeSpace = 0
            var endIndex = arr.indexOfFirst { it == endId.toString() }

            for (index in 0..endIndex) {
                val s = arr[index]
                if (s == ".") {
                    if (freeSpace == 0) startIndex = index
                    freeSpace++
                } else {
                    if (freeSpace >= fileSize) {
                        repeat(fileSize) {
                            arr[startIndex++] = arr[endIndex]
                            arr[endIndex++] = "."
                        }
                        break
                    }
                    freeSpace = 0
                }
            }
            endId--
        }

        return arr.mapIndexed { index, digit ->
            if (digit == ".") 0
            else index * digit.toLong()
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = 2333133121414131402.toString()
    val part1 = part1(testInput)
    part1.println()
    val part2 = part2(testInput)
    part2.println()
    check(part2 == 2858L)

    val input = readInput("2024/Day09").first()
    check(part1(input) == 6421128769094)
    check(part2(input) == 6448168620520)
}
