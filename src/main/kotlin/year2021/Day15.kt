/*
 * Copyright (c) 2021, DevMJS. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this code.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Please contact DevMJS on contact.devmjs@gmail.com if you need
 * additional information or have any questions.
 */

package year2021

import readInputAsString
import java.util.*

fun main()
{
    val input = readInputAsString("d15.txt").let {
        val width = it[0].length
        val array = Array(it.size) { Array(width) { 0 } }
        for ((y, i) in it.withIndex())
            for ((x, j) in i.withIndex())
                array[y][x] = j.toString().toInt()
        array
    }
    println(getBestRoute(input, input.size, input[0].size))
    println(getBestRoute(input, input.size * 5, input[0].size * 5))
}

//Due to an array can not be cast to <Comparable> we need to implement a custom comparator.
private fun <T> arrayComparator(firstComparator: Comparator<T>): Comparator<Array<T>> =
    compareBy(firstComparator) {v -> v[0]} //Always compare by the first element of the array.

private fun getData(input: Array<Array<Int>>, y: Int, x: Int): Int
{
    val v = input[y % input.size][x % input[0].size] + (y / input.size) + (x / input[0].size)
    return (v - 1) % 9 + 1
}

private fun getBestRoute(input: Array<Array<Int>>, maxY: Int, maxX: Int): Int
{
    val queue = PriorityQueue<Array<Int>>(arrayComparator(naturalOrder()))
    queue.add(arrayOf(input[0][0],0,0))
    val visited = hashMapOf<Pair<Int, Int>, Boolean>()
    val cost = hashMapOf<Pair<Int, Int>, Int>()
    while (queue.isNotEmpty())
    {
        val element = queue.poll()
        val value = element[0]
        val pair = Pair(element[1], element[2])
        if (visited[pair] != null)
            continue
        visited[pair] = true
        cost[pair] = value
        if (pair.first == maxY - 1 && pair.second == maxX - 1)
            break
        for (i in arrayOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0)))
        {
            val r2 = pair.first + i.first
            val c2 = pair.second + i.second
            if (!(r2 in 0 until maxY && c2 in 0 until maxX))
                continue
            queue.add(arrayOf(value + getData(input, r2, c2), r2, c2))
        }
    }
    return cost[Pair(maxY - 1, maxX - 1)]!! - input[0][0]
}
