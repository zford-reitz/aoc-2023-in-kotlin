import kotlin.math.max
import kotlin.math.min

fun main() {

    fun isPartNumber(input: List<String>, rowIndex: Int, columnIndex: Int): Boolean {
        val minRowIndex = max(rowIndex - 1, 0)
        val minColumnIndex = max(columnIndex - 1, 0)

        val maxRowIndex = min(rowIndex + 1, input.size - 1)
        val maxColumnIndex = min(columnIndex + 1, input[rowIndex].length - 1)

        for (row in minRowIndex..maxRowIndex) {
            for (column in minColumnIndex..maxColumnIndex) {
                val char = input[row][column]
                if (!char.isDigit() && char != '.') {
                    return true
                }
            }
        }

        return false
    }

    fun findNumbers(input: List<String>): List<String> {
        val numbers = mutableListOf<String>()
        var isPartNumber = false
        var number = ""
        for ((rowIndex, row) in input.asSequence().withIndex()) {
            for ((columnIndex, char) in row.asSequence().withIndex()) {
                if (char.isDigit()) {
                    number += char
                    isPartNumber = isPartNumber || isPartNumber(input = input, rowIndex = rowIndex, columnIndex = columnIndex)
                } else {
                    if (isPartNumber) {
                        numbers.add(number)
                    }
                    number = ""
                    isPartNumber = false
                }
            }

            if (isPartNumber) {
                numbers.add(number)
            }
            number = ""
            isPartNumber = false

        }

        return numbers
    }

    fun part1(input: List<String>): Int {
        return findNumbers(input).also { it.println() }.map { Integer.parseInt(it) }.sum()
    }

    fun findPossiblePartNumbers(line: String): List<PossiblePartNumber> {
        val possiblePartNumbers = mutableListOf<PossiblePartNumber>()
        var number = ""
        for ((columnIndex, char) in line.asSequence().withIndex()) {
            if (char.isDigit()) {
                number += char
            } else {
                if (number != "") {
                    val endIndex = columnIndex - 1
                    possiblePartNumbers.add(PossiblePartNumber(Integer.parseInt(number), columnIndex - number.length, endIndex))
                }
                number = ""
            }
        }

        if (number != "") {
            val endIndex = line.length - 1
            possiblePartNumbers.add(PossiblePartNumber(Integer.parseInt(number), line.length - number.length, endIndex))
        }

        return possiblePartNumbers
    }

    fun findAdjacentPartNumbers(input: List<String>, rowIndex: Int, columnIndex: Int): List<Int> {
        val possiblePartNumbers = mutableListOf<PossiblePartNumber>()
        for (row in max(rowIndex - 1, 0)..min(rowIndex + 1, input.size - 1)) {
            possiblePartNumbers.addAll(findPossiblePartNumbers(input[row]))
        }

        return possiblePartNumbers.filter { it.isAdjacentToColumnIndex(columnIndex) }.map { it.number }
    }

    fun findGearRatios(input: List<String>): List<Int> {
        val gearRatioLocations = mutableListOf<Pair<Int, Int>>()
        val gearRatios = mutableListOf<Int>()
        for ((rowIndex, row) in input.asSequence().withIndex()) {
            for ((columnIndex, char) in row.asSequence().withIndex()) {
                if (char == '*') {
                    val adjacentPartNumbers = findAdjacentPartNumbers(input, rowIndex, columnIndex)
                    if (adjacentPartNumbers.size == 2) {
                        gearRatios.add(adjacentPartNumbers[0] * adjacentPartNumbers[1])
                        gearRatioLocations.add(Pair(rowIndex, columnIndex))
                    }
                }
            }
        }

        gearRatioLocations.println()

        return gearRatios
    }

    fun part2(input: List<String>): Int {
        return findGearRatios(input).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

data class PossiblePartNumber(val number: Int, val startIndex: Int, val endIndex: Int) {
    fun isAdjacentToColumnIndex(columnIndex: Int): Boolean {
        for (index in startIndex..endIndex) {
            if (index >= columnIndex - 1 && index <= columnIndex + 1) {
                return true
            }
        }

        return false
    }
}
