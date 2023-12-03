fun main() {
    fun emptyArray() = arrayOf(
        IntArray(5) { -1 },
        IntArray(5) { -1 },
        IntArray(5) { -1 },
        IntArray(5) { -1 },
        IntArray(5) { -1 }
    )

    fun print(card: Array<IntArray>) {
        card.forEach { arrays ->
            arrays.forEach {
                print("$it, ")
            }
            println()
        }
    }

    fun checkRows(bingoCards: Array<IntArray>): Boolean {
        bingoCards.forEach { row ->
            if (row.none { it != -1 }) {
                return true
            }
        }
        return false
    }

    fun checkColumns(bingoCards: Array<IntArray>): Boolean {
        val columns = emptyArray()

        // reverse array
        for (i in 0 until 5) {
            for (j in 0 until 5){
                columns[j][i] = bingoCards[i][j]
            }
        }

        return checkRows(columns)
    }

    fun isBingo(number: Int, bingoCards: Array<IntArray>): Boolean {
        bingoCards.forEachIndexed { rowIndex, row ->
            row.forEachIndexed row@{ columnIndex, i ->
                if (number == bingoCards[rowIndex][columnIndex]) {
                    bingoCards[rowIndex][columnIndex] = -1
                    return@row
                }
            }
        }
        return checkRows(bingoCards) || checkColumns(bingoCards)
    }

    fun fillBingoCardList(
        cards: List<String>
    ) : MutableList<Array<IntArray>> {
        var card = emptyArray()
        var indexOfRow = 0
        val bingoCardList = mutableListOf<Array<IntArray>>()

        cards.forEachIndexed iterateInput@{ indexOfLine, line ->
            val row = line
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.toInt() }

            row.forEachIndexed { index, number ->
                card[indexOfRow][index] = number
            }

            indexOfRow++

            if (line.trim().isBlank() || indexOfLine == cards.lastIndex) {
                indexOfRow = 0
                bingoCardList.add(card)

                card = emptyArray()
            }
        }
        return bingoCardList
    }

    fun sumOf(card: Array<IntArray>) = card.sumOf { row ->
        row.filter { it != -1 }.sum()
    }

    fun part1(input: List<String>): Int {
        val numbers = input.first().split(',').map { it.toInt() }
        val cards = input.subList(2, input.size)
        val bingoCardList = fillBingoCardList(cards)

        numbers.forEach { number ->
            bingoCardList.forEach { card ->
                if (isBingo(number, card)) {
                    return number * sumOf(card)
                }
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        val numbers = input.first().split(',').map { it.toInt() }
        val cards = input.subList(2, input.size)
        val bingoCardList = fillBingoCardList(cards).toMutableList<Array<IntArray>?>()

        numbers.forEach { number ->
            var lastCard = emptyArray()
            bingoCardList.forEachIndexed { rowIndex, card ->
                if (card != null && isBingo(number, card)) {
                    lastCard = card
                    bingoCardList[rowIndex] = null
                }
            }

            if (bingoCardList.filterNotNull().isEmpty()) {
                return number * sumOf(lastCard)
            }
        }
        return 0
    }

    val input = readInput("2021/Day04")
    val sample = readInput("2021/Day04_test")

    check(part1(sample) == 4512)
    check(part2(sample) == 1924)
    check(part1(input) == 39902)
    check(part2(input) == 26936)
}
