fun main() {
    fun parseGame(line: String): Game {
        val idAndRest = line.removePrefix("Game ").split(":")
        val id = Integer.parseInt(idAndRest[0])
        val pulls = mutableListOf<Pull>()
        val pullTexts = idAndRest[1].split(";")
        for (pullText in pullTexts) {
            var red = 0;
            var blue = 0;
            var green = 0;
            for (colorPullText in pullText.trim().split(", ")) {
                val countAndColor = colorPullText.split(" ")
                val count = Integer.parseInt(countAndColor[0])
                val color = countAndColor[1]
                when (color) {
                    "red" -> {
                        red = count
                    }

                    "blue" -> {
                        blue = count
                    }

                    "green" -> {
                        green = count
                    }
                }
            }

            pulls.add(Pull(red = red, blue = blue, green = green))
        }

        return Game(id, pulls)
    }

    fun part1(input: List<String>): Int {
        return input.map(::parseGame)
            .also { println(it) }
            .filter { it.isPossible(maxRed = 12, maxGreen = 13, maxBlue = 14) }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input.map(::parseGame)
            .sumOf { it.power() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

data class Game(val id: Int, val pulls: List<Pull>) {
    fun isPossible(maxRed: Int, maxGreen: Int, maxBlue: Int): Boolean {
        return pulls.none { it.red > maxRed || it.blue > maxBlue || it.green > maxGreen }
    }

    fun power(): Int =
        pulls.map { it.green }.max() * pulls.map { it.red }.max() * pulls.map { it.blue }.max()

}

data class Pull(val red: Int, val blue: Int, val green: Int)
