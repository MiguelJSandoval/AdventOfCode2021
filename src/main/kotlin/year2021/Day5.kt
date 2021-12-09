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
import kotlin.math.floor

fun main()
{
    val inputEj5 = computeInput(readInputAsString("d5.txt"))
    println(ej5P1(inputEj5))
    println(ej5P2(inputEj5))
}

private fun computeInput(array: Array<String>): Array<Array<Pair<Int, Int>>> =
    array.let {
        val lines = Array(it.size) { Array(2) { Pair(0, 0) } }
        for ((idx, i) in it.withIndex())
        {
            val split = i.split(" -> ")
            val split2 = split[0].split(",")
            val split3 = split[1].split(",")
            lines[idx][0] = Pair(split2[0].toInt(), split2[1].toInt())
            lines[idx][1] = Pair(split3[0].toInt(), split3[1].toInt())
        }
        lines
    }

private fun ej5P1(inputEj5: Array<Array<Pair<Int, Int>>>): Long
{
    var xMax = -1
    var yMax = -1
    for (i in inputEj5)
        for (j in i)
        {
            if (j.first > xMax)
                xMax = j.first
            if (j.second > yMax)
                yMax = j.second
        }
    val matrix = Array(yMax + 1) { Array(xMax + 1) { 0 } }
    for (i in inputEj5)
    {
        val equalX = i[0].first == i[1].first
        val equalY = i[0].second == i[1].second
        if (equalX || equalY)
        {
            val x0 = if (equalX) i[0].first else Math.min(i[0].first, i[1].first)
            val x1 = if (equalX) i[0].first else Math.max(i[0].first, i[1].first)
            val y0 = if (equalY) i[0].second else Math.min(i[0].second, i[1].second)
            val y1 = if (equalY) i[0].second else Math.max(i[0].second, i[1].second)
            if (equalX)
            {
                if (equalY)
                    matrix[y0][x0] += 1
                for (j in y0..y1)
                    matrix[j][x0] += 1
            } else
            {
                for (j in x0..x1)
                    matrix[y0][j] += 1
            }
        }
    }
    var counter = 0L
    for (i in matrix)
        for (j in i)
            if (j >= 2)
                counter++
    return counter
}

private fun ej5P2(inputEj5: Array<Array<Pair<Int, Int>>>): Long
{
    var xMax = -1
    var yMax = -1
    for (i in inputEj5)
        for (j in i)
        {
            if (j.first > xMax)
                xMax = j.first
            if (j.second > yMax)
                yMax = j.second
        }
    val matrix = Array(yMax + 1) { Array(xMax + 1) { 0 } }
    for (i in inputEj5)
    {
        val equalX = i[0].first == i[1].first
        val equalY = i[0].second == i[1].second
        if (equalX || equalY)
        {
            val x0 = if (equalX) i[0].first else Math.min(i[0].first, i[1].first)
            val x1 = if (equalX) i[0].first else Math.max(i[0].first, i[1].first)
            val y0 = if (equalY) i[0].second else Math.min(i[0].second, i[1].second)
            val y1 = if (equalY) i[0].second else Math.max(i[0].second, i[1].second)
            if (equalX)
            {
                if (equalY)
                    matrix[y0][x0] += 1
                for (j in y0..y1)
                    matrix[j][x0] += 1
            } else
            {
                for (j in x0..x1)
                    matrix[y0][j] += 1
            }
        } else
        {
            val num = i[1].second - i[0].second
            val den = i[1].first - i[0].first
            val m = num.toDouble() / den.toDouble()
            val x0 = Math.min(i[0].first, i[1].first)
            val x1 = Math.max(i[0].first, i[1].first)
            val y0 = Math.min(i[0].second, i[1].second)
            val y1 = Math.max(i[0].second, i[1].second)
            for (j in y0..y1)
                for (k in x0..x1)
                {
                    val y = floor((m * (k - i[1].first)) + i[1].second)
                    if (y.toInt() == j)
                        matrix[j][k] += 1
                }
        }
    }
    var counter = 0L
    for (i in matrix)
        for (j in i)
            if (j >= 2)
                counter++
    return counter
}
