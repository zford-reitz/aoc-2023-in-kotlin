fun main() {

    val spelledValues = mapOf(
        Pair("one", '1'),
        Pair("two", '2'),
        Pair("three", '3'),
        Pair("four", '4'),
        Pair("five", '5'),
        Pair("six", '6'),
        Pair("seven", '7'),
        Pair("eight", '8'),
        Pair("nine", '9'),
    )

    fun lineValue(line: String): Int =
        Integer.valueOf(line.first { it.isDigit() }.toString() + line.last { it.isDigit() }.toString())

    fun part1(input: List<String>): Int {
        var total = 0
        for (line in input) {
            total += lineValue(line)
        }
        return total
    }

    fun firstDigitOrSpelledDigit(line: String): Char {
        val firstDigitIndex = line.indexOfFirst { it.isDigit() }

        val firstSpelledDigit = line.findAnyOf(spelledValues.keys)
        if (firstDigitIndex == -1 || (firstSpelledDigit != null && firstSpelledDigit.first < firstDigitIndex)) {
            return spelledValues[firstSpelledDigit!!.second]!!
        }

        return line[firstDigitIndex]
    }

    fun lastDigitOrSpelledDigit(line: String): Char {
        val lastDigitIndex = line.indexOfLast { it.isDigit() }

        val lastSpelledDigit = line.findLastAnyOf(spelledValues.keys)
        if (lastDigitIndex == -1 || (lastSpelledDigit != null && lastSpelledDigit.first > lastDigitIndex)) {
            return spelledValues[lastSpelledDigit!!.second]!!
        }

        return line[lastDigitIndex]
    }

    fun lineValuePart2(line: String): Int =
        Integer.valueOf(firstDigitOrSpelledDigit(line).toString() + lastDigitOrSpelledDigit(line).toString())

    fun part2(input: List<String>): Int {
        var total = 0
        for (line in input) {
            total += lineValuePart2(line)
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
