val area = "..##.......\n" +
        "#...#...#..\n" +
        ".#....#..#.\n" +
        "..#.#...#.#\n" +
        ".#...##..#.\n" +
        "..#.##.....\n" +
        ".#.#.#....#\n" +
        ".#........#\n" +
        "#.##...#...\n" +
        "#...##....#\n" +
        ".#..#...#.#"
val weight = area.indexOf('\n')
weight
val grid = area.filterNot { it == '\n' }
grid
val angle = 3 to 1
val step = angle.first + angle.second * weight
//val ss = generateSequence(0) { it + angle.first + angle.second * weight - weight * (weight / angle.first + 1) }
//val idxs = ss.takeWhile { it <= area.length }.toList()
//idxs
(weight / angle.first + 1) * weight
//area.slice(idxs)
//area.slice(idxs).count { it == '#' }