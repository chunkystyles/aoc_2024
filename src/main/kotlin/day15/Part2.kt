package day15

import utils.*

fun main() {
  setInputIoStartTime()
  val input = getInput(15)
  setInputParseStartTime()
  val (map, moves) = parseInput2(input)
  setAlgorithmStartTime()
  var robot = 0 to 0
  for (y in map.indices) {
    for (x in map[y].indices) {
      if (map[y][x] == Space.ROBOT) {
        robot = x to y
        break
      }
    }
  }
  for (move in moves) {
    robot = wholeMove2(map, robot, move)
  }
  var total = 0
  for ((y, row) in map.withIndex()) {
    for ((x, space) in row.withIndex()) {
      if (space == Space.BOX_LEFT) {
        total += y * 100 + x
      }
    }
  }
  println(getElapsedTime())
  println("Total: $total")
}

fun parseInput2(input: String): Pair<MutableList<MutableList<Space>>, List<Direction>> {
  val split = input.split("\n")
  val map = mutableListOf<MutableList<Space>>()
  var i = 0
  while (i < split.size && split[i].isNotBlank()) {
    val row = mutableListOf<Space>()
    split[i]
      .split("")
      .filter { it.isNotBlank() }
      .forEach() { space ->
        when (space) {
          "#" -> {
            row.add(Space.WALL)
            row.add(Space.WALL)
          }
          "." -> {
            row.add(Space.EMPTY)
            row.add(Space.EMPTY)
          }
          "@" -> {
            row.add(Space.ROBOT)
            row.add(Space.EMPTY)
          }
          "O" -> {
            row.add(Space.BOX_LEFT)
            row.add(Space.BOX_RIGHT)
          }
          else -> throw IllegalArgumentException("Invalid space: $space")
        }
      }
    map.add(row)
    i++
  }
  i++
  val moves: MutableList<Direction> = mutableListOf()
  for (j in i until split.size) {
    moves.addAll(
      split[j].split("")
        .filter { it.isNotBlank() }
        .map {
          when (it) {
            "^" -> Direction.UP
            "v" -> Direction.DOWN
            "<" -> Direction.LEFT
            ">" -> Direction.RIGHT
            else -> throw IllegalArgumentException("Invalid direction: $it")
          }
        }
    )
  }
  return map to moves
}

fun wholeMove2(map: MutableList<MutableList<Space>>, robot: Pair<Int, Int>, direction: Direction): Pair<Int, Int> {
  val intendedSpace = singleMove(robot, direction)
  if (!isInBounds(map, intendedSpace) || map[intendedSpace.second][intendedSpace.first] == Space.WALL) {
    return robot
  }
  if (map[intendedSpace.second][intendedSpace.first] == Space.EMPTY) {
    map[intendedSpace.second][intendedSpace.first] = Space.ROBOT
    map[robot.second][robot.first] = Space.EMPTY
    return intendedSpace
  }
  return if (direction == Direction.LEFT || direction == Direction.RIGHT) {
    wholeMoveHorizontal(map, robot, direction)
  } else {
    wholeMoveVertical(map, robot, direction)
  }
}

fun wholeMoveHorizontal(map: MutableList<MutableList<Space>>, robot: Pair<Int, Int>, direction: Direction): Pair<Int, Int> {
  if (direction == Direction.RIGHT) {
    var x = robot.first + 1
    while (x < map[robot.second].size && map[robot.second][x] != Space.EMPTY && map[robot.second][x] != Space.WALL) {
      x++
    }
    if (map[robot.second][x] == Space.EMPTY) {
      var nextSpace = Space.BOX_RIGHT
      while (x > robot.first + 1) {
        map[robot.second][x] = nextSpace
        nextSpace = if (nextSpace == Space.BOX_RIGHT) {
          Space.BOX_LEFT
        } else {
          Space.BOX_RIGHT
        }
        x--
      }
      map[robot.second][x] = Space.ROBOT
      map[robot.second][robot.first] = Space.EMPTY
      return x to robot.second
    } else {
      return robot
    }
  } else {
    var x = robot.first - 1
    while (x >= 0 && map[robot.second][x] != Space.EMPTY && map[robot.second][x] != Space.WALL) {
      x--
    }
    if (map[robot.second][x] == Space.EMPTY) {
      var nextSpace = Space.BOX_LEFT
      while (x < robot.first - 1) {
        map[robot.second][x] = nextSpace
        nextSpace = if (nextSpace == Space.BOX_RIGHT) {
          Space.BOX_LEFT
        } else {
          Space.BOX_RIGHT
        }
        x++
      }
      map[robot.second][x] = Space.ROBOT
      map[robot.second][robot.first] = Space.EMPTY
      return x to robot.second
    } else {
      return robot
    }
  }
}

fun wholeMoveVertical(map: MutableList<MutableList<Space>>, robot: Pair<Int, Int>, direction: Direction): Pair<Int, Int> {
  val vy = if (direction == Direction.UP) -1 else 1
  val stack = mutableListOf<Pair<Int, Int>>()
  var spacesToMove = mutableSetOf<Pair<Int, Int>>()
  addBoxToList(map, stack, robot.first to robot.second + vy)
  while (stack.size > 0) {
    val location = stack.removeAt(stack.size - 1)
    val nextLocation = location.first to location.second + vy
    if (isInBounds(map, nextLocation)) {
      val nextSpace = map[nextLocation.second][nextLocation.first]
      when (nextSpace) {
        Space.BOX_RIGHT, Space.BOX_LEFT -> {
          addBoxToList(map, stack, nextLocation)
          spacesToMove.add(location)
        }
        Space.EMPTY -> {
          spacesToMove.add(location)
        }
        else -> {
          return robot
        }
      }
    } else {
      return robot
    }
  }
  if (direction == Direction.UP) {
    for (location in spacesToMove.sortedBy(Pair<Int, Int>::second)) {
      map[location.second + vy][location.first] = map[location.second][location.first]
      map[location.second][location.first] = Space.EMPTY
    }
  } else {
    for (location in spacesToMove.sortedByDescending(Pair<Int, Int>::second)) {
      map[location.second + vy][location.first] = map[location.second][location.first]
      map[location.second][location.first] = Space.EMPTY
    }
  }
  map[robot.second][robot.first] = Space.EMPTY
  map[robot.second + vy][robot.first] = Space.ROBOT
  return robot.first to robot.second + vy
}

fun addBoxToList(map: MutableList<MutableList<Space>>, list: MutableList<Pair<Int, Int>>, location: Pair<Int, Int>) {
  if (!list.contains(location)) {
    list.add(location)
  }
  when (val space = map[location.second][location.first]) {
    Space.BOX_LEFT -> {
      val otherSpace = location.first + 1 to location.second
      if (!list.contains(otherSpace)) {
        list.add(otherSpace)
      }
    }
    Space.BOX_RIGHT -> {
      val otherSpace = location.first - 1 to location.second
      if (!list.contains(otherSpace)) {
        list.add(otherSpace)
      }
    }
    else -> {
      throw IllegalArgumentException("Space at $location is not a box: $space")
    }
  }
}