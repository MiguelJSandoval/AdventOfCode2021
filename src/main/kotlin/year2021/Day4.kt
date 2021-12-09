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

import readInputAsNum
import readInputAsString

fun main()
{
    val turns = readInputAsNum("d4_turns.txt", true)
    val inputEj4 = readInputAsString("d4.txt")
    println(ej4P1(inputEj4, turns))
    println(ej4P2(inputEj4, turns))
}

private fun computeInput(inputEj4: Array<String>): Array<Array<Array<Pair<Int, Boolean>>>>
{
    return inputEj4.let {
        val boards = arrayListOf<Array<Array<Pair<Int, Boolean>>>>()
        var board = Array(5) { Array(5) { Pair(0, false) } }
        var counter = 0
        for (i in it)
        {
            if (i != "")
            {
                val split1 = i.split(" ").toList()
                val split = arrayListOf<Int>()
                for (j in split1)
                    if (j != "")
                        split.add(j.toInt())
                for ((idx, j) in split.withIndex())
                    board[counter][idx] = Pair(j, false)
                counter++
            } else
            {
                boards.add(board)
                board = Array(5) { Array(5) { Pair(0, false) } }
                counter = 0
            }
        }
        boards.add(board)
        boards.toTypedArray()
    }
}

private fun ej4P1(inputEj4: Array<String>, turns: Array<Int>): Long
{
    val newInput = computeInput(inputEj4)
    var boardIdx = -1
    var winBy = -1

    for (i in turns)
    {
        for ((x, j) in newInput.withIndex())
            for ((y, k) in j.withIndex())
                for ((z, l) in k.withIndex())
                    if (l.first == i)
                        newInput[x][y][z] = Pair(i, true)
        var find = false
        for ((x, j) in newInput.withIndex())
        {
            var row = 0
            var column = 0
            for (k in 0 until 5)
            {
                for (l in 0 until 5)
                {
                    if (j[k][l].second)
                        row++
                    if (j[l][k].second)
                        column++
                }
                if (row == 5 || column == 5)
                {
                    find = true
                    boardIdx = x
                    winBy = i
                    break
                }
                row = 0
                column = 0
            }
            if (find)
                break
        }

        if (find)
            break
    }

    var noVisited = 0L
    val board = newInput[boardIdx]
    for (i in board)
        for (j in i)
            if (!j.second)
                noVisited += j.first
    return noVisited * winBy
}

private fun ej4P2(inputEj4: Array<String>, turns: Array<Int>): Long
{
    val newInput = computeInput(inputEj4)
    var lastWinner = -1
    var winBy = -1

    val winners = arrayListOf<Int>()

    for (i in turns)
    {
        for ((x, j) in newInput.withIndex())
            if (!winners.contains(x))
            {
                for ((y, k) in j.withIndex())
                    for ((z, l) in k.withIndex())
                        if (l.first == i)
                            newInput[x][y][z] = Pair(i, true)
            }
        for ((x, j) in newInput.withIndex())
        {
            if (!winners.contains(x))
            {
                var row = 0
                var column = 0
                for (k in 0 until 5)
                {
                    for (l in 0 until 5)
                    {
                        if (j[k][l].second)
                            row++
                        if (j[l][k].second)
                            column++
                    }
                    if (row == 5 || column == 5)
                    {
                        lastWinner = x
                        winBy = i
                        winners.add(x)
                        break
                    }
                    row = 0
                    column = 0
                }
            }
        }
    }
    var noVisited = 0L
    val board = newInput[lastWinner]
    for (i in board)
        for (j in i)
            if (!j.second)
                noVisited += j.first
    return noVisited * winBy
}
