fun main() {
    fun part1(input: List<String>): Int {
        var fishes = input.first().split(",")
            .map { it.toInt() }
            .toMutableList()

        for (i in 1..80) {
            var numOfFishesToCreate = 0
            fishes = fishes.map { timer ->
                var newTimer = timer - 1
                if (newTimer == -1) {
                    newTimer = 6
                    numOfFishesToCreate++
                }
                newTimer
            }.toMutableList()

            repeat(numOfFishesToCreate) {
                fishes.add(8)
            }
        }

        return fishes.size
    }

    fun part2(input: List<String>): Long {
        val fishes = input.first().split(",")
            .map { it.toInt() }
            .toMutableList()

        var fishSums = LongArray(9)

        fishes.forEach {
            fishSums[it] = fishSums[it] + 1
        }

        for (i in 1..256) {
            val newFishSums = LongArray(9)
            newFishSums.forEachIndexed { index, _ ->
                if (index == 0) {
                    newFishSums[6] = newFishSums[6] + fishSums[index]
                    return@forEachIndexed
                }
                newFishSums[index - 1] = newFishSums[index - 1] + fishSums[index]
            }
            newFishSums[8] = fishSums[0]

            fishSums = newFishSums
        }
        return fishSums.sum()
    }

    val input = readInput("Day06")
    val sample = readInput("Day06_test")
    check(part1(sample) == 5934)
    check(part2(sample) == 26984457539)
    check(part1(input) == 373378)
    check(part2(input) == 1682576647495)
}
