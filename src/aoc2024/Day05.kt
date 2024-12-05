fun main() {
    data class PageOrderingRule(
        val upperBound: Int,
        val lowerBound: Int,
    )

    data class PageInstructions(
        val rules: List<PageOrderingRule>,
        val pages: List<String>,
    ) {
        val ruleMap: Map<Int, List<Int>> = mutableMapOf<Int, List<Int>>().apply {
            rules.forEach { rule ->
                this[rule.upperBound] =
                    (this[rule.upperBound]?.toMutableList() ?: mutableListOf()).apply { add(rule.lowerBound) }
            }
        }
    }

    fun parseInput(input: List<String>): PageInstructions {
        val separatorIndex = input.indexOf("")
        val rules = input.subList(0, separatorIndex).map { line ->
            val (upperBound, lowerBound) = line.split("|")
            PageOrderingRule(upperBound.toInt(), lowerBound.toInt())
        }
        val pages = input.subList(separatorIndex + 1, input.size)
        return PageInstructions(rules, pages)
    }

    fun part1(input: List<String>): Int {
        val instructions = parseInput(input)
        val ruleMap = instructions.ruleMap
        return instructions.pages.sumOf { pagesLine ->
            val pages = pagesLine.split(",")
                .map { it.toInt() }

            pages.forEachIndexed { index, page ->
                val validNumbers = ruleMap[page] ?: listOf()
                pages.subList(index + 1, pages.size).forEach {
                    if (!validNumbers.contains(it)) {
                        return@sumOf 0
                    }
                }
            }
            pages[pages.size / 2]
        }
    }

    fun arrange(ruleMap: Map<Int, List<Int>>, pages: List<Int>, excludeIndex: Int): List<Int> {
        val placePage = pages[excludeIndex]
        val newPages = pages.toMutableList().apply { removeAt(excludeIndex) }
        newPages.forEachIndexed { index, page ->
            val validNumbers = ruleMap[page] ?: listOf()
            if (!validNumbers.contains(placePage)) {
                newPages.add(index, placePage)
                return newPages
            }
        }
        newPages.add(placePage)
        return newPages
    }

    fun check(ruleMap: Map<Int, List<Int>>, pages: List<Int>): List<List<Int>> {
        val invalidPages = mutableListOf<List<Int>>()
        pages.forEachIndexed { index, page ->
            val validNumbers = ruleMap[page] ?: listOf()
            pages.subList(index + 1, pages.size).forEach { page1 ->
                if (!validNumbers.contains(page1)) {
                    invalidPages.add(arrange(ruleMap, pages, index))
                    return invalidPages
                }
            }
        }
        return invalidPages
    }

    fun part2(input: List<String>): Int {
        val instructions = parseInput(input)
        val ruleMap = instructions.ruleMap
        var allPages = instructions.pages.map { pagesLine ->
            pagesLine.split(",")
                .map { it.toInt() }
        }

        val invalidPages = mutableListOf<List<Int>>()

        while (allPages.isNotEmpty()) {
            val iPages = mutableListOf<List<Int>>()
            allPages.forEach { pages ->
                iPages.addAll(check(ruleMap, pages))
            }
            allPages = iPages
            if (invalidPages.isEmpty()) {
                invalidPages.addAll(iPages)
            } else {

                iPages.forEach { pages ->
                    val index = invalidPages.indexOfFirst { list ->
                        list.containsAll(pages)
                    }
                    if (index != -1) invalidPages[index] = pages
                }
            }

        }

        return invalidPages
            .sumOf { pages ->
                pages[pages.size / 2]
            }
    }

    fun part2_1(input: List<String>): Int {
        val separatorIndex = input.indexOf("")
        val rules = input.subList(0, separatorIndex).map { line ->
            val (upperBound, lowerBound) = line.split("|").map { it.toInt() }
            upperBound to lowerBound
        }

        val updates = input.subList(separatorIndex + 1, input.size).map { line ->
            line.split(",").map { it.toInt() }
        }

        data class Page(val value: Int, val rules: List<Pair<Int, Int>>) : Comparable<Page> {

    		override fun compareTo(other: Page): Int {
    			if (value == other.value) return 0
    			return if (value to other.value in rules) -1 else 1
    		}

    	}

    	fun List<Int>.sortedPages() = map { Page(it, rules) }.sorted().map { it.value }
        fun List<Int>.isValid() = sortedPages() == this


        return updates
            .filter { !it.isValid() }
            .map { it.sortedPages() }
            .sumOf { it[it.size / 2] }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("2024/Day05_test")
//    parseInput(testInput).println()
    val part1 = part1(testInput)
    part1.println()
    val part2 = part2_1(testInput)
    part2.println()
    check(part1 == 143)
    check(part2 == 123)

    val input = readInput("2024/Day05")
    check(part1(input) == 6260)
    check(part2_1(input) == 5346)
}
