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
    val inputEj13 = readInputAsString("d13.txt").let {
        var xMax = -1
        var yMax = -1
        for (i in it)
        {
            val split = i.split(',')
            if (split[0].toInt() > xMax)
                xMax = split[0].toInt()
            if (split[1].toInt() > yMax)
                yMax = split[1].toInt()
        }
        val board = Array(yMax + 1) { Array(xMax + 1) { '.' } }
        for (i in it)
        {
            val split = i.split(',')
            board[split[1].toInt()][split[0].toInt()] = '#'
        }
        board
    }
    val instructions = readInputAsString("d13_instructions.txt").let {
        val array = Array(it.size) { Pair("", 0) }
        for ((idx, i) in it.withIndex())
        {
            val split = i.split(" ")[2].split('=')
            array[idx] = Pair(split[0], split[1].toInt())
        }
        array
    }
    println(ej13P1(inputEj13, instructions))
    println(ej13P1(inputEj13, instructions, instructions.size))
}

private fun ej13P1(input: Array<Array<Char>>, inst: Array<Pair<String, Int>>, iterations: Int = 1): Long
{
    var newInput = input.clone()
    var iter = 0
    while (iter < iterations)
    {
        val by = inst[iter].first
        val index = inst[iter].second
        if (by == "x")
        {
            val aux = Array(newInput.size) { Array((newInput[0].size - index) + 1) { '.' } }
            for ((y, i) in newInput.withIndex())
            {
                for (x in 0 until index)
                    aux[y][x] = newInput[y][x]
            }
            for ((y, i) in newInput.withIndex())
            {
                for (x in index until i.size)
                    aux[y][index - (x - index)] =
                        if (newInput[y][index - (x - index)] == '.' && newInput[y][x] == '.') '.' else '#'
            }
            newInput = aux
        } else if (by == "y")
        {
            val aux = Array((newInput.size - index) + 1) { Array(newInput[0].size) { '.' } }
            for (y in 0 until index)
                for ((x, j) in newInput[y].withIndex())
                    aux[y][x] = j
            for (y in index until newInput.size)
                for ((x, j) in newInput[y].withIndex())
                    aux[index - (y - index)][x] = if (j == '.' && newInput[index - (y - index)][x] == '.') '.' else '#'
            newInput = aux
        }
        iter++
    }
    var sum = 0L
    for (i in newInput)
        for (j in i)
            if (j == '#')
                sum++
    return sum
}
