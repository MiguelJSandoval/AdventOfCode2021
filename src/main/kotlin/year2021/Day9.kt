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

fun main()
{
    val inputEj9 = readInputAsString("d9.txt")
    val lowPoints = arrayListOf<Pair<Int, Int>>()
    println(ej9P1(inputEj9, lowPoints))
    println(ej9P2(inputEj9, lowPoints))
}

private fun ej9P1(inputEj9: Array<String>, lowPoints: ArrayList<Pair<Int, Int>>): Long
{
    var sum = 0L
    for ((y, i) in inputEj9.withIndex())
    {
        for ((x, j) in i.withIndex())
        {
            val comparators = Pair(j.toString().toInt(), arrayListOf<Int>())
            if (y - 1 >= 0)
                comparators.second.add(inputEj9[y - 1][x].toString().toInt())
            if (y + 1 < inputEj9.size)
                comparators.second.add(inputEj9[y + 1][x].toString().toInt())
            if (x - 1 >= 0)
                comparators.second.add(inputEj9[y][x - 1].toString().toInt())
            if (x + 1 < i.length)
                comparators.second.add(inputEj9[y][x + 1].toString().toInt())
            var counter = 0
            for (k in comparators.second)
                if (k > comparators.first) counter++
                else break
            if (counter == comparators.second.size)
            {
                lowPoints.add(Pair(y, x))
                sum += (comparators.first + 1)
            }
        }
    }
    return sum
}

private fun ej9P2(inputEj9: Array<String>, lowPoints: ArrayList<Pair<Int, Int>>): Long
{
    val nIn = inputEj9.let {
        val array = Array(it.size) { Array(inputEj9[0].length) { 0 } }
        for ((y, i) in it.withIndex())
            for ((x, j) in i.withIndex())
                array[y][x] = j.toString().toInt()
        array
    }
    val basins = arrayListOf<ArrayList<Pair<Int, Int>>>()
    val sizes = arrayListOf<Int>()
    for (i in lowPoints)
    {
        val arr = arrayListOf(i)
        findAdjacent(nIn, arr, i)
        basins.add(arr)
        sizes.add(arr.size)
    }
    sizes.sort()
    return sizes[sizes.size - 1].toLong() * sizes[sizes.size - 2] * sizes[sizes.size - 3]
}

private fun findAdjacent(input: Array<Array<Int>>,
                         arr: ArrayList<Pair<Int, Int>>,
                         idx: Pair<Int, Int>)
{
    val j = input[idx.first][idx.second]
    val y = idx.first
    val x = idx.second
    if (y - 1 >= 0 && input[y - 1][x] != 9 && input[y - 1][x] > j)
    {
        val pair = Pair(y - 1, x)
        if (!arr.contains(pair))
        {
            arr.add(pair)
            findAdjacent(input, arr, pair)
        }
    }
    if (y + 1 < input.size && input[y + 1][x] != 9 && input[y + 1][x] > j)
    {
        val pair = Pair(y + 1, x)
        if (!arr.contains(pair))
        {
            arr.add(pair)
            findAdjacent(input, arr, pair)
        }
    }
    if (x - 1 >= 0 && input[y][x - 1] != 9 && input[y][x - 1] > j)
    {
        val pair = Pair(y, x - 1)
        if (!arr.contains(pair))
        {
            arr.add(pair)
            findAdjacent(input, arr, pair)
        }
    }
    if (x + 1 < input[0].size && input[y][x + 1] != 9 && input[y][x + 1] > j)
    {
        val pair = Pair(y, x + 1)
        if (!arr.contains(pair))
        {
            arr.add(pair)
            findAdjacent(input, arr, pair)
        }
    }
}
